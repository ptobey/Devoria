package me.devoria.combat;

import java.util.EnumMap;
import java.util.Map;
import java.util.Objects;
import java.util.function.IntSupplier;

public final class DamageCalculator {

    private DamageCalculator() {
    }

    public static DamageRoll roll(
            Map<String, String> attributes,
            IntSupplier percentileSupplier) {
        Objects.requireNonNull(attributes, "attributes");
        Objects.requireNonNull(percentileSupplier, "percentileSupplier");

        EnumMap<DamageType, Integer> amounts = new EnumMap<>(DamageType.class);
        for (DamageType type : DamageType.values()) {
            String range = attributes.get(type.legacyKey());
            int amount = range == null
                    ? 0
                    : StatRange.parse(range).atPercentile(percentileSupplier.getAsInt());
            amounts.put(type, amount);
        }
        return new DamageRoll(amounts);
    }
}
