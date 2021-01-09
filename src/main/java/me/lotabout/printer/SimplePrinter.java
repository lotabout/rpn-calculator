package me.lotabout.printer;

import me.lotabout.repl.Operator;
import me.lotabout.repl.Printer;
import me.lotabout.repl.struct.CalcContext;
import me.lotabout.repl.struct.ExecutionException;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

public class SimplePrinter<T> implements Printer<T> {

    @Override
    public String printContext(CalcContext<T> calcContext) {
        StringBuilder sb = new StringBuilder();
        sb.append("stack:");

        // the stack order is latest first, we want earliest first
        List<T> stack = StreamSupport.stream(calcContext.getStack().spliterator(), false)
                .collect(Collectors.toList());
        Collections.reverse(stack);

        stack.forEach(item -> {
            sb.append(" ");
            sb.append(item);
        });

        return sb.toString();
    }

    @Override
    public String printError(Operator<T> op, ExecutionException ex) {
        return String.format("operator %s (position: %s): %s", op.getName(), op.getPosition().getStart() + 1, ex.getMessage());
    }
}
