package me.devoria.spells.imanity.mages;

import me.devoria.spells.Spell;
import me.devoria.spells.imanity.mages.base.ManaPull;
import me.devoria.spells.imanity.mages.heavy.ManaBurst;
import me.devoria.spells.imanity.mages.movement.Teleport;
import me.devoria.spells.imanity.mages.util.Channeling;

public interface MageSpells {
    Spell MANA_PULL = new ManaPull();
    Spell CHANNELING = new Channeling();
    Spell TELEPORT = new Teleport();
    Spell MANA_BURST = new ManaBurst();
}
