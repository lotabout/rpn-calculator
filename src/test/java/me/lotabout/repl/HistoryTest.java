package me.lotabout.repl;

import org.junit.Test;

import static org.junit.Assert.*;

public class HistoryTest {

    @Test
    public void emptyItemShouldReturnEmpty() {
        History<Integer> history = new History<>(8);
        assertFalse(history.undo().isPresent());
        assertFalse(history.redo().isPresent());
    }

    @Test
    public void historyCouldBeUndo() {
        History<Integer> history = new History<>(8);
        history.save(1);
        history.save(2);
        history.save(3);
        assertEquals(3, (int) history.undo().get());
        assertEquals(2, (int) history.undo().get());
        assertEquals(2, (int) history.redo().get());
        assertEquals(2, (int) history.undo().get());
        assertEquals(1, (int) history.undo().get());
        assertFalse(history.undo().isPresent());
        assertEquals(1, (int) history.redo().get());
        assertEquals(2, (int) history.redo().get());
        assertEquals(3, (int) history.redo().get());
        assertFalse(history.redo().isPresent());
    }

    @Test
    public void earliestItemShouldBeEvicted() {
        History<Integer> history = new History<>(2);
        history.save(1);
        history.save(2);
        history.save(3);
        assertEquals(3, (int) history.undo().get());
        assertEquals(2, (int) history.undo().get());
        assertFalse(history.undo().isPresent());
    }

    @Test
    public void clearShouldRemoveAllItems() {
        History<Integer> history = new History<>(2);
        history.save(1);
        history.save(2);
        history.clear();
        assertFalse(history.undo().isPresent());
        assertFalse(history.redo().isPresent());
    }

}