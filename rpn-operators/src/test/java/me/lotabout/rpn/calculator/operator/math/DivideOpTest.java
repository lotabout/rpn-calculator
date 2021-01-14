package me.lotabout.rpn.calculator.operator.math;

import static org.junit.Assert.assertEquals;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import me.lotabout.rpn.repl.struct.RealNumber;
import org.junit.Test;

public class DivideOpTest {

  @Test
  public void operandsNumberShouldBe2() {
    DivideOp op = new DivideOp();
    assertEquals(2, op.getNumberOfOperands());
  }

  @Test
  public void divideOrder() {
    DivideOp op = new DivideOp();

    // stack top: [1, 2]
    // => 2 / 1
    List<RealNumber> operands =
        Stream.of(1, 2).map(RealNumber::valueOf).collect(Collectors.toList());
    RealNumber result = op.executeInner(operands);
    assertEquals(2, result.getValue().intValue());
  }

  @Test(expected = ArithmeticException.class)
  public void divideZero() {
    DivideOp op = new DivideOp();
    List<RealNumber> operands =
        Stream.of(0, 2).map(RealNumber::valueOf).collect(Collectors.toList());
    RealNumber result = op.executeInner(operands);
  }
}
