package me.devoria.spells.lightseekers.angels;

import me.devoria.spells.Spell;
import me.devoria.spells.lightseekers.angels.base.AngelicChaos;
import me.devoria.spells.lightseekers.angels.heavy.DivineWrath;
import me.devoria.spells.lightseekers.angels.movement.Flight;
import me.devoria.spells.lightseekers.angels.util.Lament;

public interface AngelSpells {
    Spell ANGELIC_CHAOS = new AngelicChaos();
    Spell LAMENT = new Lament();
    Spell FLIGHT = new Flight();
    Spell DIVINE_WRATH = new DivineWrath();
}
