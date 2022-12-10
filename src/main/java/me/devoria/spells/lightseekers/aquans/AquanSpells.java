package me.devoria.spells.lightseekers.aquans;

import me.devoria.spells.Spell;
import me.devoria.spells.lightseekers.aquans.base.AquaJet;
import me.devoria.spells.lightseekers.aquans.heavy.WaterPrison;
import me.devoria.spells.lightseekers.aquans.movement.Ripwhirl;
import me.devoria.spells.lightseekers.aquans.util.OceanPower;

public class AquanSpells {
    public static final Spell AQUAJET = new AquaJet();
    public static final Spell OCEAN_POWER = new OceanPower();
    public static final Spell RIPWHIRL = new Ripwhirl();
    public static final Spell WATER_PRISON = new WaterPrison();
}
