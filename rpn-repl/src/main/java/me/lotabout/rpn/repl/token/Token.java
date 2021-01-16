package me.lotabout.rpn.repl.token;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class Token {
  private TokenType tokenType;
  private String content;
  private int tokenStart;
  private int tokenEnd;
}
