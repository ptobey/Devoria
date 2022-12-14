package me.devoria.spells.imanity.humans;

import me.devoria.spells.Spell;
import me.devoria.spells.imanity.humans.base.HeroicStrike;
import me.devoria.spells.imanity.humans.heavy.EnergyBurst;
import me.devoria.spells.imanity.humans.movement.Dash;
import me.devoria.spells.imanity.humans.util.AdventurersAura;

public interface HumanSpells {
    Spell HEROIC_STRIKE = new HeroicStrike();
    Spell ADVENTURERS_AURA = new AdventurersAura();
    Spell DASH = new Dash();
    Spell ENERGY_BURST = new EnergyBurst();
}
