package me.lotabout.rpn.console;

import static org.junit.Assert.assertEquals;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.nio.charset.StandardCharsets;

import me.lotabout.rpn.console.ConsoleCalculator;
import org.junit.Test;

public class ConsoleCalculatorTest {

  @Test
  public void example1() {
    String input = "5 2\n";
    String expected = ConsoleCalculator.PROMPT + "stack: 5 2\n";
    test(input, expected);
  }

  @Test
  public void example2() {
    String input = "2 sqrt\n" + "clear 9 sqrt\n";
    String expected = ConsoleCalculator.PROMPT + "stack: 1.4142135623\n" + "stack: 3\n";
    test(input, expected);
  }

  @Test
  public void example3() {
    String input = "5 2 -\n" + "3 -\n" + "clear\n";
    String expected = ConsoleCalculator.PROMPT + "stack: 3\n" + "stack: 0\n" + "stack:\n";
    test(input, expected);
  }

  @Test
  public void example4() {
    String input = "5 4 3 2\n" + "undo undo *\n" + "5 *\n" + "undo\n";
    String expected =
        ConsoleCalculator.PROMPT
            + "stack: 5 4 3 2\n"
            + "stack: 20\n"
            + "stack: 100\n"
            + "stack: 20 5\n";
    test(input, expected);
  }

  @Test
  public void example5() {
    String input = "7 12 2 /\n" + "*\n" + "4 /\n";
    String expected = ConsoleCalculator.PROMPT + "stack: 7 6\n" + "stack: 42\n" + "stack: 10.5\n";
    test(input, expected);
  }

  @Test
  public void example6() {
    String input = "1 2 3 4 5\n" + "*\n" + "clear 3 4 -\n";
    String expected =
        ConsoleCalculator.PROMPT + "stack: 1 2 3 4 5\n" + "stack: 1 2 3 20\n" + "stack: -1\n";
    test(input, expected);
  }

  @Test
  public void example7() {
    String input = "1 2 3 4 5\n" + "* * * *\n";
    String expected = ConsoleCalculator.PROMPT + "stack: 1 2 3 4 5\n" + "stack: 120\n";
    test(input, expected);
  }

  @Test
  public void example8() {
    String input = "1 2 3 * 5 + * * 6 5\n";
    String expected =
        ConsoleCalculator.PROMPT
            + "operator * (position: 15): insufficient parameters\n"
            + "stack: 11\n";
    test(input, expected);
  }

  private static void setSystemIn(String input) {
    System.setIn(new ByteArrayInputStream(input.getBytes(StandardCharsets.UTF_8)));
  }

  private static ByteArrayOutputStream getSystemOutput() {
    ByteArrayOutputStream baos = new ByteArrayOutputStream();
    PrintStream ps = new PrintStream(baos);
    System.setOut(ps);
    return baos;
  }

  private static void test(String input, String expected) {
    setSystemIn(input);
    ByteArrayOutputStream output = getSystemOutput();
    ConsoleCalculator.main(new String[] {});
    assertEquals(expected, output.toString());
  }
}
