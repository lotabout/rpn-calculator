package me.lotabout.rpn.calculator.operator;

import me.lotabout.rpn.repl.Operator;
import me.lotabout.rpn.repl.context.HistoryContext;
import me.lotabout.rpn.repl.context.REPLContext;
import me.lotabout.rpn.repl.struct.ExecutionException;

public abstract class HistoryOp implements Operator {

  protected abstract void executeInner(HistoryContext context);

  @Override
  public boolean needToSaveResult() {
    return false;
  }

  @Override
  public void execute(REPLContext calcREPLContext) throws ExecutionException {
    executeInner(calcREPLContext);
  }
}
