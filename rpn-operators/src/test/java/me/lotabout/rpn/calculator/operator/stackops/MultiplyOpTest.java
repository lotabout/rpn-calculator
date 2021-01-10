package me.lotabout.rpn.calculator.operator.stackops;

import static org.junit.Assert.*;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import me.lotabout.rpn.calculator.RealNumber;
import me.lotabout.rpn.calculator.operator.stackops.MultiplyOp;
import me.lotabout.rpn.repl.struct.TokenPos;
import org.junit.Test;

public class MultiplyOpTest {

  @Test
  public void operandsNumberShouldBe2() {
    MultiplyOp op = new MultiplyOp(new TokenPos(0, 1));
    assertEquals(2, op.getNumberOfOperands());
  }

  @Test
  public void multiplyShouldBeCorrect() {
    MultiplyOp op = new MultiplyOp(new TokenPos(0, 1));

    List<RealNumber> operands =
        Stream.of(0.5, 2D).map(RealNumber::new).collect(Collectors.toList());
    RealNumber result = op.executeInner(operands);
    assertEquals(1D, result.getValue(), 1e-5);
  }
}
