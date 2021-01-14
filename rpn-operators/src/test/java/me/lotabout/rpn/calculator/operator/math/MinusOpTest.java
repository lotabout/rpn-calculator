package me.lotabout.rpn.calculator.operator.math;

import static org.junit.Assert.assertEquals;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import me.lotabout.rpn.repl.struct.RealNumber;
import org.junit.Test;

public class MinusOpTest {

  @Test
  public void operandsNumberShouldBe2() {
    MinusOp op = new MinusOp();
    assertEquals(2, op.getNumberOfOperands());
  }

  @Test
  public void minusOrder() {
    MinusOp op = new MinusOp();

    // stack top: [1, 2]
    // => 2 - 1
    List<RealNumber> operands =
        Stream.of(1, 2).map(RealNumber::valueOf).collect(Collectors.toList());
    RealNumber result = op.executeInner(operands);
    assertEquals(1, result.getValue().intValue());
  }
}
