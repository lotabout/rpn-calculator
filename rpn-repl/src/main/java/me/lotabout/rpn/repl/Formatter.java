package me.lotabout.rpn.repl;

import me.lotabout.rpn.repl.context.CalcContext;
import me.lotabout.rpn.repl.struct.ExecutionException;
import me.lotabout.rpn.repl.token.Token;

public interface Formatter<T> {
  String formatContext(CalcContext<T> calcContext);

  String formatError(Token op, ExecutionException ex);
}
