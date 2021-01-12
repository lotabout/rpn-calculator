package me.lotabout.rpn.repl.struct;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

import java.util.Arrays;
import java.util.Collections;
import me.lotabout.rpn.repl.context.DefaultContext;
import org.junit.Test;

public class DefaultContextTest {

  @Test
  public void stackShouldWork() {
    DefaultContext defaultContext = new DefaultContext();
    assertFalse(defaultContext.pop().isPresent());
    defaultContext.push(new RealNumber(1));
    defaultContext.push(new RealNumber(2));
    assertEquals(Arrays.asList(new RealNumber(2), new RealNumber(1)), defaultContext.getStack());
  }

  @Test
  public void undoRedoShouldWork() {
    DefaultContext defaultContext = new DefaultContext();
    assertFalse(defaultContext.pop().isPresent());
    defaultContext.push(new RealNumber(1));
    defaultContext.push(new RealNumber(2));
    defaultContext.checkpoint();
    assertEquals(Arrays.asList(new RealNumber(2), new RealNumber(1)), defaultContext.getStack());
    defaultContext.pop();
    defaultContext.push(new RealNumber(3));
    defaultContext.checkpoint();
    assertEquals(Arrays.asList(new RealNumber(3), new RealNumber(1)), defaultContext.getStack());
    defaultContext.push(new RealNumber(4));
    defaultContext.checkpoint();
    assertEquals(
        Arrays.asList(new RealNumber(4), new RealNumber(3), new RealNumber(1)),
        defaultContext.getStack());

    defaultContext.undo();
    assertEquals(
        Arrays.asList(new RealNumber(4), new RealNumber(3), new RealNumber(1)),
        defaultContext.getStack());
    defaultContext.undo();
    assertEquals(Arrays.asList(new RealNumber(3), new RealNumber(1)), defaultContext.getStack());
    defaultContext.undo();
    assertEquals(Arrays.asList(new RealNumber(2), new RealNumber(1)), defaultContext.getStack());
    defaultContext.undo();
    assertEquals(Collections.emptyList(), defaultContext.getStack());
    defaultContext.undo();
    assertEquals(Collections.emptyList(), defaultContext.getStack());

    defaultContext.redo();
    assertEquals(Arrays.asList(new RealNumber(2), new RealNumber(1)), defaultContext.getStack());
    defaultContext.redo();
    assertEquals(Arrays.asList(new RealNumber(3), new RealNumber(1)), defaultContext.getStack());
    defaultContext.redo();
    assertEquals(
        Arrays.asList(new RealNumber(4), new RealNumber(3), new RealNumber(1)),
        defaultContext.getStack());
    defaultContext.redo();
    assertEquals(
        Arrays.asList(new RealNumber(4), new RealNumber(3), new RealNumber(1)),
        defaultContext.getStack());
  }
}
