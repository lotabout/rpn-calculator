package me.lotabout.rpn.repl;

import me.lotabout.rpn.repl.context.REPLContext;
import me.lotabout.rpn.repl.struct.ExecutionException;

public interface Operator {
  /** get the name of the operator */
  String getName();
  /**
   * Given calculation context, execute the operator
   *
   * @param calcREPLContext The stack context
   * @throws ExecutionException Any error happened during execution
   */
  void execute(REPLContext calcREPLContext) throws ExecutionException;

  default boolean needToSaveResult() {
    return true;
  }
}
