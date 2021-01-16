package me.lotabout.rpn.repl;

import java.util.List;
import me.lotabout.rpn.repl.token.Token;

public interface Tokenizer {
  List<Token> tokenize(String line);
}
