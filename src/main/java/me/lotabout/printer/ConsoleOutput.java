package me.lotabout.printer;

import me.lotabout.repl.OutputConsumer;

public class ConsoleOutput implements OutputConsumer {
  @Override
  public void consume(String content) {
    System.out.println(content);
  }
}
