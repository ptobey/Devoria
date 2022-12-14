package me.devoria.spells.lightseekers.aquans;

import me.devoria.spells.Spell;
import me.devoria.spells.lightseekers.aquans.base.AquaJet;
import me.devoria.spells.lightseekers.aquans.heavy.WaterPrison;
import me.devoria.spells.lightseekers.aquans.movement.Ripwhirl;
import me.devoria.spells.lightseekers.aquans.util.OceanPower;

public interface AquanSpells {
    Spell AQUAJET = new AquaJet();
    Spell OCEAN_POWER = new OceanPower();
    Spell RIPWHIRL = new Ripwhirl();
    Spell WATER_PRISON = new WaterPrison();
}
