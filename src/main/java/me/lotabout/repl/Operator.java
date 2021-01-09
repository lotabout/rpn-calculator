package me.lotabout.repl;

import me.lotabout.repl.struct.CalContext;
import me.lotabout.repl.struct.ExecutionException;
import me.lotabout.repl.struct.TokenPos;

public interface Operator<T> {

    /**
     * Retrieve the name of the operator, e.g. {@code *}
     */
    String getName();

    /**
     * The position of the operator on the line.
     */
    TokenPos getPosition();

    /**
     * Given calculation context, execute the operator
     *
     * @param calcCalContext The stack context
     * @throws ExecutionException Any error happened during execution
     */
    void execute(CalContext<T> calcCalContext) throws ExecutionException;

    default boolean needToSaveResult() {
        return true;
    }
}
