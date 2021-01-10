package me.lotabout.rpn.calculator.operator.impl;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.StreamSupport;
import me.lotabout.rpn.calculator.operator.RealNumber;
import me.lotabout.rpn.calculator.operator.RealNumberOperator;
import me.lotabout.rpn.calculator.operator.exception.InsufficientParameters;
import me.lotabout.rpn.repl.context.REPLContext;
import me.lotabout.rpn.repl.struct.ExecutionException;
import me.lotabout.rpn.repl.struct.TokenPos;

public abstract class ArithmeticOp extends PositionedOp implements RealNumberOperator {
  protected ArithmeticOp(TokenPos position) {
    super(position);
  }

  protected abstract int getNumberOfOperands();

  /**
   * Execute the arithmetic operation, the number of operands are guaranteed to be satisfied
   *
   * <p>The order of operands: [0] is the top of the stack
   */
  protected abstract RealNumber executeInner(List<RealNumber> operands) throws ExecutionException;

  @Override
  public void execute(REPLContext<RealNumber> replContext) throws ExecutionException {
    int numOperands = this.getNumberOfOperands();
    List<RealNumber> operands =
        StreamSupport.stream(replContext.getStack().spliterator(), false)
            .limit(numOperands)
            .collect(Collectors.toList());
    if (operands.size() != numOperands) {
      throw new InsufficientParameters();
    }

    RealNumber result;
    try {
      result = executeInner(operands);
    } catch (ArithmeticException ex) {
      throw new ExecutionException(ex.getMessage(), ex);
    }

    IntStream.range(0, numOperands).forEach(_i -> replContext.pop());
    replContext.push(result);
  }
}
