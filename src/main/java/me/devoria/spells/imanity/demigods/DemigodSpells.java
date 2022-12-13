package me.devoria.spells.imanity.demigods;

import me.devoria.spells.Spell;
import me.devoria.spells.imanity.demigods.base.Godswipe;
import me.devoria.spells.imanity.demigods.heavy.GodsWrath;
import me.devoria.spells.imanity.demigods.movement.Ascension;
import me.devoria.spells.imanity.demigods.util.CallofOrder;

public interface DemigodSpells {
    Spell GODSWIPE = new Godswipe();
    Spell CALL_OF_ORDER = new CallofOrder();
    Spell ASCENSION = new Ascension();
    Spell GODS_WRATH = new GodsWrath();
}
