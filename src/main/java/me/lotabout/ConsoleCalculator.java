package me.lotabout;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.ServiceLoader;
import lombok.extern.slf4j.Slf4j;
import me.lotabout.calculator.RealNumber;
import me.lotabout.calculator.RealNumberOperatorReader;
import me.lotabout.printer.ConsoleOutput;
import me.lotabout.printer.SimplePrinter;
import me.lotabout.repl.OutputConsumer;
import me.lotabout.repl.Printer;
import me.lotabout.repl.REPL;
import me.lotabout.repl.Tokenizer;
import me.lotabout.tokenizer.OperatorReader;
import me.lotabout.tokenizer.RegexTokenizer;

@Slf4j
public class ConsoleCalculator {
  public static final String PROMPT =
      "Type in RRNs(Reverse Polish Notation) to start calculation, Ctrl-D to exit\n";

  public static void main(String[] args) {
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
