package me.lotabout.rpn.repl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import lombok.extern.slf4j.Slf4j;
import me.lotabout.rpn.repl.struct.NumberOp;
import me.lotabout.rpn.repl.struct.RealNumber;
import me.lotabout.rpn.repl.struct.Token;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

@Slf4j
@Service
public class TokenizerImpl implements Tokenizer {
  private static final Pattern RE_SEPARATOR = Pattern.compile("\\s+");
  private final Map<String, Operator> operatorMap = new HashMap<>();

  @Autowired
  public TokenizerImpl(List<Operator> operators) {
    for (Operator op : operators) {
      if (StringUtils.isEmpty(op.getName())) {
        log.warn("operator[{}]'s name is empty, skip", op.getClass());
      }
      operatorMap.put(op.getName(), op);
    }
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
    String name = line.substring(start, end);
    Operator op = operatorMap.get(name);
    if (op == null) {
      op = tryParse(name);
    }
    return Token.builder().name(name).operator(op).tokenStart(start).tokenEnd(end).build();
  }

  private NumberOp tryParse(String content) {
    try {
      double number = Double.parseDouble(content);
      return new NumberOp(new RealNumber(number));
    } catch (NumberFormatException _ignored) {
      return null;
    }
  }
}
