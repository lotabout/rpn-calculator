package me.lotabout.calculator.operator;

import me.lotabout.calculator.RealNumber;
import me.lotabout.calculator.RealNumberOperator;
import me.lotabout.repl.struct.CalcContext;
import me.lotabout.repl.struct.ExecutionException;
import me.lotabout.repl.struct.TokenPos;

public class ClearOp extends PositionedOp implements RealNumberOperator {

  public ClearOp(TokenPos position) {
    super(position);
  }

  @Override
  public String getName() {
    return "clear";
  }

  @Override
  public void execute(CalcContext<RealNumber> calcCalcContext) throws ExecutionException {
    calcCalcContext.clear();
  }
}
