package me.lotabout.repl;

import java.util.Iterator;
import java.util.List;
import java.util.stream.Stream;
import lombok.extern.slf4j.Slf4j;
import me.lotabout.repl.struct.CalcContext;
import me.lotabout.repl.struct.ExecutionException;

@Slf4j
public class REPL<T> {
  private final Tokenizer<T> tokenizer;
  private final Printer<T> printer;
  private final CalcContext<T> calcContext;
  private final OutputConsumer outputConsumer;

  public REPL(Tokenizer<T> tokenizer, Printer<T> printer, OutputConsumer outputConsumer) {
    this.tokenizer = tokenizer;
    this.printer = printer;
    this.outputConsumer = outputConsumer;
    this.calcContext = new CalcContext<>();
  }

  /** Given a (finite/infinite) stream of lines, execute them one by one */
  public void executeLines(Stream<String> lines) {
    if (lines.isParallel()) {
      throw new IllegalArgumentException("input streams of REPL should not be parallel");
    }
    lines.forEach(this::executeLine);
  }

  /** Given lines of inputs, execute them one by one */
  public void executeLines(List<String> lines) {
    executeLines(lines.stream());
  }

  /**
   * Given a line of inputs, tokenize the input line and execute operators parsed. If any the
   * operator fails, discard the rest of operators.
   *
   * @param line input line contains operands and operators
   */
  public void executeLine(String line) {
    Iterator<Operator<T>> iterator = tokenizer.tokenize(Stream.of(line)).iterator();
    while (iterator.hasNext()) {
      Operator<T> op = iterator.next();
      boolean success = this.execute(op);
      if (!success) {
        break;
      }
    }
    outputConsumer.consume(this.printer.printContext(this.calcContext));
  }

  /**
   * Execute an operator on REPL's context
   *
   * @param op the operator to be executed
   * @return true if op exist normally, false otherwise
   */
  public boolean execute(Operator<T> op) {
    if (op.needToSaveResult()) {
      this.calcContext.checkpoint();
    }

    boolean exitNormally = true;
    try {
      op.execute(this.calcContext);
    } catch (ExecutionException ex) {
      outputConsumer.consume(this.printer.printError(op, ex));
      exitNormally = false;
    } catch (Exception ex) {
      log.error("error on execute op[{}, pos: {}]", op.getName(), op.getPosition(), ex);
      exitNormally = false;
    }

    return exitNormally;
  }
}
