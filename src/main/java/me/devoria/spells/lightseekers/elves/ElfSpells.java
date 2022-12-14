package me.devoria.spells.lightseekers.elves;

import me.devoria.spells.Spell;
import me.devoria.spells.lightseekers.elves.base.LightSpear;
import me.devoria.spells.lightseekers.elves.heavy.ArrowRain;
import me.devoria.spells.lightseekers.elves.movement.LeapofFate;
import me.devoria.spells.lightseekers.elves.util.EyeofLight;

public interface ElfSpells {
    Spell LIGHT_SPEAR = new LightSpear();
    Spell EYE_OF_LIGHT = new EyeofLight();
    Spell LEAP_OF_FATE = new LeapofFate();
    Spell ARROW_RAIN = new ArrowRain();
}
