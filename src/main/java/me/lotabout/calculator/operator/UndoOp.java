package me.lotabout.calculator.operator;

import me.lotabout.calculator.RealNumber;
import me.lotabout.calculator.RealNumberOperator;
import me.lotabout.repl.struct.CalcContext;
import me.lotabout.repl.struct.ExecutionException;
import me.lotabout.repl.struct.TokenPos;

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
