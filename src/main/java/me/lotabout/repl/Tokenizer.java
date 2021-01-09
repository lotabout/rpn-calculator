package me.lotabout.repl;

import java.util.stream.Stream;

public interface Tokenizer<T> {

    Stream<Operator<T>> tokenize(Stream<String> lines);
}
