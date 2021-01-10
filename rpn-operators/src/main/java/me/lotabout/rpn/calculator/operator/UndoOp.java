package me.lotabout.rpn.calculator.operator;

import me.lotabout.rpn.calculator.operator.impl.HistoryOp;
import me.lotabout.rpn.repl.context.HistoryContext;
import me.lotabout.rpn.repl.struct.TokenPos;

public class UndoOp extends HistoryOp {

  public UndoOp(TokenPos position) {
    super(position);
  }

  @Override
  protected void executeInner(HistoryContext context) {
    context.undo();
  }

  @Override
  public String getName() {
    return "undo";
  }
}
