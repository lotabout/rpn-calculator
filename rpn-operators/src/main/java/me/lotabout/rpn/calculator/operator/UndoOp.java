package me.lotabout.rpn.calculator.operator;

import me.lotabout.rpn.calculator.RealNumber;
import me.lotabout.rpn.calculator.RealNumberOperator;
import me.lotabout.rpn.repl.CalcContext;
import me.lotabout.rpn.repl.struct.ExecutionException;
import me.lotabout.rpn.repl.struct.TokenPos;

public class UndoOp extends PositionedOp implements RealNumberOperator {

  public UndoOp(TokenPos position) {
    super(position);
  }

  @Override
  public String getName() {
    return "undo";
  }

  @Override
  public void execute(CalcContext<RealNumber> calcCalcContext) throws ExecutionException {
    calcCalcContext.undo();
  }

  @Override
  public boolean needToSaveResult() {
    return false;
  }
}
