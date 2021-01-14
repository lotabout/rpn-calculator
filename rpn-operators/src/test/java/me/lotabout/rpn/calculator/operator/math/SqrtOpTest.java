package me.lotabout.rpn.calculator.operator.math;

import static org.junit.Assert.assertEquals;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import me.lotabout.rpn.repl.struct.RealNumber;
import org.junit.Test;

public class SqrtOpTest {

  @Test
  public void operandsNumberShouldBe1() {
    SqrtOp op = new SqrtOp();
    assertEquals(1, op.getNumberOfOperands());
  }

  @Test
  public void plusShouldBeCorrect() {
    SqrtOp op = new SqrtOp();

    List<RealNumber> operands = Stream.of(2).map(RealNumber::valueOf).collect(Collectors.toList());
    RealNumber result = op.executeInner(operands);
    assertEquals("1.4142135623", result.toString());
  }
}
