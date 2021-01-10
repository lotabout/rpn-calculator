package me.lotabout.rpn.calculator.operator.impl;

import me.lotabout.rpn.calculator.operator.RealNumberOperator;
import me.lotabout.rpn.repl.struct.TokenPos;

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
