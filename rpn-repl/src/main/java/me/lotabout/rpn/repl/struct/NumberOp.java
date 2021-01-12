package me.lotabout.rpn.repl.struct;

import lombok.AllArgsConstructor;
import me.lotabout.rpn.repl.Operator;
import me.lotabout.rpn.repl.context.REPLContext;

@AllArgsConstructor
public class NumberOp implements Operator {
  private final RealNumber number;

  @Override
  public String getName() {
    return null;
  }

  @Override
  public void execute(REPLContext calcREPLContext) throws ExecutionException {
    calcREPLContext.push(number);
  }
}
