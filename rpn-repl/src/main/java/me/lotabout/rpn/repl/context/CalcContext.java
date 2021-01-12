package me.lotabout.rpn.repl.context;

import java.util.List;
import java.util.Optional;
import me.lotabout.rpn.repl.struct.RealNumber;

public interface CalcContext {
  /** Get the inner stack, the last in item comes first */
  List<RealNumber> getStack();

  /** pop out the item on the top of the stack */
  Optional<RealNumber> pop();

  /** push the item on the top of the stack */
  void push(RealNumber element);
}
