package me.devoria.spells.imanity.humans.util;

import me.devoria.cooldowns.CooldownManager;
import me.devoria.spells.Spell;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class AdventurersAura extends Spell {
    @Override
    public void cast(Player p, CooldownManager cooldownManager) {
        p.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, 200, 1));

        // Particles
    }

    @Override
    public String toString() {
        return "AdventurersAura";
    }
}
