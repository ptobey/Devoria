package me.devoria.spells.imanity.humans;

import me.devoria.spells.Spell;
import me.devoria.spells.imanity.humans.base.HeroicStrike;
import me.devoria.spells.imanity.humans.heavy.EnergyBurst;
import me.devoria.spells.imanity.humans.movement.Dash;
import me.devoria.spells.imanity.humans.util.AdventurersAura;

public class HumanSpells {
    public static Spell HEROIC_STRIKE = new HeroicStrike();
    public static Spell ADVENTURERS_AURA = new AdventurersAura();
    public static Spell DASH = new Dash();
    public static Spell ENERGY_BURST = new EnergyBurst();
}
