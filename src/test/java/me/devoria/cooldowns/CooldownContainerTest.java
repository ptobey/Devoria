package me.devoria.cooldowns;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Map;
import java.util.concurrent.atomic.AtomicLong;
import org.junit.jupiter.api.Test;

class CooldownContainerTest {

    @Test
    void remainingTimeUsesTheInjectedClock() {
        AtomicLong clock = new AtomicLong(1_000);
        CooldownContainer cooldowns = new CooldownContainer(clock::get);

        cooldowns.setCooldownFromNow("heavy", 250);
        assertEquals(250, cooldowns.getCooldownLeft("heavy"));
        assertFalse(cooldowns.isCooldownDone("heavy"));

        clock.set(1_250);
        assertEquals(0, cooldowns.getCooldownLeft("heavy"));
        assertTrue(cooldowns.isCooldownDone("heavy"));
    }

    @Test
    void missingCooldownsAreDone() {
        CooldownContainer cooldowns = new CooldownContainer(() -> 1_000);

        assertTrue(cooldowns.isCooldownDone("missing"));
    }

    @Test
    void serializedNumericValuesAreRestoredAsLongs() {
        CooldownContainer cooldowns = new CooldownContainer(Map.of("spell", 1_500), () -> 1_000);

        assertEquals(500, cooldowns.getCooldownLeft("spell"));
        assertEquals(Map.of("spell", 1_500L), cooldowns.serialize());
    }
}
