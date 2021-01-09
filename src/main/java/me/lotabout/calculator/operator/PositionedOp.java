package me.lotabout.calculator.operator;

import me.lotabout.repl.Operator;
import me.lotabout.repl.struct.TokenPos;

public abstract class PositionedOp<T> implements Operator<T> {
    private final TokenPos position;

    protected PositionedOp(TokenPos position) {
        this.position = position;
    }

    @Override
    public TokenPos getPosition() {
        return position;
    }
}
