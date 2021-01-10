package me.lotabout.rpn.repl.context;

public interface HistoryContext {
  /** Save the current stack status for later undo/redo */
  void checkpoint();

  /** Clear all saved checkpoint */
  void clear();

  /** set the stack to the latest saved history */
  void undo();

  /** undo the previous `undo` operation */
  void redo();
}
