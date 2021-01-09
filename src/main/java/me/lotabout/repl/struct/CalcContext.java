package me.lotabout.repl.struct;

import java.util.Optional;
import org.pcollections.ConsPStack;
import org.pcollections.PStack;

public class CalcContext<T> {
  private final History<PStack<T>> history;
  private PStack<T> stack; // an immutable stack

  public CalcContext() {
    this(1024);
  }

  public CalcContext(int historyCapacity) {
    this.history = new History<>(historyCapacity);
    this.stack = ConsPStack.empty();
  }

  /** Get the inner stack, the last in item comes first */
  public Iterable<T> getStack() {
    return stack;
  }

  /** pop out the item on the top of the stack */
  public Optional<T> pop() {
    if (this.stack.isEmpty()) {
      return Optional.empty();
    }

    T ret = this.stack.get(0);
    this.stack = this.stack.minus(0);
    return Optional.of(ret);
  }

  /** push the item on the top of the stack */
  public void push(T element) {
    this.stack = this.stack.plus(element);
  }

  /** Save the current stack status for later undo/redo */
  public void checkpoint() {
    this.history.save(this.stack);
  }

  /** Clear all saved checkpoint */
  public void clear() {
    this.stack = ConsPStack.empty();
    this.history.clear();
  }

  /** set the stack to the latest saved history */
  public void undo() {
    this.stack = this.history.undo().orElseGet(ConsPStack::empty);
  }

  /** undo the previous `undo` operation */
  public void redo() {
    this.history.redo().ifPresent(stack -> this.stack = stack);
  }
}