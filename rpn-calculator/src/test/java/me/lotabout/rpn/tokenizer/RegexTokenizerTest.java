package me.lotabout.rpn.tokenizer;

import static org.junit.Assert.assertEquals;

import java.util.Arrays;
import java.util.List;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import lombok.AllArgsConstructor;
import lombok.Data;
import me.lotabout.rpn.repl.CalcContext;
import me.lotabout.rpn.repl.Operator;
import me.lotabout.rpn.repl.Tokenizer;
import me.lotabout.rpn.repl.struct.ExecutionException;
import me.lotabout.rpn.repl.struct.TokenPos;
import org.junit.Test;

public class RegexTokenizerTest {

  @Test
  public void lineShouldBeTokenized() {
    List<OperatorReader<Integer>> readers =
        Arrays.asList(new SeparatorReader(), new NumberReader());
    Tokenizer<Integer> tokenizer = new RegexTokenizer<>(readers);

    String input = " 1\t2.2 \n-3.";
    List<MockOperator> ops =
        tokenizer
            .tokenize(Arrays.stream(input.split("\n")))
            .map(op -> (MockOperator) op)
            .collect(Collectors.toList());

    List<MockOperator> expected =
        Arrays.asList(
            new MockOperator(" ", new TokenPos(0, 1)),
            new MockOperator("1", new TokenPos(1, 2)),
            new MockOperator("\t", new TokenPos(2, 3)),
            new MockOperator("2.2", new TokenPos(3, 6)),
            new MockOperator(" ", new TokenPos(6, 7)),
            new MockOperator("-3.", new TokenPos(0, 3)));

    assertEquals(expected, ops);
  }

  @Data
  @AllArgsConstructor
  private static class MockOperator implements Operator<Integer> {
    private String content;
    private TokenPos pos;

    @Override
    public String getName() {
      return content;
    }

    @Override
    public TokenPos getPosition() {
      return pos;
    }

    @Override
    public void execute(CalcContext<Integer> calcCalcContext) throws ExecutionException {}
  }

  private abstract static class MockReader implements OperatorReader<Integer> {
    @Override
    public Operator<Integer> createOperator(String content, TokenPos pos) {
      return new MockOperator(content, pos);
    }
  }

  private static class SeparatorReader extends MockReader {
    @Override
    public Pattern getPattern() {
      return Pattern.compile("\\s+");
    }
  }

  private static class NumberReader extends MockReader {
    @Override
    public Pattern getPattern() {
      return Pattern.compile("[+-]?\\d+(\\.\\d*)?");
    }
  }
}
