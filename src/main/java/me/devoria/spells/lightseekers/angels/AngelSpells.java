package me.devoria.spells.lightseekers.angels;

import me.devoria.spells.Spell;
import me.devoria.spells.lightseekers.angels.base.AngelicVoice;
import me.devoria.spells.lightseekers.angels.heavy.CurseoftheDamned;
import me.devoria.spells.lightseekers.angels.movement.Flight;
import me.devoria.spells.lightseekers.angels.util.Aura;

public interface AngelSpells {
    Spell ANGELIC_VOICE = new AngelicVoice();
    Spell AURA = new Aura();
    Spell FLIGHT = new Flight();
    Spell CURSE_OF_THE_DAMNED = new CurseoftheDamned();
}
