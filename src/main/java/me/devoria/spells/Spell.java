package me.devoria.spells;

import javax.annotation.Nullable;
import me.devoria.cooldowns.CooldownManager;
import me.devoria.spells.imanity.demigods.DemigodSpells;
import me.devoria.spells.imanity.humans.HumanSpells;
import me.devoria.spells.imanity.knights.KnightSpells;
import me.devoria.spells.imanity.mages.MageSpells;
import me.devoria.spells.lightseekers.angels.AngelSpells;
import me.devoria.spells.lightseekers.aquans.AquanSpells;
import me.devoria.spells.lightseekers.astreans.AstreanSpells;
import me.devoria.spells.lightseekers.elves.ElfSpells;
import org.bukkit.entity.Player;

public abstract class Spell {
    public abstract void cast(Player p, CooldownManager cooldownManager);
    public abstract String toString();

    public static Spell fromString(String string) {
        return switch (string) {
            case "Godswipe" -> DemigodSpells.GODSWIPE;
            case "GodsWrath" -> DemigodSpells.GODS_WRATH;
            case "Ascension" -> DemigodSpells.ASCENSION;
            case "CallOfOrder" -> DemigodSpells.CALL_OF_ORDER;
            case "HeroicStrike" -> HumanSpells.HEROIC_STRIKE;
            case "EnergyBurst" -> HumanSpells.ENERGY_BURST;
            case "Dash" -> HumanSpells.DASH;
            case "AdventurersAura" -> HumanSpells.ADVENTURERS_AURA;
            case "HeavySlam" -> KnightSpells.HEAVY_SLAM;
            case "KnightsJustice" -> KnightSpells.KNIGHTS_JUSTICE;
            case "RageLeap" -> KnightSpells.RAGE_LEAP;
            case "DivineProtection" -> KnightSpells.DIVINE_PROTECTION;
            case "ManaPull" -> MageSpells.MANA_PULL;
            case "ManaBurst" -> MageSpells.MANA_BURST;
            case "Teleport" -> MageSpells.TELEPORT;
            case "Channeling" -> MageSpells.CHANNELING;
            case "AngelicVoice" -> AngelSpells.ANGELIC_VOICE;
            case "CurseOfTheDamned" -> AngelSpells.CURSE_OF_THE_DAMNED;
            case "Flight" -> AngelSpells.FLIGHT;
            case "Aura" -> AngelSpells.AURA;
            case "AquaJet" -> AquanSpells.AQUAJET;
            case "WaterPrison" -> AquanSpells.WATER_PRISON;
            case "Ripwhirl" -> AquanSpells.RIPWHIRL;
            case "OceanPower" -> AquanSpells.OCEAN_POWER;
            case "LightSurge" -> AstreanSpells.LIGHT_SURGE;
            case "Omniblast" -> AstreanSpells.OMNIBLAST;
            case "Warp" -> AstreanSpells.WARP;
            case "LightPlague" -> AstreanSpells.LIGHT_PLAGUE;
            case "LightSpear" -> ElfSpells.LIGHT_SPEAR;
            case "ArrowRain" -> ElfSpells.ARROW_RAIN;
            case "LeapOfFate" -> ElfSpells.LEAP_OF_FATE;
            case "EyeOfLight" -> ElfSpells.EYE_OF_LIGHT;
            case "Default" -> DefaultSpells.DEFAULT;
            default -> throw new IllegalStateException("Unexpected value: " + string);
        };
    }
}
