package me.devoria.combat;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.Map;
import org.junit.jupiter.api.Test;

class DamageCalculatorTest {

    @Test
    void rollsTypedDamageAndCalculatesTotal() {
        DamageRoll result = DamageCalculator.roll(
                Map.of(
                        "damage", "10-20",
                        "earthDamage", "4-8",
                        "windDamage", "2-2"),
                () -> 50);

        assertEquals(15, result.amount(DamageType.PHYSICAL));
        assertEquals(6, result.amount(DamageType.EARTH));
        assertEquals(2, result.amount(DamageType.WIND));
        assertEquals(0, result.amount(DamageType.FIRE));
        assertEquals(23, result.total());
    }

    @Test
    void supportsTheCanonicalElectricKey() {
        DamageRoll result = DamageCalculator.roll(Map.of("thunderDamage", "3-9"), () -> 100);

        assertEquals(9, result.amount(DamageType.ELECTRIC));
    }

    @Test
    void invalidInputFailsBeforeRuntimeEffects() {
        assertThrows(
                IllegalArgumentException.class,
                () -> DamageCalculator.roll(Map.of("damage", "invalid"), () -> 50));
        assertThrows(
                IllegalArgumentException.class,
                () -> DamageCalculator.roll(Map.of("damage", "1-2"), () -> -1));
        assertThrows(
                IllegalArgumentException.class,
                () -> new DamageRoll(Map.of(DamageType.FIRE, -1)));
    }
}
