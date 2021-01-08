package me.lotabout.repl;

import org.junit.Test;

import java.util.Arrays;
import java.util.Collections;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

public class ContextTest {

    @Test
    public void stackShouldWork() {
        Context<Integer> context = new Context<>();
        assertFalse(context.pop().isPresent());
        context.push(1);
        context.push(2);
        assertEquals(Arrays.asList(2, 1), context.getStack());
    }

    @Test
    public void undoRedoShouldWork() {
        Context<Integer> context = new Context<>();
        assertFalse(context.pop().isPresent());
        context.push(1);
        context.push(2);
        context.checkpoint();
        assertEquals(Arrays.asList(2, 1), context.getStack());
        context.pop();
        context.push(3);
        context.checkpoint();
        assertEquals(Arrays.asList(3, 1), context.getStack());
        context.push(4);
        context.checkpoint();
        assertEquals(Arrays.asList(4, 3, 1), context.getStack());

        context.undo();
        assertEquals(Arrays.asList(4, 3, 1), context.getStack());
        context.undo();
        assertEquals(Arrays.asList(3, 1), context.getStack());
        context.undo();
        assertEquals(Arrays.asList(2, 1), context.getStack());
        context.undo();
        assertEquals(Collections.emptyList(), context.getStack());
        context.undo();
        assertEquals(Collections.emptyList(), context.getStack());

        context.redo();
        assertEquals(Arrays.asList(2, 1), context.getStack());
        context.redo();
        assertEquals(Arrays.asList(3, 1), context.getStack());
        context.redo();
        assertEquals(Arrays.asList(4, 3, 1), context.getStack());
        context.redo();
        assertEquals(Arrays.asList(4, 3, 1), context.getStack());

    }

}