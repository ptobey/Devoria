package me.devoria.spells.imanity.knights;

import me.devoria.spells.Spell;
import me.devoria.spells.imanity.knights.base.HeavySlam;
import me.devoria.spells.imanity.knights.heavy.KnightsJustice;
import me.devoria.spells.imanity.knights.movement.RageLeap;
import me.devoria.spells.imanity.knights.util.DivineProtection;

public class KnightSpells {
    public static final Spell HEAVY_SLAM = new HeavySlam();
    public static final Spell DIVINE_PROTECTION = new DivineProtection();
    public static final Spell RAGE_LEAP = new RageLeap();
    public static final Spell KNIGHTS_JUSTICE = new KnightsJustice();
}
