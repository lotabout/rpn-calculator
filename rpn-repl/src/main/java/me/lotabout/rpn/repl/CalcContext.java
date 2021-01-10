package me.lotabout.rpn.repl;

import java.util.Optional;

public interface CalcContext<T> {
  /** Get the inner stack, the last in item comes first */
  Iterable<T> getStack();

  /** pop out the item on the top of the stack */
  Optional<T> pop();

  /** push the item on the top of the stack */
  void push(T element);

  /** Save the current stack status for later undo/redo */
  void checkpoint();

  /** Clear all saved checkpoint */
  void clear();

  /** set the stack to the latest saved history */
  void undo();

  /** undo the previous `undo` operation */
  void redo();
}
