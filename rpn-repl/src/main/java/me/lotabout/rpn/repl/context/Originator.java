package me.lotabout.rpn.repl.context;

public interface Originator<T> {
  T snapshot();

  void restoreFrom(T snapshot);
}
