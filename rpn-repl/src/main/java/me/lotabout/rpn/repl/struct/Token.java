package me.lotabout.rpn.repl.struct;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NonNull;
import me.lotabout.rpn.repl.Operator;
import org.springframework.lang.Nullable;

@Data
@Builder
@AllArgsConstructor
public class Token {
  @NonNull private String name;
  @Nullable private Operator operator;
  private int tokenStart;
  private int tokenEnd;
}
