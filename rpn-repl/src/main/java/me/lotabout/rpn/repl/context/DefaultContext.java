package me.lotabout.rpn.repl.context;

import java.util.List;
import java.util.Optional;
import me.lotabout.rpn.repl.struct.RealNumber;
import org.pcollections.ConsPStack;
import org.pcollections.PStack;

public class DefaultContext implements REPLContext<RealNumber, PStack<RealNumber>> {

  // Note: PStack is an immutable class. It's save to pass it around, store, restore, ...
  private PStack<RealNumber> stack;

  public DefaultContext() {
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
  public void clear() {
    this.stack = ConsPStack.empty();
  }

  @Override
  public PStack<RealNumber> snapshot() {
    return this.stack;
  }

  @Override
  public void restoreFrom(PStack<RealNumber> snapshot) {
    this.stack = snapshot;
  }
}
