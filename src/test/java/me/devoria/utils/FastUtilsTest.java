package me.devoria.utils;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Map;
import org.junit.jupiter.api.Test;

class FastUtilsTest {

    @Test
    void randomIntegersStayInsideTheRequestedRange() {
        for (int iteration = 0; iteration < 10_000; iteration++) {
            int value = FastUtils.randomIntInRange(5, 11);
            assertTrue(value >= 5 && value < 11, () -> "Out-of-range value: " + value);
        }
    }

    @Test
    void equalRangeBoundsReturnThatBound() {
        assertEquals(7, FastUtils.randomIntInRange(7, 7));
        assertEquals(2.5, FastUtils.randomDoubleInRange(2.5, 2.5));
        assertEquals(-1.25f, FastUtils.randomFloatInRange(-1.25f, -1.25f));
    }

    @Test
    void invalidRangesAreRejected() {
        assertThrows(IllegalArgumentException.class, () -> FastUtils.randomIntInRange(4, 3));
        assertThrows(IllegalArgumentException.class, () -> FastUtils.randomDoubleInRange(Double.NaN, 1));
        assertThrows(IllegalArgumentException.class, () -> FastUtils.randomFloatInRange(2, Float.POSITIVE_INFINITY));
    }

    @Test
    void metadataParsingPreservesValuesAfterTheFirstSeparator() {
        assertEquals(
                Map.of("health", "100", "note", "value:with:colons"),
                FastUtils.map(",health:100,note:value:with:colons"));
    }

    @Test
    void malformedMetadataIsRejectedExplicitly() {
        assertThrows(IllegalArgumentException.class, () -> FastUtils.map(null));
        assertThrows(IllegalArgumentException.class, () -> FastUtils.map(",missing-separator"));
        assertThrows(IllegalArgumentException.class, () -> FastUtils.map(",missing-value:"));
    }
}
