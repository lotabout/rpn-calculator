package me.lotabout.rpn.repl.context;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.Optional;

/** A bounded ring buffer for saving history and recovering from history (LILO). */
public class History<T> {
  private final int capacity;
  private final Deque<T> undoDeque; // LILO
  private final Deque<T> redoDeque; // LILO

  public History(int capacity) {
    if (capacity <= 0) {
      throw new IllegalArgumentException("capacity should be greater than 0, given " + capacity);
    }
    undoDeque = new ArrayDeque<>(capacity);
    redoDeque = new ArrayDeque<>(capacity);
    this.capacity = capacity;
  }

  /**
   * Save an item. Will evict the earliest item if the capacity of the history is reached.
   *
   * @param element The new element.
   */
  public void save(T element) {
    if (capacity <= undoDeque.size()) {
      undoDeque.pollFirst();
    }

    undoDeque.offerLast(element);
    redoDeque.clear();
  }

  /**
   * Pop out the last saved item and put the item into redo deque for later redo.
   *
   * @return empty if no history is saved, the last saved item otherwise.
   */
  public Optional<T> undo() {
    if (undoDeque.isEmpty()) {
      return Optional.empty();
    }

    T element = undoDeque.pollLast();
    redoDeque.offerLast(element);
    return Optional.of(element);
  }

  /**
   * Pop out the last undo item and put the item into undo deque.
   *
   * @return empty if no previous undo operation, the last undo item otherwise .
   */
  public Optional<T> redo() {
    if (redoDeque.isEmpty()) {
      return Optional.empty();
    }

    T element = redoDeque.pollLast();
    undoDeque.offerLast(element);
    return Optional.of(element);
  }

  public void clear() {
    this.undoDeque.clear();
    this.redoDeque.clear();
  }
}
