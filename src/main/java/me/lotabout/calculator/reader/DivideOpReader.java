package me.lotabout.calculator.reader;

import java.util.regex.Pattern;
import me.lotabout.calculator.RealNumber;
import me.lotabout.calculator.RealNumberOperatorReader;
import me.lotabout.calculator.operator.stackops.DivideOp;
import me.lotabout.repl.Operator;
import me.lotabout.repl.struct.TokenPos;

public class DivideOpReader implements RealNumberOperatorReader {
  @Override
  public Pattern getPattern() {
    return Pattern.compile("/");
  }

  @Override
  public Operator<RealNumber> createOperator(String content, TokenPos position) {
    return new DivideOp(position);
  }
}
