package me.lotabout.calculator.operator;

import me.lotabout.calculator.RealNumberOperator;
import me.lotabout.repl.struct.TokenPos;

public abstract class PositionedOp implements RealNumberOperator {
  private final TokenPos position;

  protected PositionedOp(TokenPos position) {
    this.position = position;
  }

  @Override
  public TokenPos getPosition() {
    return position;
  }
}
