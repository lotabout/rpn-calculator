package me.lotabout.calculator.operator;

import me.lotabout.calculator.RealNumber;
import me.lotabout.repl.struct.CalcContext;
import me.lotabout.repl.struct.ExecutionException;
import me.lotabout.repl.struct.TokenPos;

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
