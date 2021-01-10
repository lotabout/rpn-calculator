package me.lotabout.rpn.calculator.operator.stackops;

import static org.junit.Assert.assertEquals;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import me.lotabout.rpn.calculator.RealNumber;
import me.lotabout.rpn.calculator.operator.stackops.MinusOp;
import me.lotabout.rpn.repl.struct.TokenPos;
import org.junit.Test;

public class MinusOpTest {

  @Test
  public void operandsNumberShouldBe2() {
    MinusOp op = new MinusOp(new TokenPos(0, 1));
    assertEquals(2, op.getNumberOfOperands());
  }

  @Test
  public void minusOrder() {
    MinusOp op = new MinusOp(new TokenPos(0, 1));

    // stack top: [1, 2]
    // => 2 - 1
    List<RealNumber> operands = Stream.of(1, 2).map(RealNumber::new).collect(Collectors.toList());
    RealNumber result = op.executeInner(operands);
    assertEquals(1D, result.getValue(), 1e-5);
  }
}
