package me.lotabout.rpn.repl;

import me.lotabout.rpn.repl.context.REPLContext;
import me.lotabout.rpn.repl.struct.ExecutionException;
import me.lotabout.rpn.repl.struct.Token;

public interface Formatter {
  String formatContext(REPLContext REPLContext);

  String formatError(Token op, ExecutionException ex);
}
