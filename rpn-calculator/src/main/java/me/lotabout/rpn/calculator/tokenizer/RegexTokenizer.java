package me.lotabout.rpn.calculator.tokenizer;

import java.util.*;
import java.util.function.Function;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;
import me.lotabout.rpn.repl.Operator;
import me.lotabout.rpn.repl.Tokenizer;
import me.lotabout.rpn.repl.struct.TokenPos;

public class RegexTokenizer<T> implements Tokenizer<T> {

  private static final Pattern reCapturingGroup =
      Pattern.compile("(\\\\*)(\\(\\?<[^>]+>|\\((?!\\?))");

  private final List<OperatorReader<T>> readers;
  private final Pattern readerPattern;

  public RegexTokenizer(List<OperatorReader<T>> readers) {
    this.readers = readers;

    String combinedPattern =
        readers
            .stream()
            .map(OperatorReader::getPattern)
            .map(Pattern::pattern)
            .map(RegexTokenizer::escapeCapturingGroup)
            .map(RegexTokenizer::groupPattern)
            .collect(Collectors.joining("|"));
    readerPattern = Pattern.compile(combinedPattern);
  }

  @Override
  public Stream<Operator<T>> tokenize(Stream<String> lines) {
    return lines.flatMap(this::extractOperator);
  }

  private Stream<Operator<T>> extractOperator(String line) {
    // 1 2 + => [1, 2, +]
    return StreamSupport.stream(
        Spliterators.spliteratorUnknownSize(
            new LineTokenizer<>(readerPattern.matcher(line), readers),
            Spliterator.ORDERED | Spliterator.NONNULL),
        false);
  }

  static class LineTokenizer<T> implements Iterator<Operator<T>> {
    private final Matcher matcher;
    private final List<OperatorReader<T>> readers;
    private boolean matchConsumed = true;

    public LineTokenizer(Matcher matcher, List<OperatorReader<T>> readers) {
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
    public Operator<T> next() {
      if (!hasNext()) {
        throw new NoSuchElementException();
      }

      // check out which group matches
      int group =
          IntStream.range(1, matcher.groupCount() + 1)
              .filter(i -> matcher.start(i) != -1)
              .findFirst()
              .orElseThrow(NoSuchElementException::new);

      matchConsumed = true;
      OperatorReader<T> reader = readers.get(group - 1);
      TokenPos position = new TokenPos(matcher.start(group), matcher.end(group));
      return reader.createOperator(matcher.group(group), position);
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
    return replaceAll(
            pattern,
            reCapturingGroup,
            m -> m.group(1).length() % 2 > 0 ? m.group(0) : m.group(1) + "(?:")
        .toString();
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
