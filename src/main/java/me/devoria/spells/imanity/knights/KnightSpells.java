package me.devoria.spells.imanity.knights;

import me.devoria.spells.Spell;
import me.devoria.spells.imanity.knights.base.HeavySlam;
import me.devoria.spells.imanity.knights.heavy.KnightsJustice;
import me.devoria.spells.imanity.knights.movement.RageLeap;
import me.devoria.spells.imanity.knights.util.DivineProtection;

public interface KnightSpells {
    Spell HEAVY_SLAM = new HeavySlam();
    Spell DIVINE_PROTECTION = new DivineProtection();
    Spell RAGE_LEAP = new RageLeap();
    Spell KNIGHTS_JUSTICE = new KnightsJustice();
}
