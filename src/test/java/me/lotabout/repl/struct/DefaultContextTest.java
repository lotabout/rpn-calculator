package me.lotabout.repl.struct;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

import java.util.Arrays;
import java.util.Collections;
import org.junit.Test;

public class DefaultContextTest {

  @Test
  public void stackShouldWork() {
    DefaultContext<Integer> defaultContext = new DefaultContext<>();
    assertFalse(defaultContext.pop().isPresent());
    defaultContext.push(1);
    defaultContext.push(2);
    assertEquals(Arrays.asList(2, 1), defaultContext.getStack());
  }

  @Test
  public void undoRedoShouldWork() {
    DefaultContext<Integer> defaultContext = new DefaultContext<>();
    assertFalse(defaultContext.pop().isPresent());
    defaultContext.push(1);
    defaultContext.push(2);
    defaultContext.checkpoint();
    assertEquals(Arrays.asList(2, 1), defaultContext.getStack());
    defaultContext.pop();
    defaultContext.push(3);
    defaultContext.checkpoint();
    assertEquals(Arrays.asList(3, 1), defaultContext.getStack());
    defaultContext.push(4);
    defaultContext.checkpoint();
    assertEquals(Arrays.asList(4, 3, 1), defaultContext.getStack());

    defaultContext.undo();
    assertEquals(Arrays.asList(4, 3, 1), defaultContext.getStack());
    defaultContext.undo();
    assertEquals(Arrays.asList(3, 1), defaultContext.getStack());
    defaultContext.undo();
    assertEquals(Arrays.asList(2, 1), defaultContext.getStack());
    defaultContext.undo();
    assertEquals(Collections.emptyList(), defaultContext.getStack());
    defaultContext.undo();
    assertEquals(Collections.emptyList(), defaultContext.getStack());

    defaultContext.redo();
    assertEquals(Arrays.asList(2, 1), defaultContext.getStack());
    defaultContext.redo();
    assertEquals(Arrays.asList(3, 1), defaultContext.getStack());
    defaultContext.redo();
    assertEquals(Arrays.asList(4, 3, 1), defaultContext.getStack());
    defaultContext.redo();
    assertEquals(Arrays.asList(4, 3, 1), defaultContext.getStack());
  }
}
