package me.lotabout.rpn.calculator.operator.math;

import static org.junit.Assert.assertEquals;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import me.lotabout.rpn.repl.struct.RealNumber;
import org.junit.Test;

public class PlusOpTest {

  @Test
  public void operandsNumberShouldBe2() {
    PlusOp op = new PlusOp();
    assertEquals(2, op.getNumberOfOperands());
  }

  @Test
  public void plusShouldBeCorrect() {
    PlusOp op = new PlusOp();

    List<RealNumber> operands =
        Stream.of(0.5, 2D).map(RealNumber::new).collect(Collectors.toList());
    RealNumber result = op.executeInner(operands);
    assertEquals(2.5D, result.getValue(), 1e-5);
  }
}
