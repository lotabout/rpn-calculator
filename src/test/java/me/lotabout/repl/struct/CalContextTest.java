package me.lotabout.repl.struct;

import me.lotabout.repl.struct.CalContext;
import org.junit.Test;

import java.util.Arrays;
import java.util.Collections;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

public class CalContextTest {

    @Test
    public void stackShouldWork() {
        CalContext<Integer> calContext = new CalContext<>();
        assertFalse(calContext.pop().isPresent());
        calContext.push(1);
        calContext.push(2);
        assertEquals(Arrays.asList(2, 1), calContext.getStack());
    }

    @Test
    public void undoRedoShouldWork() {
        CalContext<Integer> calContext = new CalContext<>();
        assertFalse(calContext.pop().isPresent());
        calContext.push(1);
        calContext.push(2);
        calContext.checkpoint();
        assertEquals(Arrays.asList(2, 1), calContext.getStack());
        calContext.pop();
        calContext.push(3);
        calContext.checkpoint();
        assertEquals(Arrays.asList(3, 1), calContext.getStack());
        calContext.push(4);
        calContext.checkpoint();
        assertEquals(Arrays.asList(4, 3, 1), calContext.getStack());

        calContext.undo();
        assertEquals(Arrays.asList(4, 3, 1), calContext.getStack());
        calContext.undo();
        assertEquals(Arrays.asList(3, 1), calContext.getStack());
        calContext.undo();
        assertEquals(Arrays.asList(2, 1), calContext.getStack());
        calContext.undo();
        assertEquals(Collections.emptyList(), calContext.getStack());
        calContext.undo();
        assertEquals(Collections.emptyList(), calContext.getStack());

        calContext.redo();
        assertEquals(Arrays.asList(2, 1), calContext.getStack());
        calContext.redo();
        assertEquals(Arrays.asList(3, 1), calContext.getStack());
        calContext.redo();
        assertEquals(Arrays.asList(4, 3, 1), calContext.getStack());
        calContext.redo();
        assertEquals(Arrays.asList(4, 3, 1), calContext.getStack());

    }

}