package me.lotabout.rpn.calculator.operator.math;

import java.util.List;
import me.lotabout.rpn.calculator.operator.RealNumber;
import me.lotabout.rpn.calculator.operator.impl.ArithmeticOp;
import me.lotabout.rpn.repl.struct.ExecutionException;
import me.lotabout.rpn.repl.struct.TokenPos;

public class MinusOp extends ArithmeticOp {
  public MinusOp(TokenPos position) {
    super(position);
  }

  @Override
  protected int getNumberOfOperands() {
    return 2;
  }

  @Override
  protected RealNumber executeInner(List<RealNumber> operands) throws ExecutionException {
    return new RealNumber(operands.get(1).getValue() - operands.get(0).getValue());
  }

  @Override
  public String getName() {
    return "-";
  }
}
