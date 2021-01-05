package me.lotabout.repl;

import java.util.stream.Stream;

public interface Tokenizer {

    Stream<Operator> tokenize(Stream<String> lines);
}
