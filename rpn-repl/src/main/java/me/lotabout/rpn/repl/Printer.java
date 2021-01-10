package me.lotabout.rpn.repl;

import me.lotabout.rpn.repl.context.REPLContext;
import me.lotabout.rpn.repl.struct.ExecutionException;

public interface Printer<T> {
  String printContext(REPLContext<T> REPLContext);

  String printError(Operator<T> op, ExecutionException ex);
}
