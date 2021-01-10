package me.lotabout.rpn.calculator.operator.impl;

import me.lotabout.rpn.calculator.operator.RealNumber;
import me.lotabout.rpn.calculator.operator.RealNumberOperator;
import me.lotabout.rpn.repl.context.HistoryContext;
import me.lotabout.rpn.repl.context.REPLContext;
import me.lotabout.rpn.repl.struct.ExecutionException;
import me.lotabout.rpn.repl.struct.TokenPos;

public abstract class HistoryOp extends PositionedOp implements RealNumberOperator {
  protected HistoryOp(TokenPos position) {
    super(position);
  }

  protected abstract void executeInner(HistoryContext context);

  @Override
  public boolean needToSaveResult() {
    return false;
  }

  @Override
  public void execute(REPLContext<RealNumber> calcREPLContext) throws ExecutionException {
    executeInner(calcREPLContext);
  }
}
