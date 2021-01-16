package me.lotabout.rpn.repl.token;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import lombok.extern.slf4j.Slf4j;
import me.lotabout.rpn.repl.Tokenizer;
import me.lotabout.rpn.repl.struct.RealNumber;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class TokenizerImpl implements Tokenizer {
  private static final Pattern RE_SEPARATOR = Pattern.compile("\\s+");
  private static final Map<String, TokenType> tokenTypeMap = new HashMap<>();

  static {
    tokenTypeMap.put("clear", TokenType.CLEAR);
    tokenTypeMap.put("undo", TokenType.UNDO);
    tokenTypeMap.put("redo", TokenType.REDO);
  }

  @Override
  public List<Token> tokenize(String line) {
    List<Token> ret = new ArrayList<>();
    Matcher matcher = RE_SEPARATOR.matcher(line);

    int start = 0, end = 0;
    while (matcher.find()) {
      end = matcher.start();
      if (end > start) {
        Token token = parseToken(line, start, end);
        ret.add(token);
      }
      start = matcher.end();
    }

    end = line.length();
    if (end > start) {
      Token token = parseToken(line, start, end);
      ret.add(token);
    }

    return ret;
  }

  private Token parseToken(String line, int start, int end) {
    String content = line.substring(start, end);
    TokenType tokenType;
    if (isNumber(content)) {
      tokenType = TokenType.NUMBER;
    } else {
      tokenType = tokenTypeMap.getOrDefault(content, TokenType.MATH);
    }

    return Token.builder()
        .tokenType(tokenType)
        .content(content)
        .tokenStart(start)
        .tokenEnd(end)
        .build();
  }

  private boolean isNumber(String content) {
    try {
      RealNumber _ignored = RealNumber.valueOf(content);
      return true;
    } catch (NumberFormatException _ignored) {
      return false;
    }
  }
}
