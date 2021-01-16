package me.lotabout.rpn.repl.context;

import java.util.List;
import java.util.Optional;

public interface CalcContext<T> {
  /** Get the inner stack, the last in item comes first */
  List<T> getStack();

  /** pop out the item on the top of the stack */
  Optional<T> pop();

  /** push the item on the top of the stack */
  void push(T element);

  /** clear all the items */
  void clear();
}
