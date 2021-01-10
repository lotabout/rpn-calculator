package me.lotabout.rpn.repl;

import me.lotabout.rpn.repl.struct.ExecutionException;

public interface Printer<T> {
  String printContext(CalcContext<T> calcContext);

  String printError(Operator<T> op, ExecutionException ex);
}
