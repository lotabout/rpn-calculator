package me.lotabout.rpn.calculator.operator.math;

import static org.junit.Assert.assertEquals;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import me.lotabout.rpn.calculator.operator.RealNumber;
import me.lotabout.rpn.repl.struct.TokenPos;
import org.junit.Test;

public class DivideOpTest {

  @Test
  public void operandsNumberShouldBe2() {
    DivideOp op = new DivideOp(new TokenPos(0, 1));
    assertEquals(2, op.getNumberOfOperands());
  }

  @Test
  public void divideOrder() {
    DivideOp op = new DivideOp(new TokenPos(0, 1));

    // stack top: [1, 2]
    // => 2 / 1
    List<RealNumber> operands = Stream.of(1, 2).map(RealNumber::new).collect(Collectors.toList());
    RealNumber result = op.executeInner(operands);
    assertEquals(2D, result.getValue(), 1e-5);
  }

  @Test(expected = ArithmeticException.class)
  public void divideZero() {
    DivideOp op = new DivideOp(new TokenPos(0, 1));
    List<RealNumber> operands = Stream.of(0, 2).map(RealNumber::new).collect(Collectors.toList());
    RealNumber result = op.executeInner(operands);
  }
}
