package me.devoria.spells;

import me.devoria.cooldowns.CooldownManager;
import me.devoria.spells.imanity.humans.HumanSpells;
import me.devoria.spells.lightseekers.elves.ElfSpells;
import org.bukkit.entity.Player;

public abstract class Spell {
    public void cast(Player p, CooldownManager cooldownManager) {

    }

    public static Spell fromString(String string) {
        switch (string) {
            case "HeroicStrike": return HumanSpells.HEROIC_STRIKE;
            case "EnergyBurst": return HumanSpells.ENERGY_BURST;
            case "Dash": return HumanSpells.DASH;
            case "AdventurersAura": return HumanSpells.ADVENTURERS_AURA;
            case "LightSpear": return ElfSpells.LIGHT_SPEAR;
            case "ArrowRain": return ElfSpells.ARROW_RAIN;
            case "LeapOfFate": return ElfSpells.LEAP_OF_FATE;
            case "EyeOfLight": return ElfSpells.EYE_OF_LIGHT;
            case default: return null;
        }
    }

    public String toString() {
        return null;
    }
}
