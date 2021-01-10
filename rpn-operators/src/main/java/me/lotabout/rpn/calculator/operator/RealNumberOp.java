package me.lotabout.rpn.calculator.operator;

import me.lotabout.rpn.calculator.RealNumber;
import me.lotabout.rpn.repl.CalcContext;
import me.lotabout.rpn.repl.struct.ExecutionException;
import me.lotabout.rpn.repl.struct.TokenPos;

public class RealNumberOp extends PositionedOp {
  private final RealNumber realNumber;

  public RealNumberOp(String numberString, TokenPos tokenPos) {
    super(tokenPos);
    this.realNumber = new RealNumber(Double.parseDouble(numberString));
  }

  @Override
  public String getName() {
    return "Number";
  }

  @Override
  public void execute(CalcContext<RealNumber> calcCalcContext) throws ExecutionException {
    calcCalcContext.push(realNumber);
  }
}
