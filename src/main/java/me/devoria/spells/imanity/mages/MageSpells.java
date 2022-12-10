package me.devoria.spells.imanity.mages;

import me.devoria.spells.Spell;
import me.devoria.spells.imanity.mages.base.ManaPull;
import me.devoria.spells.imanity.mages.heavy.ManaBurst;
import me.devoria.spells.imanity.mages.movement.Teleport;
import me.devoria.spells.imanity.mages.util.Channeling;

public class MageSpells {
    public static Spell MANA_PULL = new ManaPull();
    public static Spell CHANNELING = new Channeling();
    public static Spell TELEPORT = new Teleport();
    public static Spell MANA_BURST = new ManaBurst();
}
