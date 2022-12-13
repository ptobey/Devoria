package me.devoria.spells.lightseekers.astreans;

import me.devoria.spells.Spell;
import me.devoria.spells.lightseekers.astreans.base.LightSurge;
import me.devoria.spells.lightseekers.astreans.heavy.Omniblast;
import me.devoria.spells.lightseekers.astreans.movement.Warp;
import me.devoria.spells.lightseekers.astreans.util.LightPlague;

public interface AstreanSpells {
    Spell LIGHT_SURGE = new LightSurge();
    Spell OMNIBLAST = new Omniblast();
    Spell WARP = new Warp();
    Spell LIGHT_PLAGUE = new LightPlague();
}
