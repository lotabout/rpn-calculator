package me.lotabout.repl;

import java.util.regex.Pattern;

public interface OperatorReader {
    /**
     * Regex pattern for parsing operator from input string
     */
    Pattern getPattern();

    Operator createOperator(String content, int startPos, int endPosExclusive);
}
