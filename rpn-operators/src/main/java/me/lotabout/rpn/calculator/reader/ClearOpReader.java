package me.lotabout.rpn.calculator.reader;

import java.util.regex.Pattern;
import me.lotabout.rpn.calculator.RealNumber;
import me.lotabout.rpn.calculator.RealNumberOperatorReader;
import me.lotabout.rpn.calculator.operator.ClearOp;
import me.lotabout.rpn.repl.Operator;
import me.lotabout.rpn.repl.struct.TokenPos;

public class ClearOpReader implements RealNumberOperatorReader {
  @Override
  public Pattern getPattern() {
    return Pattern.compile("clear");
  }

  @Override
  public Operator<RealNumber> createOperator(String content, TokenPos position) {
    return new ClearOp(position);
  }
}
