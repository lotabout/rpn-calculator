package me.lotabout.calculator.operator.exception;

import me.lotabout.repl.struct.ExecutionException;

public class InsufficientParameters extends ExecutionException {
  public InsufficientParameters() {
    super("insufficient parameters");
  }
}
