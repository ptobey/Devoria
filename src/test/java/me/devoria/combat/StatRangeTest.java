package me.devoria.combat;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

class StatRangeTest {

    @Test
    void rollsEndpointsAndRoundedMidpoint() {
        StatRange range = StatRange.parse("10-21");

        assertEquals(10, range.atPercentile(0));
        assertEquals(16, range.atPercentile(50));
        assertEquals(21, range.atPercentile(100));
    }

    @Test
    void parsesNegativeBounds() {
        StatRange range = StatRange.parse("-10--2");

        assertEquals(-6, range.atPercentile(50));
    }

    @Test
    void rejectsMalformedAndReversedRanges() {
        assertThrows(IllegalArgumentException.class, () -> StatRange.parse("10"));
        assertThrows(IllegalArgumentException.class, () -> StatRange.parse("8-4"));
        assertThrows(IllegalArgumentException.class, () -> new StatRange(1, 2).atPercentile(101));
    }
}
