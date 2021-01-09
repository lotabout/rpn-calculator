package me.lotabout.repl.struct;

import java.util.Optional;
import me.lotabout.repl.CalcContext;
import org.pcollections.ConsPStack;
import org.pcollections.PStack;

public class DefaultContext<T> implements CalcContext<T> {
  private final History<PStack<T>> history;
  private PStack<T> stack; // an immutable stack

  public DefaultContext() {
    this(1024);
  }

  public DefaultContext(int historyCapacity) {
    this.history = new History<>(historyCapacity);
    this.stack = ConsPStack.empty();
  }

  @Override
  public Iterable<T> getStack() {
    return stack;
  }

  @Override
  public Optional<T> pop() {
    if (this.stack.isEmpty()) {
      return Optional.empty();
    }

    T ret = this.stack.get(0);
    this.stack = this.stack.minus(0);
    return Optional.of(ret);
  }

  @Override
  public void push(T element) {
    this.stack = this.stack.plus(element);
  }

  @Override
  public void checkpoint() {
    this.history.save(this.stack);
  }

  @Override
  public void clear() {
    this.stack = ConsPStack.empty();
    this.history.clear();
  }

  @Override
  public void undo() {
    this.stack = this.history.undo().orElseGet(ConsPStack::empty);
  }

  @Override
  public void redo() {
    this.history.redo().ifPresent(stack -> this.stack = stack);
  }
}
