package me.lotabout.repl;

import me.lotabout.repl.struct.CalContext;
import me.lotabout.repl.struct.ExecutionException;

public interface Printer<T> {
    void print(CalContext<T> calContext);

    void printError(Operator<T> op, ExecutionException ex);
}
