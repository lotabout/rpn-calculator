package me.lotabout.rpn.calculator.printer;

import static org.junit.Assert.assertEquals;

import me.lotabout.rpn.repl.Operator;
import me.lotabout.rpn.repl.Printer;
import me.lotabout.rpn.repl.context.DefaultContext;
import me.lotabout.rpn.repl.struct.ExecutionException;
import me.lotabout.rpn.repl.struct.TokenPos;
import org.junit.Test;
import org.mockito.Mockito;

@SuppressWarnings("unchecked")
public class SimplePrinterTest {
  private final Printer<Integer> printer = new SimplePrinter<>();

  @Test
  public void printStack() {
    DefaultContext<Integer> context = new DefaultContext<>();
    context.push(1);
    context.push(2);
    context.push(3);
    assertEquals("stack: 1 2 3", printer.printContext(context));
  }

  @Test
  public void emptyStack() {
    DefaultContext<Integer> context = new DefaultContext<>();
    assertEquals("stack:", printer.printContext(context));
  }

  @Test
  public void printError() {
    Operator<Integer> opPlus = (Operator<Integer>) Mockito.mock(Operator.class);
    Mockito.when(opPlus.getName()).thenReturn("+");
    Mockito.when(opPlus.getPosition()).thenReturn(new TokenPos(1, 3));

    String error = printer.printError(opPlus, new ExecutionException("insufficient parameters"));
    String expected = "operator + (position: 2): insufficient parameters";
    assertEquals(expected, error);
  }
}
