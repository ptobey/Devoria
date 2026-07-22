package me.devoria.spells;

import java.util.Objects;
import java.util.function.BooleanSupplier;

/**
 * Coordinates one spell effect with its mana and cooldown mutation. Calls are
 * expected on the server thread, so the resource commit is one synchronous
 * operation after all rejection paths have returned.
 */
public final class SpellCastTransaction {
    private SpellCastTransaction() {
    }

    public enum Outcome {
        SUCCESS,
        INSUFFICIENT_MANA,
        ACTIVE_COOLDOWN,
        INVALID_STATE,
        REJECTED
    }

    public record Result(Outcome outcome, int manaCost,
            int remainingMana, long remainingCooldownMillis) {
        public long remainingCooldownSeconds() {
            return remainingCooldownMillis <= 0
                    ? 0
                    : (remainingCooldownMillis + 999) / 1_000;
        }
    }

    public interface Resources {
        int currentMana();

        int maxMana();

        long cooldownRemaining(String cooldownKey);

        void commit(int newMana, String cooldownKey, int cooldownMillis);
    }

    public static Result attempt(Resources resources, SpellCastRule rule,
            String cooldownKey, boolean stateValid, BooleanSupplier executeEffect) {
        Objects.requireNonNull(resources, "resources");
        Objects.requireNonNull(rule, "rule");
        Objects.requireNonNull(cooldownKey, "cooldownKey");
        Objects.requireNonNull(executeEffect, "executeEffect");

        int currentMana = resources.currentMana();
        int maxMana = resources.maxMana();
        if (!stateValid || maxMana < 0 || currentMana < 0 || currentMana > maxMana) {
            return result(Outcome.INVALID_STATE, rule, currentMana, 0);
        }

        long remainingCooldown = Math.max(0,
                resources.cooldownRemaining(cooldownKey));
        if (remainingCooldown > 0) {
            return result(Outcome.ACTIVE_COOLDOWN, rule, currentMana,
                    remainingCooldown);
        }

        if (currentMana < rule.manaCost()) {
            return result(Outcome.INSUFFICIENT_MANA, rule, currentMana, 0);
        }

        if (!executeEffect.getAsBoolean()) {
            return result(Outcome.REJECTED, rule, currentMana, 0);
        }

        int remainingMana = currentMana - rule.manaCost();
        resources.commit(remainingMana, cooldownKey, rule.cooldownMillis());
        return result(Outcome.SUCCESS, rule, remainingMana, 0);
    }

    private static Result result(Outcome outcome, SpellCastRule rule,
            int remainingMana, long remainingCooldown) {
        return new Result(outcome, rule.manaCost(), remainingMana,
                remainingCooldown);
    }
}
