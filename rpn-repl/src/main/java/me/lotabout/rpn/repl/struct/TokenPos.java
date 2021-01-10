package me.lotabout.rpn.repl.struct;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class TokenPos {
  private int start;
  private int end;
}
