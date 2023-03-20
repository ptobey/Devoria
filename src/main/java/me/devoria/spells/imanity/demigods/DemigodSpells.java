package me.devoria.spells.imanity.demigods;

import me.devoria.spells.Spell;
import me.devoria.spells.imanity.demigods.base.SpinSlash;
import me.devoria.spells.imanity.demigods.heavy.GodsClaws;
import me.devoria.spells.imanity.demigods.movement.Lunge;
import me.devoria.spells.imanity.demigods.util.GodScream;

public interface DemigodSpells {
    Spell LUNGE = new Lunge();
    Spell SPINSLASH = new SpinSlash();
    Spell GODS_CLAWS = new GodsClaws();
    Spell GOD_SCREAM = new GodScream();
}
