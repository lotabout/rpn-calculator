package me.lotabout.rpn.repl;

import lombok.extern.slf4j.Slf4j;
import me.lotabout.rpn.repl.context.DefaultContext;
import me.lotabout.rpn.repl.context.REPLContext;
import me.lotabout.rpn.repl.struct.ExecutionException;
import me.lotabout.rpn.repl.struct.Token;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/** Read-Evaluate-Print-Loop, main entry for driving the calculation process */
@Slf4j
@Service
public class REPL {
  private final Tokenizer tokenizer;
  private final Formatter formatter;
  private final REPLContext REPLContext;
  private final Printer printer;

  @Autowired
  public REPL(Tokenizer tokenizer, Formatter formatter, Printer printer) {
    this(new DefaultContext(), tokenizer, formatter, printer);
  }

  public REPL(REPLContext context, Tokenizer tokenizer, Formatter formatter, Printer printer) {
    this.tokenizer = tokenizer;
    this.formatter = formatter;
    this.printer = printer;
    this.REPLContext = context;
  }

  /**
   * Given a line of inputs, tokenize the input line and execute operators parsed. If any the
   * operator fails, discard the rest of operators.
   *
   * @param line input line contains operands and operators
   */
  public void executeLine(String line) {
    for (Token token : tokenizer.tokenize(line)) {
      boolean success = this.execute(token);
      if (!success) {
        break;
      }
    }
    printer.print(this.formatter.formatContext(this.REPLContext));
  }

  /**
   * Execute an operator on REPL's context
   *
   * @param token the token to be executed
   * @return true if op exist normally, false otherwise
   */
  public boolean execute(Token token) {
    Operator op = token.getOperator();

    if (op != null && op.needToSaveResult()) {
      this.REPLContext.checkpoint();
    }

    boolean exitNormally = true;
    try {
      if (op == null) {
        throw new ExecutionException(
            String.format(
                "unknown operator[%s, pos: %d~%d]",
                token.getName(), token.getTokenStart(), token.getTokenEnd()));
      }
      op.execute(this.REPLContext);
    } catch (ExecutionException ex) {
      printer.print(this.formatter.formatError(token, ex));
      exitNormally = false;
    } catch (Exception ex) {
      log.error(
          "error on execute op[{}, pos: {}/{}]",
          token.getName(),
          token.getTokenStart(),
          token.getTokenEnd(),
          ex);
      exitNormally = false;
    }

    return exitNormally;
  }
}
