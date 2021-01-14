package me.lotabout.rpn.calculator.operator.math;

import ch.obermuhlner.math.big.BigDecimalMath;
import java.util.List;
import me.lotabout.rpn.calculator.operator.ArithmeticOp;
import me.lotabout.rpn.repl.struct.ExecutionException;
import me.lotabout.rpn.repl.struct.RealNumber;
import org.springframework.stereotype.Component;

@Component
public class SqrtOp extends ArithmeticOp {

  @Override
  protected int getNumberOfOperands() {
    return 1;
  }

  @Override
  protected RealNumber executeInner(List<RealNumber> operands) throws ExecutionException {
    return new RealNumber(BigDecimalMath.sqrt(operands.get(0).getValue(), MATH_CONTEXT));
  }

  @Override
  public String getName() {
    return "sqrt";
  }
}
