package me.lotabout.calculator;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class RealNumberTest {

    @Test
    public void realNumberPrecision() {
        RealNumber sqrt2 = new RealNumber(Math.sqrt(2));
        assertEquals("1.4142135623", sqrt2.toString());

        RealNumber a = new RealNumber(0.1234567890123456D);
        RealNumber b = new RealNumber(0.0000000000000001D);
        assertEquals("0.123456789", a.toString());
        assertEquals("0", b.toString());

        RealNumber c = new RealNumber(a.getValue() + b.getValue());
        assertEquals("0.123456789", a.toString());
    }
}