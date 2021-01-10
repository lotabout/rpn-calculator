package me.lotabout.rpn.calculator.operator.impl;

import me.lotabout.rpn.calculator.operator.RealNumber;
import me.lotabout.rpn.repl.context.REPLContext;
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
  public void execute(REPLContext<RealNumber> calcREPLContext) throws ExecutionException {
    calcREPLContext.push(realNumber);
  }
}
