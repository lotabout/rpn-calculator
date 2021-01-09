package me.lotabout.repl;

import me.lotabout.repl.struct.ExecutionException;

public interface Printer<T> {
  String printContext(CalcContext<T> calcContext);

  String printError(Operator<T> op, ExecutionException ex);
}
