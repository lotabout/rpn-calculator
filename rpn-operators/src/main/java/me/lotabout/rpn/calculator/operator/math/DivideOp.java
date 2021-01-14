package me.lotabout.rpn.calculator.operator.math;

import java.util.List;
import me.lotabout.rpn.calculator.operator.ArithmeticOp;
import me.lotabout.rpn.repl.struct.ExecutionException;
import me.lotabout.rpn.repl.struct.RealNumber;
import org.springframework.stereotype.Component;

@Component
public class DivideOp extends ArithmeticOp {

  @Override
  protected int getNumberOfOperands() {
    return 2;
  }

  @Override
  protected RealNumber executeInner(List<RealNumber> operands) throws ExecutionException {
    return new RealNumber(operands.get(1).getValue().divide(operands.get(0).getValue()));
  }

  @Override
  public String getName() {
    return "/";
  }
}
