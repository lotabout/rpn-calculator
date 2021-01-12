package me.lotabout.rpn.repl.context;

import java.util.List;
import java.util.Optional;
import me.lotabout.rpn.repl.struct.RealNumber;
import org.pcollections.ConsPStack;
import org.pcollections.PStack;

public class DefaultContext implements REPLContext {
  private final History<PStack<RealNumber>> history;
  private PStack<RealNumber> stack; // an immutable stack

  public DefaultContext() {
    this(1024);
  }

  public DefaultContext(int historyCapacity) {
    this.history = new History<>(historyCapacity);
    this.stack = ConsPStack.empty();
  }

  @Override
  public List<RealNumber> getStack() {
    return stack;
  }

  @Override
  public Optional<RealNumber> pop() {
    if (this.stack.isEmpty()) {
      return Optional.empty();
    }

    RealNumber ret = this.stack.get(0);
    this.stack = this.stack.minus(0);
    return Optional.of(ret);
  }

  @Override
  public void push(RealNumber element) {
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
