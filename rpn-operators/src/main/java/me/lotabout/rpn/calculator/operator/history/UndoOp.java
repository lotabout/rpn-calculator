package me.lotabout.rpn.calculator.operator.history;

import me.lotabout.rpn.calculator.operator.HistoryOp;
import me.lotabout.rpn.repl.context.HistoryContext;
import org.springframework.stereotype.Component;

@Component
public class UndoOp extends HistoryOp {

  @Override
  protected void executeInner(HistoryContext context) {
    context.undo();
  }

  @Override
  public String getName() {
    return "undo";
  }
}
