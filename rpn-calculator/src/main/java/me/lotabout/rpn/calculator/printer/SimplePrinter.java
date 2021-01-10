package me.lotabout.rpn.calculator.printer;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;
import me.lotabout.rpn.repl.CalcContext;
import me.lotabout.rpn.repl.Operator;
import me.lotabout.rpn.repl.Printer;
import me.lotabout.rpn.repl.struct.ExecutionException;

public class SimplePrinter<T> implements Printer<T> {

  @Override
  public String printContext(CalcContext<T> calcContext) {
    StringBuilder sb = new StringBuilder();
    sb.append("stack:");

    // the stack order is latest first, we want earliest first
    List<T> stack =
        StreamSupport.stream(calcContext.getStack().spliterator(), false)
            .collect(Collectors.toList());
    Collections.reverse(stack);

    stack.forEach(
        item -> {
          sb.append(" ");
          sb.append(item);
        });

    return sb.toString();
  }

  @Override
  public String printError(Operator<T> op, ExecutionException ex) {
    return String.format(
        "operator %s (position: %s): %s",
        op.getName(), op.getPosition().getStart() + 1, ex.getMessage());
  }
}
