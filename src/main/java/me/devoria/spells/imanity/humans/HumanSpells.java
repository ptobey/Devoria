package me.devoria.spells.imanity.humans;

import me.devoria.spells.Spell;
import me.devoria.spells.imanity.humans.base.HeroicStrike;
import me.devoria.spells.imanity.humans.heavy.EnergyBurst;
import me.devoria.spells.imanity.humans.movement.Dash;
import me.devoria.spells.imanity.humans.util.AdventurersAura;

public class HumanSpells {
    public static final Spell HEROIC_STRIKE = new HeroicStrike();
    public static final Spell ADVENTURERS_AURA = new AdventurersAura();
    public static final Spell DASH = new Dash();
    public static final Spell ENERGY_BURST = new EnergyBurst();
}
