package me.lotabout.rpn.repl.context;

public interface REPLContext<Value, Snapshot> extends CalcContext<Value>, Originator<Snapshot> {}
