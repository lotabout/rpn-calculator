package me.lotabout.rpn.calculator.reader;

import java.util.regex.Pattern;
import me.lotabout.rpn.calculator.operator.RealNumber;
import me.lotabout.rpn.calculator.operator.RealNumberOp;
import me.lotabout.rpn.calculator.operator.RealNumberOperatorReader;
import me.lotabout.rpn.repl.Operator;
import me.lotabout.rpn.repl.struct.TokenPos;

public class RealNumberOpReader implements RealNumberOperatorReader {
  @Override
  public Pattern getPattern() {
    return Pattern.compile("[+-]?\\d+(\\.\\d*)?");
  }

  @Override
  public Operator<RealNumber> createOperator(String content, TokenPos position) {
    return new RealNumberOp(content, position);
  }
}
