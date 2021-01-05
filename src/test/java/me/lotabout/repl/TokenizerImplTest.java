package me.lotabout.repl;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import static org.junit.Assert.assertEquals;

public class TokenizerImplTest {

    @Test
    public void lineShouldBeTokenized() {
        List<OperatorReader> readers = Arrays.asList(new SeparatorReader(), new NumberReader());
        Tokenizer tokenizer = new TokenizerImpl(readers);

        String input = " 1\t2.2 \n-3.";
        List<MockOperator> ops = tokenizer.tokenize(Arrays.stream(input.split("\n")))
                .map(op -> (MockOperator) op)
                .collect(Collectors.toList());

        List<MockOperator> expected = Arrays.asList(
                new MockOperator(" ", 0, 1),
                new MockOperator("1", 1, 2),
                new MockOperator("\t", 2, 3),
                new MockOperator("2.2", 3, 6),
                new MockOperator(" ", 6, 7),
                new MockOperator("-3.", 0, 3)
        );

        assertEquals(expected, ops);
    }

    @Data
    @AllArgsConstructor
    private static class MockOperator implements Operator {
        private String content;
        private int start;
        private int end;
    }

    private static abstract class MockReader implements OperatorReader {
        @Override
        public Operator createOperator(String content, int startPos, int end) {
            return new MockOperator(content, startPos, end);
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