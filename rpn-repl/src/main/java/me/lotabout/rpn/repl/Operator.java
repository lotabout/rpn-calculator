package me.lotabout.rpn.repl;

import me.lotabout.rpn.repl.context.REPLContext;
import me.lotabout.rpn.repl.struct.ExecutionException;
import me.lotabout.rpn.repl.struct.TokenPos;

public interface Operator<T> {

  /** Retrieve the name of the operator, e.g. {@code *} */
  String getName();

  /** The position of the operator on the line. */
  TokenPos getPosition();

  /**
   * Given calculation context, execute the operator
   *
   * @param calcREPLContext The stack context
   * @throws ExecutionException Any error happened during execution
   */
  void execute(REPLContext<T> calcREPLContext) throws ExecutionException;

  default boolean needToSaveResult() {
    return true;
  }
}
