package me.lotabout.rpn.repl;

import me.lotabout.rpn.repl.context.CalcContext;
import me.lotabout.rpn.repl.struct.ExecutionException;

public interface Operator<T> {
  /** get the name of the operator */
  String getName();
  /**
   * Given calculation context, execute the operator
   *
   * @param context The stack context
   * @throws ExecutionException Any error happened during execution
   */
  void execute(CalcContext<T> context) throws ExecutionException;

  default boolean needToSaveResult() {
    return true;
  }
}
