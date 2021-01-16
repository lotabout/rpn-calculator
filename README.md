![Build](https://github.com/lotabout/rpn-calculator/workflows/Java%20CI%20with%20Maven/badge.svg)

# RPN(Reverse Polish Notation) Calculator

A Command-Line based RPN calculator. Main goal is to be maintainable,
extensible and testable and fun.

## Quick Start

```console
$ mvn clean package
$ java -jar rpn-console-calculator/target/rpn-console-calculator-1.0-SNAPSHOT-jar-with-dependencies.jar
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
    * extend `ArithmeticOp` class and mark it `@Component` and ready to go
* Properly abstracted. Easy tweaking the components for your own need. For
  example default implementation would show the contents on console, could
  easily implement `Printer` to send the results to HTTP responses.

## Show me the code

- `rpn-console-calculator`: Main entry that construct and run a REPL.
  * implement console based read/write utility
  * create instances of components and wire them up.
- `rpn-repl`: contains the abstractions of a REPL.
  * `Tokenizer` will transform input string into tokens;
  * `REPL` itself would lookup the corresponding operators and execute them
  * operators are passed a `CalcContext` they can operate on
  * implement `RealNumber`, the main operand of operators
  * `Formatter` is used for formatting the context and error messages
  * `Printer` would be needed to print the formatted messages, be it console
      or HTTP responses.
  * `RealNumber` is built upon `BigDecimal` for keeping enough precisions(>=15).
- `rpn-operators`: pluggable package that implement operators.

## ChangeLog

### 2021-01-16

- Segregate history management from math ops.
    * Take "memento pattern"'s idea and `Context` handles only save/restore
        snapshot, but not the `undo/redo` function.
    * `REPL` now deal with `undo/redo` cause the history management operators
        are finite and special(different from math ops).
- `Tokenizer` now returns tokens, not ops.

### 2021-01-14

- `BigDecimal` support. Cause `double` could not represents financial numbers
    correctly though its precision is around 15 digits.
- Use [big-math](https://github.com/eobermuhlner/big-math) library for `sqrt`
    implementation. `Math.sqrt` don't have enough precision.

### 2021-01-12

- Introduce Spring and replace SPI with Spring's `@autowire`.
- Realize that `Operator` is stateless. That means a single bean is enough, remove `OperatorReader`s.
- Introduce wrapper class `Token` that wraps positions and `Operator` to be executed.
- Make `Context` specific, because it's too complex and unnecessary to introduce generic.
- Replace `RegexTokenizer` with a simple space-separated one.
