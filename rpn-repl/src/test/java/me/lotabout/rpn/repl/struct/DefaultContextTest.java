package me.lotabout.rpn.repl.struct;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

import java.util.Arrays;
import me.lotabout.rpn.repl.context.DefaultContext;
import org.junit.Test;

public class DefaultContextTest {

  @Test
  public void stackShouldWork() {
    DefaultContext defaultContext = new DefaultContext();
    assertFalse(defaultContext.pop().isPresent());
    defaultContext.push(RealNumber.valueOf(1));
    defaultContext.push(RealNumber.valueOf(2));
    assertEquals(
        Arrays.asList(RealNumber.valueOf(2), RealNumber.valueOf(1)), defaultContext.getStack());
  }
}
