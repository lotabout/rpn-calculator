package me.lotabout.rpn.calculator.reader;

import java.util.regex.Pattern;
import me.lotabout.rpn.calculator.RealNumber;
import me.lotabout.rpn.calculator.RealNumberOperatorReader;
import me.lotabout.rpn.calculator.operator.stackops.PlusOp;
import me.lotabout.rpn.repl.Operator;
import me.lotabout.rpn.repl.struct.TokenPos;

public class PlusOpReader implements RealNumberOperatorReader {
  @Override
  public Pattern getPattern() {
    return Pattern.compile("\\+");
  }

  @Override
  public Operator<RealNumber> createOperator(String content, TokenPos position) {
    return new PlusOp(position);
  }
}