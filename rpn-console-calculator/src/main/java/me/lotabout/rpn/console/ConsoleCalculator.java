package me.lotabout.rpn.console;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.List;
import java.util.ServiceLoader;
import lombok.extern.slf4j.Slf4j;
import me.lotabout.rpn.calculator.operator.RealNumber;
import me.lotabout.rpn.calculator.operator.RealNumberOperatorReader;
import me.lotabout.rpn.calculator.printer.ConsoleOutput;
import me.lotabout.rpn.calculator.printer.SimplePrinter;
import me.lotabout.rpn.calculator.tokenizer.OperatorReader;
import me.lotabout.rpn.calculator.tokenizer.RegexTokenizer;
import me.lotabout.rpn.repl.OutputConsumer;
import me.lotabout.rpn.repl.Printer;
import me.lotabout.rpn.repl.REPL;
import me.lotabout.rpn.repl.Tokenizer;

@Slf4j
public class ConsoleCalculator {
  public static final String PROMPT =
      "Type in RRNs(Reverse Polish Notation) to start calculation, Ctrl-D to exit\n";

  public static void main(String[] args) {
    if (args.length > 0) {
      OperatorLoader loader = new OperatorLoader();
      for (String fileOrDirectory : args) {
        try {
          loader.addJarOrDir(fileOrDirectory);
        } catch (MalformedURLException ex) {
          log.info("Failed to add jar file to loader: {}", fileOrDirectory, ex);
        }
      }
      Thread.currentThread().setContextClassLoader(loader);
    }

    List<OperatorReader<RealNumber>> readers = loadReaderImplementations();
    Tokenizer<RealNumber> tokenizer = new RegexTokenizer<>(readers);
    Printer<RealNumber> printer = new SimplePrinter<>();
    OutputConsumer consumer = new ConsoleOutput();
    REPL<RealNumber> repl = new REPL<>(tokenizer, printer, consumer);

    System.out.print(PROMPT);
    BufferedReader stdin = new BufferedReader(new InputStreamReader(System.in));
    repl.executeLines(stdin.lines());
  }

  private static List<OperatorReader<RealNumber>> loadReaderImplementations() {
    List<OperatorReader<RealNumber>> readers = new ArrayList<>();
    for (RealNumberOperatorReader reader : ServiceLoader.load(RealNumberOperatorReader.class)) {
      log.info("Loading Reader: " + reader.getClass().getName());
      readers.add(reader);
    }

    return readers;
  }
}
