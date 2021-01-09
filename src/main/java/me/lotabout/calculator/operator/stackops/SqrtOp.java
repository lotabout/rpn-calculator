package me.lotabout.calculator.operator.stackops;

import me.lotabout.calculator.RealNumber;
import me.lotabout.calculator.operator.ArithmeticOp;
import me.lotabout.repl.struct.ExecutionException;
import me.lotabout.repl.struct.TokenPos;

import java.util.List;

public class SqrtOp extends ArithmeticOp {
    public SqrtOp(TokenPos position) {
        super(position);
    }

    @Override
    protected int getNumberOfOperands() {
        return 1;
    }

    @Override
    protected RealNumber executeInner(List<RealNumber> operands) throws ExecutionException {
        return new RealNumber(Math.sqrt(operands.get(0).getValue()));
    }

    @Override
    public String getName() {
        return "sqrt";
    }
}
