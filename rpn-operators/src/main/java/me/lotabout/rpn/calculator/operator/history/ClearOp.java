package me.lotabout.rpn.calculator.operator.history;

import me.lotabout.rpn.calculator.operator.HistoryOp;
import me.lotabout.rpn.repl.context.HistoryContext;
import me.lotabout.rpn.repl.struct.ExecutionException;
import org.springframework.stereotype.Component;

@Component
public class ClearOp extends HistoryOp {

  @Override
  public String getName() {
    return "clear";
  }

  @Override
  public void executeInner(HistoryContext context) throws ExecutionException {
    context.clear();
  }
}
