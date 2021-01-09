package me.lotabout.calculator.reader;

import java.util.regex.Pattern;
import me.lotabout.calculator.RealNumber;
import me.lotabout.calculator.operator.ClearOp;
import me.lotabout.repl.Operator;
import me.lotabout.repl.struct.TokenPos;
import me.lotabout.tokenizer.OperatorReader;

public class ClearOpReader implements OperatorReader<RealNumber> {
  @Override
  public Pattern getPattern() {
    return Pattern.compile("clear");
  }

  @Override
  public Operator<RealNumber> createOperator(String content, TokenPos position) {
    return new ClearOp(position);
  }
}
