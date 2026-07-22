package me.devoria.spells;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicBoolean;
import org.junit.jupiter.api.Test;

class SpellCastTransactionTest {

    @Test
    void successfulCastExecutesAndCommitsResourcesOnce() {
        FakeResources resources = new FakeResources(80, 100);
        AtomicBoolean executed = new AtomicBoolean();

        SpellCastTransaction.Result result = SpellCastTransaction.attempt(
                resources, new SpellCastRule(30, 3_000), "heavy", true,
                () -> executed.compareAndSet(false, true));

        assertEquals(SpellCastTransaction.Outcome.SUCCESS, result.outcome());
        assertTrue(executed.get());
        assertEquals(50, resources.mana);
        assertEquals(3_000L, resources.cooldowns.get("heavy"));
        assertEquals(1, resources.commits);
    }

    @Test
    void insufficientManaDoesNotExecuteOrMutate() {
        FakeResources resources = new FakeResources(29, 100);
        AtomicBoolean executed = new AtomicBoolean();

        SpellCastTransaction.Result result = SpellCastTransaction.attempt(
                resources, new SpellCastRule(30, 3_000), "heavy", true,
                () -> {
                    executed.set(true);
                    return true;
                });

        assertEquals(SpellCastTransaction.Outcome.INSUFFICIENT_MANA,
                result.outcome());
        assertFalse(executed.get());
        assertEquals(29, resources.mana);
        assertEquals(0, resources.commits);
    }

    @Test
    void activeCooldownReportsRoundedUpSecondsWithoutMutation() {
        FakeResources resources = new FakeResources(100, 100);
        resources.cooldowns.put("base", 1_001L);

        SpellCastTransaction.Result result = SpellCastTransaction.attempt(
                resources, new SpellCastRule(10, 1_000), "base", true,
                () -> true);

        assertEquals(SpellCastTransaction.Outcome.ACTIVE_COOLDOWN,
                result.outcome());
        assertEquals(2, result.remainingCooldownSeconds());
        assertEquals(100, resources.mana);
        assertEquals(0, resources.commits);
    }

    @Test
    void invalidStateDoesNotInspectEffectOrMutate() {
        FakeResources resources = new FakeResources(100, 100);
        AtomicBoolean executed = new AtomicBoolean();

        SpellCastTransaction.Result result = SpellCastTransaction.attempt(
                resources, new SpellCastRule(10, 1_000), "base", false,
                () -> {
                    executed.set(true);
                    return true;
                });

        assertEquals(SpellCastTransaction.Outcome.INVALID_STATE,
                result.outcome());
        assertFalse(executed.get());
        assertEquals(0, resources.commits);
    }

    @Test
    void exactManaCostSucceedsAtZeroWithoutGoingNegative() {
        FakeResources resources = new FakeResources(15, 100);

        SpellCastTransaction.Result result = SpellCastTransaction.attempt(
                resources, new SpellCastRule(15, 1_000), "movement", true,
                () -> true);

        assertEquals(SpellCastTransaction.Outcome.SUCCESS, result.outcome());
        assertEquals(0, resources.mana);
        assertEquals(0, result.remainingMana());
    }

    @Test
    void cooldownsAreIndependentPerSpellSlot() {
        FakeResources resources = new FakeResources(100, 100);
        SpellCastRule rule = new SpellCastRule(10, 1_000);

        assertEquals(SpellCastTransaction.Outcome.SUCCESS,
                SpellCastTransaction.attempt(resources, rule, "base", true,
                        () -> true).outcome());
        assertEquals(SpellCastTransaction.Outcome.SUCCESS,
                SpellCastTransaction.attempt(resources, rule, "movement", true,
                        () -> true).outcome());
        assertEquals(SpellCastTransaction.Outcome.ACTIVE_COOLDOWN,
                SpellCastTransaction.attempt(resources, rule, "base", true,
                        () -> true).outcome());

        assertEquals(80, resources.mana);
        assertEquals(Map.of("base", 1_000L, "movement", 1_000L),
                resources.cooldowns);
    }

    @Test
    void rejectedEffectDoesNotChargeOrStartCooldown() {
        FakeResources resources = new FakeResources(100, 100);

        SpellCastTransaction.Result result = SpellCastTransaction.attempt(
                resources, new SpellCastRule(20, 4_000), "utility", true,
                () -> false);

        assertEquals(SpellCastTransaction.Outcome.REJECTED, result.outcome());
        assertEquals(100, resources.mana);
        assertTrue(resources.cooldowns.isEmpty());
        assertEquals(0, resources.commits);
    }

    @Test
    void corruptManaStateIsRejectedBeforeItCanEscapeBounds() {
        FakeResources resources = new FakeResources(101, 100);

        SpellCastTransaction.Result result = SpellCastTransaction.attempt(
                resources, new SpellCastRule(10, 1_000), "base", true,
                () -> true);

        assertEquals(SpellCastTransaction.Outcome.INVALID_STATE,
                result.outcome());
        assertEquals(101, resources.mana);
        assertEquals(0, resources.commits);
    }

    private static final class FakeResources
            implements SpellCastTransaction.Resources {
        private int mana;
        private final int maxMana;
        private final Map<String, Long> cooldowns = new HashMap<>();
        private int commits;

        private FakeResources(int mana, int maxMana) {
            this.mana = mana;
            this.maxMana = maxMana;
        }

        @Override
        public int currentMana() {
            return mana;
        }

        @Override
        public int maxMana() {
            return maxMana;
        }

        @Override
        public long cooldownRemaining(String cooldownKey) {
            return cooldowns.getOrDefault(cooldownKey, 0L);
        }

        @Override
        public void commit(int newMana, String cooldownKey,
                int cooldownMillis) {
            mana = newMana;
            cooldowns.put(cooldownKey, (long) cooldownMillis);
            commits++;
        }
    }
}
