package me.devoria.spells.lightseekers.elves;

import me.devoria.spells.Spell;
import me.devoria.spells.lightseekers.elves.base.LightSpear;
import me.devoria.spells.lightseekers.elves.heavy.ArrowRain;
import me.devoria.spells.lightseekers.elves.movement.LeapofFate;
import me.devoria.spells.lightseekers.elves.util.EyeofLight;

public class ElfSpells {
    public static Spell LIGHT_SPEAR = new LightSpear();
    public static Spell EYE_OF_LIGHT = new EyeofLight();
    public static Spell LEAP_OF_FATE = new LeapofFate();
    public static Spell ARROW_RAIN = new ArrowRain();
}
