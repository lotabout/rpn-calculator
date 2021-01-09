package me.lotabout.calculator.reader;

import java.util.regex.Pattern;
import me.lotabout.calculator.RealNumber;
import me.lotabout.calculator.RealNumberOperatorReader;
import me.lotabout.calculator.operator.stackops.SqrtOp;
import me.lotabout.repl.Operator;
import me.lotabout.repl.struct.TokenPos;

public class SqrtOpReader implements RealNumberOperatorReader {

  @Override
  public Pattern getPattern() {
    return Pattern.compile("sqrt");
  }

  @Override
  public Operator<RealNumber> createOperator(String content, TokenPos position) {
    return new SqrtOp(position);
  }
}
