package me.lotabout.rpn.repl;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import lombok.AllArgsConstructor;
import lombok.Data;
import me.lotabout.rpn.repl.context.REPLContext;
import me.lotabout.rpn.repl.struct.ExecutionException;
import me.lotabout.rpn.repl.struct.TokenPos;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

@SuppressWarnings("unchecked")
public class REPLTest {
  private Operator<Integer> one;
  private Operator<Integer> two;
  private Operator<Integer> opPlus;
  private Tokenizer<Integer> tokenizer;
  private Printer<Integer> printer;
  private OutputConsumer outputConsumer;

  @Before
  public void setup() {
    this.one = new NumOp(1, new TokenPos(0, 1));
    this.two = new NumOp(2, new TokenPos(2, 3));
    this.opPlus = new PlusOp(new TokenPos(4, 5));

    this.tokenizer = (Tokenizer<Integer>) Mockito.mock(Tokenizer.class);

    this.printer = (Printer<Integer>) Mockito.mock(Printer.class);
    Mockito.when(printer.printContext(Mockito.any())).thenReturn("stack:");
    Mockito.when(printer.printError(Mockito.any(), Mockito.any())).thenReturn("error");

    this.outputConsumer = Mockito.spy(OutputConsumer.class);
    Mockito.doNothing().when(outputConsumer).consume(Mockito.any());
  }

  @Test
  public void operatorShouldBeExecuted() {
    REPL<Integer> repl = new REPL<>(tokenizer, printer, outputConsumer);
    assertTrue(repl.execute(one));
    assertTrue(repl.execute(two));
    assertTrue(repl.execute(opPlus));
  }

  @Test
  public void errorShouldBeExecuted() {
    REPL<Integer> repl = new REPL<>(tokenizer, printer, outputConsumer);
    assertTrue(repl.execute(one));
    assertFalse(repl.execute(opPlus));
  }

  @Data
  @AllArgsConstructor
  private static class NumOp implements Operator<Integer> {
    private int num;
    private TokenPos pos;

    @Override
    public String getName() {
      return String.valueOf(num);
    }

    @Override
    public TokenPos getPosition() {
      return pos;
    }

    @Override
    public void execute(REPLContext<Integer> calcREPLContext) throws ExecutionException {
      calcREPLContext.push(num);
    }
  }

  @Data
  @AllArgsConstructor
  private static class PlusOp implements Operator<Integer> {
    private TokenPos pos;

    @Override
    public String getName() {
      return "+";
    }

    @Override
    public TokenPos getPosition() {
      return pos;
    }

    @Override
    public void execute(REPLContext<Integer> calcREPLContext) throws ExecutionException {
      Integer left =
          calcREPLContext
              .pop()
              .orElseThrow(() -> new ExecutionException("insufficient parameters"));
      Integer right =
          calcREPLContext
              .pop()
              .orElseThrow(() -> new ExecutionException("insufficient parameters"));

      calcREPLContext.push(left + right);
    }
  }
}
