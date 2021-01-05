package me.lotabout.repl;

public interface OperatorReader {
    /**
     * Regex pattern for parsing operator from input string
     */
    String getPattern();

    Operator createOperator(String content, int startPos, int endPosExclusive);
}
