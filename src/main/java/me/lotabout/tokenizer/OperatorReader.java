package me.lotabout.tokenizer;

import me.lotabout.repl.Operator;
import me.lotabout.repl.struct.TokenPos;

import java.util.regex.Pattern;

public interface OperatorReader<T> {
    /**
     * Regex pattern for parsing operator from input string
     */
    Pattern getPattern();

    Operator<T> createOperator(String content, TokenPos position);
}
