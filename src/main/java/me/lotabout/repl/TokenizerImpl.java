package me.lotabout.repl;

import java.util.*;
import java.util.function.Function;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

public class TokenizerImpl implements Tokenizer {

    private static final Pattern reCapturingGroup = Pattern.compile("(\\\\*)(\\(\\?<[^>]+>|\\((?!\\?))");

    private final List<OperatorReader> readers;
    private final Pattern readerPattern;

    public TokenizerImpl(List<OperatorReader> readers) {
        this.readers = readers;

        String combinedPattern = readers.stream()
                .map(OperatorReader::getPattern)
                .map(Pattern::pattern)
                .map(TokenizerImpl::escapeCapturingGroup)
                .map(TokenizerImpl::groupPattern)
                .collect(Collectors.joining("|"));
        readerPattern = Pattern.compile(combinedPattern);
    }

    @Override
    public Stream<Operator> tokenize(Stream<String> lines) {
        return lines.flatMap(this::extractOperator);
    }

    private Stream<Operator> extractOperator(String line) {
        // 1 2 + => [1, 2, +]
        return StreamSupport.stream(Spliterators.spliteratorUnknownSize(
                new LineTokenizer(readerPattern.matcher(line), readers), Spliterator.ORDERED | Spliterator.NONNULL), false);
    }


    static class LineTokenizer implements Iterator<Operator> {
        private final Matcher matcher;
        private final List<OperatorReader> readers;
        private boolean matchConsumed = true;

        public LineTokenizer(Matcher matcher, List<OperatorReader> readers) {
            this.matcher = matcher;
            this.readers = readers;
        }

        @Override
        public boolean hasNext() {
            if (!matchConsumed) {
                return true;
            }

            if (!matcher.find()) {
                return false;
            }

            matchConsumed = false;
            return true;
        }

        @Override
        public Operator next() {
            if (!hasNext()) {
                throw new NoSuchElementException();
            }

            // check out which group matches
            int group = IntStream.range(1, matcher.groupCount() + 1)
                    .filter(i -> matcher.start(i) != -1)
                    .findFirst()
                    .orElseThrow(NoSuchElementException::new);

            matchConsumed = true;
            OperatorReader reader = readers.get(group - 1);
            return reader.createOperator(matcher.group(group), matcher.start(group), matcher.end(group));
        }
    }


    private static String groupPattern(String flattenPatten) {
        return String.format("(%s)", flattenPatten);
    }

    private static String escapeCapturingGroup(String pattern) {
        if (!pattern.contains("(") && !pattern.contains(")")) {
            return pattern;
        }

        // `(?...` => `(?...`
        // `(...` => `(?:...`
        // `(?<name>...` => `(?:...`
        // `\(...` = `\(...`
        return replaceAll(pattern, reCapturingGroup, m -> m.group(1).length() % 2 > 0 ? m.group(0) : m.group(1) + "(?:").toString();
    }

    private static StringBuffer replaceAll(
            String templateText, Pattern pattern, Function<Matcher, String> replacer) {
        Matcher matcher = pattern.matcher(templateText);
        StringBuffer result = new StringBuffer();
        while (matcher.find()) {
            matcher.appendReplacement(result, replacer.apply(matcher));
        }
        matcher.appendTail(result);
        return result;
    }
}
