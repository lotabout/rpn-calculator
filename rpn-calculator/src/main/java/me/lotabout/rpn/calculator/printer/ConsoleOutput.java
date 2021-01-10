package me.lotabout.rpn.calculator.printer;

import me.lotabout.rpn.repl.OutputConsumer;

public class ConsoleOutput implements OutputConsumer {
  @Override
  public void consume(String content) {
    System.out.println(content);
  }
}
