package me.lotabout.rpn.repl;

import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;
import lombok.extern.slf4j.Slf4j;
import me.lotabout.rpn.repl.context.History;
import me.lotabout.rpn.repl.context.REPLContext;
import me.lotabout.rpn.repl.struct.ExecutionException;
import me.lotabout.rpn.repl.struct.RealNumber;
import me.lotabout.rpn.repl.token.Token;
import org.pcollections.PStack;

/** Read-Evaluate-Print-Loop, main entry for driving the calculation process */
@Slf4j
public class REPL {
  private final Tokenizer tokenizer;
  private final Formatter<RealNumber> formatter;
  private final REPLContext<RealNumber, PStack<RealNumber>> context;
  private final History<PStack<RealNumber>> history;
  private final Printer printer;
  private final Map<String, Operator<RealNumber>> mathOps;

  public REPL(
      REPLContext<RealNumber, PStack<RealNumber>> context,
      Tokenizer tokenizer,
      Formatter<RealNumber> formatter,
      Printer printer,
      int historyCapacity,
      List<Operator<RealNumber>> mathOps) {
    this.tokenizer = tokenizer;
    this.formatter = formatter;
    this.printer = printer;
    this.context = context;
    this.history = new History<>(historyCapacity);
    this.mathOps =
        mathOps.stream().collect(Collectors.toMap(Operator::getName, Function.identity()));
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
    printer.print(this.formatter.formatContext(this.context));
  }

  /**
   * Execute an operator on REPL's context
   *
   * @param token the token to be executed
   * @return true if op exist normally, false otherwise
   */
  public boolean execute(Token token) {
    boolean saveHistory = !token.getTokenType().isHistoryToken();
    boolean exitNormally;

    try {
      if (saveHistory) {
        this.history.save(this.context.snapshot());
      }

      switch (token.getTokenType()) {
        case NUMBER:
          pushNumber(token);
          break;
        case UNDO:
          undo(token);
          break;
        case REDO:
          redo(token);
          break;
        case CLEAR:
          clear(token);
          break;
        case MATH:
          math(token);
          break;
      }
      exitNormally = true;
    } catch (ExecutionException ex) {
      printer.print(this.formatter.formatError(token, ex));
      exitNormally = false;
    } catch (Exception ex) {
      log.error(
          "error on execute op[{}, pos: {}/{}]",
          token.getTokenType(),
          token.getTokenStart(),
          token.getTokenEnd(),
          ex);
      exitNormally = false;
    }

    if (!exitNormally && saveHistory) {
      this.history.undo();
    }
    return exitNormally;
  }

  private void math(Token token) {
    // retrieve math operator
    Operator<RealNumber> op = mathOps.get(token.getContent());
    if (op == null) {
      String error =
          String.format("Unknown op[%s], pos: %s", token.getContent(), token.getTokenStart());
      throw new ExecutionException(error);
    }

    op.execute(this.context);
  }

  private void clear(Token _token) {
    this.history.clear();
    this.context.clear();
  }

  private void undo(Token _token) {
    this.history.undo().ifPresent(this.context::restoreFrom);
  }

  private void redo(Token _token) {
    this.history.redo().ifPresent(this.context::restoreFrom);
  }

  private void pushNumber(Token token) {
    RealNumber realNumber = RealNumber.valueOf(token.getContent());
    this.context.push(realNumber);
  }
}
