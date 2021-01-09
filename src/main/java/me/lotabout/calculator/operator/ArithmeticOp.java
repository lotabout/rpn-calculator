package me.lotabout.calculator.operator;

import me.lotabout.calculator.RealNumber;
import me.lotabout.calculator.operator.exception.InsufficientParameters;
import me.lotabout.repl.Operator;
import me.lotabout.repl.struct.CalcContext;
import me.lotabout.repl.struct.ExecutionException;
import me.lotabout.repl.struct.TokenPos;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.StreamSupport;

public abstract class ArithmeticOp extends PositionedOp<RealNumber> implements Operator<RealNumber> {
    protected ArithmeticOp(TokenPos position) {
        super(position);
    }

    protected abstract int getNumberOfOperands();

    /**
     * Execute the arithmetic operation, the number of operands are guaranteed to be satisfied
     * <p>
     * The order of operands: [0] is the top of the stack
     */
    protected abstract RealNumber executeInner(List<RealNumber> operands) throws ExecutionException;

    @Override
    public void execute(CalcContext<RealNumber> calcCalcContext) throws ExecutionException {
        int numOperands = this.getNumberOfOperands();
        List<RealNumber> operands = StreamSupport.stream(calcCalcContext.getStack().spliterator(), false)
                .limit(numOperands)
                .collect(Collectors.toList());
        if (operands.size() != numOperands) {
            throw new InsufficientParameters();
        }

        RealNumber result;
        try {
            result = executeInner(operands);
        } catch (ArithmeticException ex) {
            throw new ExecutionException(ex.getMessage(), ex);
        }

        IntStream.range(0, numOperands).forEach(_i -> calcCalcContext.pop());
        calcCalcContext.push(result);
    }
}
