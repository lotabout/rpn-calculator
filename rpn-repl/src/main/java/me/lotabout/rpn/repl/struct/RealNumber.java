package me.lotabout.rpn.repl.struct;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import lombok.Data;

/**
 * Wrapper of {@link java.math.BigDecimal} that overrides {@code toString} method for properly
 * representation
 */
@Data
public class RealNumber {
  private static final ThreadLocal<NumberFormat> FORMAT =
      ThreadLocal.withInitial(
          () -> {
            NumberFormat ret = new DecimalFormat("#.##########");
            ret.setRoundingMode(RoundingMode.FLOOR);
            return ret;
          });

  private final BigDecimal value;

  public RealNumber(BigDecimal value) {
    this.value = value;
  }

  public static RealNumber valueOf(String value) {
    return new RealNumber(new BigDecimal(value));
  }

  public static RealNumber valueOf(long value) {
    return new RealNumber(BigDecimal.valueOf(value));
  }

  public BigDecimal getValue() {
    return this.value;
  }

  @Override
  public String toString() {
    return FORMAT.get().format(this.value);
  }
}
