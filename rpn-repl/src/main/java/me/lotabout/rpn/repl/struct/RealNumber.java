package me.lotabout.rpn.repl.struct;

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

  private final double value;

  public RealNumber(double value) {
    this.value = value;
  }

  public double getValue() {
    return this.value;
  }

  @Override
  public String toString() {
    return FORMAT.get().format(this.value);
  }
}
