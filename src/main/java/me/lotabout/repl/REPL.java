package me.lotabout.repl;

import lombok.extern.slf4j.Slf4j;
import me.lotabout.repl.struct.CalContext;
import me.lotabout.repl.struct.ExecutionException;

import java.util.Iterator;
import java.util.List;
import java.util.stream.Stream;

@Slf4j
public class REPL<T> {
    private final Tokenizer<T> tokenizer;
    private final Printer<T> printer;
    private final CalContext<T> calContext;

    public REPL(Tokenizer<T> tokenizer, Printer<T> printer) {
        this.tokenizer = tokenizer;
        this.printer = printer;
        this.calContext = new CalContext<>();
    }

    /**
     * Given a (finite/infinite) stream of lines, execute them one by one
     */
    public void executeLines(Stream<String> lines) {
        if (lines.isParallel()) {
            throw new IllegalArgumentException("input streams of REPL should not be parallel");
        }
        lines.forEach(this::executeLine);
    }

    /**
     * Given lines of inputs, execute them one by one
     */
    public void executeLines(List<String> lines) {
        executeLines(lines.stream());
    }

    /**
     * Given a line of inputs, tokenize the input line and execute operators parsed.
     * If any the operator fails, discard the rest of operators.
     *
     * @param line input line contains operands and operators
     */
    public void executeLine(String line) {
        Iterator<Operator<T>> iterator = tokenizer.tokenize(Stream.of(line)).iterator();
        while (iterator.hasNext()) {
            Operator<T> op = iterator.next();
            boolean success = this.execute(op);
            if (!success) {
                return;
            }
        }
    }

    /**
     * Execute an operator on REPL's context
     *
     * @param op the operator to be executed
     * @return true if op exist normally, false otherwise
     */
    public boolean execute(Operator<T> op) {
        if (op.needToSaveResult()) {
            this.calContext.checkpoint();
        }

        boolean exitNormally = true;
        try {
            op.execute(this.calContext);
        } catch (ExecutionException ex) {
            this.printer.printError(op, ex);
            exitNormally = false;
        } catch (Exception ex) {
            log.error("error on execute op[{}, pos: {}]", op.getName(), op.getPosition(), ex);
            exitNormally = false;
        } finally {
            this.printer.print(this.calContext);
        }

        return exitNormally;
    }
}
