package me.lotabout.rpn.calculator.tokenizer;

import java.util.regex.Pattern;
import me.lotabout.rpn.repl.Operator;
import me.lotabout.rpn.repl.struct.TokenPos;

public interface OperatorReader<T> {
  /** Regex pattern for parsing operator from input string */
  Pattern getPattern();

  Operator<T> createOperator(String content, TokenPos position);
}
