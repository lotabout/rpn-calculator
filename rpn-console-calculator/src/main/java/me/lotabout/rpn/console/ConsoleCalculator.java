package me.lotabout.rpn.console;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import me.lotabout.rpn.repl.*;
import me.lotabout.rpn.repl.context.DefaultContext;
import me.lotabout.rpn.repl.struct.RealNumber;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@Slf4j
@SpringBootApplication(scanBasePackages = {"me.lotabout.rpn"})
public class ConsoleCalculator implements CommandLineRunner {
  public static final String PROMPT =
      "Type in RRNs(Reverse Polish Notation) to start calculation, Ctrl-D to exit\n";

  private final REPL repl;

  public ConsoleCalculator(
      Tokenizer tokenizer,
      Formatter<RealNumber> formatter,
      Printer printer,
      List<Operator<RealNumber>> mathOps) {
    this.repl = new REPL(new DefaultContext(), tokenizer, formatter, printer, 64, mathOps);
  }

  public static void main(String[] args) {
    SpringApplication.run(ConsoleCalculator.class, args);
  }

  @Override
  public void run(String... args) throws Exception {
    System.out.print(PROMPT);
    BufferedReader stdin = new BufferedReader(new InputStreamReader(System.in));
    stdin.lines().forEach(repl::executeLine);
  }
}
