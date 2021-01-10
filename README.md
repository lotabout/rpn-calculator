![Build](https://github.com/lotabout/rpn-calculator/workflows/Java%20CI%20with%20Maven/badge.svg)

# RPN(Reverse Polish Notation) Calculator

A Command-Line based RPN calculator. Main goal is to be maintainable,
extensible and testable and fun.

## Quick Start

```console
$ mvn clean package
$ java -jar target/rpn-calculator-1.0-SNAPSHOT-jar-with-dependencies.jar
[main] INFO me.lotabout.rpn.console.ConsoleCalculator - Loading Reader: ClearOpReader
[main] INFO me.lotabout.rpn.console.ConsoleCalculator - Loading Reader: DivideOpReader
[main] INFO me.lotabout.rpn.console.ConsoleCalculator - Loading Reader: MinusOpReader
[main] INFO me.lotabout.rpn.console.ConsoleCalculator - Loading Reader: MultiplyOpReader
[main] INFO me.lotabout.rpn.console.ConsoleCalculator - Loading Reader: PlusOpReader
[main] INFO me.lotabout.rpn.console.ConsoleCalculator - Loading Reader: RealNumberOpReader
[main] INFO me.lotabout.rpn.console.ConsoleCalculator - Loading Reader: SqrtOpReader
[main] INFO me.lotabout.rpn.console.ConsoleCalculator - Loading Reader: UndoOpReader
Type in RRNs(reverse polish notation) to start calculation, Ctrl-D to exit
4 5
stack: 4 5
2 -
stack: 4 3
sqrt
stack: 4 1.7320508075
clear
stack:
```

## Features

* History Manipulation, could `undo` operations and `clear` stack:

    ```console
    1 2 +
    stack: 3
    4 -
    stack: -1
    undo
    stack: 3 4
    5 -
    stack: 3 -1
    ```

* Easy for extending operators
    * New operators could simply provide a regex for parsing.
    * With the help of SPI, no need to modify existing code for adding operators

* Pluggable Components for making your own calculator. The calculator is
  divided into 3 major components, all of them are customizable:
    * `Tokenizer` for lexing: parsing input lines into operators/tokens.
    * `Printer` for representing the calculation context, e.g. the stack of numbers.
    * `OutputConsumer` for actually output the representation. The output goes to console by default.
