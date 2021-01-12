package me.lotabout.rpn.console;

import me.lotabout.rpn.repl.Printer;
import org.springframework.stereotype.Component;

@Component
public class ConsolePrinter implements Printer {
  @Override
  public void print(String content) {
    System.out.println(content);
  }
}
