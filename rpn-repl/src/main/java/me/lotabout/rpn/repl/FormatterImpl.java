package me.lotabout.rpn.repl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import me.lotabout.rpn.repl.context.REPLContext;
import me.lotabout.rpn.repl.struct.ExecutionException;
import me.lotabout.rpn.repl.struct.RealNumber;
import me.lotabout.rpn.repl.struct.Token;
import org.springframework.stereotype.Service;

@Service
public class FormatterImpl implements Formatter {

  @Override
  public String formatContext(REPLContext REPLContext) {
    StringBuilder sb = new StringBuilder();
    sb.append("stack:");

    // the stack order is latest first, we want earliest first
    List<RealNumber> stack = new ArrayList<>(REPLContext.getStack());
    Collections.reverse(stack);

    stack.forEach(
        item -> {
          sb.append(" ");
          sb.append(item);
        });

    return sb.toString();
  }

  @Override
  public String formatError(Token token, ExecutionException ex) {
    return String.format(
        "operator %s (position: %s): %s",
        token.getName(), token.getTokenStart() + 1, ex.getMessage());
  }
}
