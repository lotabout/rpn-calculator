package me.lotabout.rpn.calculator.operator.exception;

import me.lotabout.rpn.repl.struct.ExecutionException;

public class InsufficientParameters extends ExecutionException {
  public InsufficientParameters() {
    super("insufficient parameters");
  }
}
