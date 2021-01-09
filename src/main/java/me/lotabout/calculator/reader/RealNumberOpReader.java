package me.lotabout.calculator.reader;

import java.util.regex.Pattern;
import me.lotabout.calculator.RealNumber;
import me.lotabout.calculator.operator.RealNumberOp;
import me.lotabout.repl.Operator;
import me.lotabout.repl.struct.TokenPos;
import me.lotabout.tokenizer.OperatorReader;

public class RealNumberOpReader implements OperatorReader<RealNumber> {
  @Override
  public Pattern getPattern() {
    return Pattern.compile("[+-]?\\d+(\\.\\d*)?");
  }

  @Override
  public Operator<RealNumber> createOperator(String content, TokenPos position) {
    return new RealNumberOp(content, position);
  }
}