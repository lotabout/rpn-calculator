package me.lotabout.rpn.calculator.operator;

import me.lotabout.rpn.calculator.RealNumber;
import me.lotabout.rpn.calculator.RealNumberOperator;
import me.lotabout.rpn.repl.CalcContext;
import me.lotabout.rpn.repl.struct.ExecutionException;
import me.lotabout.rpn.repl.struct.TokenPos;

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
