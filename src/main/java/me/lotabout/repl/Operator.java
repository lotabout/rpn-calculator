package me.lotabout.repl;

public interface Operator {

    default boolean needToSaveResult() {
        return true;
    }

}
