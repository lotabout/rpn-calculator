package me.lotabout.rpn.calculator.operator;

import me.lotabout.rpn.calculator.operator.impl.HistoryOp;
import me.lotabout.rpn.repl.context.HistoryContext;
import me.lotabout.rpn.repl.struct.ExecutionException;
import me.lotabout.rpn.repl.struct.TokenPos;

public class ClearOp extends HistoryOp {

  public ClearOp(TokenPos position) {
    super(position);
  }

  @Override
  public String getName() {
    return "clear";
  }

  @Override
  public void executeInner(HistoryContext context) throws ExecutionException {
    context.clear();
  }
}
