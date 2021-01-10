package me.lotabout.rpn.calculator.reader;

import java.util.regex.Pattern;
import me.lotabout.rpn.calculator.operator.RealNumber;
import me.lotabout.rpn.calculator.operator.RealNumberOperatorReader;
import me.lotabout.rpn.calculator.operator.math.DivideOp;
import me.lotabout.rpn.repl.Operator;
import me.lotabout.rpn.repl.struct.TokenPos;

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
