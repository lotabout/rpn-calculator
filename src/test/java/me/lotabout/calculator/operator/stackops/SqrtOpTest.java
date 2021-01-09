package me.lotabout.calculator.operator.stackops;

import static org.junit.Assert.assertEquals;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import me.lotabout.calculator.RealNumber;
import me.lotabout.repl.struct.TokenPos;
import org.junit.Test;

public class SqrtOpTest {

  @Test
  public void operandsNumberShouldBe1() {
    SqrtOp op = new SqrtOp(new TokenPos(0, 1));
    assertEquals(1, op.getNumberOfOperands());
  }

  @Test
  public void plusShouldBeCorrect() {
    SqrtOp op = new SqrtOp(new TokenPos(0, 1));

    List<RealNumber> operands = Stream.of(2D).map(RealNumber::new).collect(Collectors.toList());
    RealNumber result = op.executeInner(operands);
    assertEquals(1.4142135623D, result.getValue(), 1e-5);
  }
}
