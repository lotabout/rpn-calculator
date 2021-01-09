package me.lotabout.repl.struct;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

import java.util.Arrays;
import java.util.Collections;
import org.junit.Test;

public class CalcContextTest {

  @Test
  public void stackShouldWork() {
    CalcContext<Integer> calcContext = new CalcContext<>();
    assertFalse(calcContext.pop().isPresent());
    calcContext.push(1);
    calcContext.push(2);
    assertEquals(Arrays.asList(2, 1), calcContext.getStack());
  }

  @Test
  public void undoRedoShouldWork() {
    CalcContext<Integer> calcContext = new CalcContext<>();
    assertFalse(calcContext.pop().isPresent());
    calcContext.push(1);
    calcContext.push(2);
    calcContext.checkpoint();
    assertEquals(Arrays.asList(2, 1), calcContext.getStack());
    calcContext.pop();
    calcContext.push(3);
    calcContext.checkpoint();
    assertEquals(Arrays.asList(3, 1), calcContext.getStack());
    calcContext.push(4);
    calcContext.checkpoint();
    assertEquals(Arrays.asList(4, 3, 1), calcContext.getStack());

    calcContext.undo();
    assertEquals(Arrays.asList(4, 3, 1), calcContext.getStack());
    calcContext.undo();
    assertEquals(Arrays.asList(3, 1), calcContext.getStack());
    calcContext.undo();
    assertEquals(Arrays.asList(2, 1), calcContext.getStack());
    calcContext.undo();
    assertEquals(Collections.emptyList(), calcContext.getStack());
    calcContext.undo();
    assertEquals(Collections.emptyList(), calcContext.getStack());

    calcContext.redo();
    assertEquals(Arrays.asList(2, 1), calcContext.getStack());
    calcContext.redo();
    assertEquals(Arrays.asList(3, 1), calcContext.getStack());
    calcContext.redo();
    assertEquals(Arrays.asList(4, 3, 1), calcContext.getStack());
    calcContext.redo();
    assertEquals(Arrays.asList(4, 3, 1), calcContext.getStack());
  }
}
