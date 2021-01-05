package me.lotabout.repl;

import java.util.Optional;
import java.util.stream.Stream;

public class REPL {
    private final Tokenizer tokenizer;
    private final Evaluator evaluator;
    private final Printer printer;
    private final Context context;

    public REPL(Tokenizer tokenizer, Evaluator evaluator, Printer printer, Context context) {
        this.tokenizer = tokenizer;
        this.evaluator = evaluator;
        this.printer = printer;
        this.context = context;
    }

    public void run(Stream<String> lines) {
        if (lines.isParallel()) {
            throw new IllegalArgumentException("input streams of REPL should not be parallel");
        }

        tokenizer.tokenize(lines)
                .forEach(op -> {
                    evaluator.evaluate(context, op);
                    printer.print(context);
                });
    }
}
