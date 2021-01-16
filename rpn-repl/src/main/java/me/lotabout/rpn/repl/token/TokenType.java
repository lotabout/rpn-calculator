package me.lotabout.rpn.repl.token;

public enum TokenType {
  NUMBER,
  UNDO,
  REDO,
  CLEAR,
  MATH,
  ;

  public boolean isHistoryToken() {
    return this == UNDO || this == REDO || this == CLEAR;
  }
}
