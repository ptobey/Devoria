package me.devoria.spells;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import java.util.UUID;

/** Tracks entities already affected by one spell cast. */
public final class CastTargetLedger {
    private final Set<UUID> claimedTargets = new HashSet<>();

    public boolean claim(UUID targetId) {
        return claimedTargets.add(Objects.requireNonNull(targetId, "targetId"));
    }
}
