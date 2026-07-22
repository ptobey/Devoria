package me.devoria.combat;

import java.util.Collections;
import java.util.EnumMap;
import java.util.Map;
import java.util.Objects;

public record DamageRoll(Map<DamageType, Integer> amounts) {

    public DamageRoll {
        Objects.requireNonNull(amounts, "amounts");
        EnumMap<DamageType, Integer> copy = new EnumMap<>(DamageType.class);
        for (DamageType type : DamageType.values()) {
            int amount = amounts.getOrDefault(type, 0);
            if (amount < 0) {
                throw new IllegalArgumentException("Damage cannot be negative: " + type);
            }
            copy.put(type, amount);
        }
        amounts = Collections.unmodifiableMap(copy);
    }

    public int amount(DamageType type) {
        return amounts.get(Objects.requireNonNull(type, "type"));
    }

    public int total() {
        int total = 0;
        for (int amount : amounts.values()) {
            total = Math.addExact(total, amount);
        }
        return total;
    }
}
