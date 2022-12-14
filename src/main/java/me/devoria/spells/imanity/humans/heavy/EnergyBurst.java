package me.devoria.spells.imanity.humans.heavy;

import me.devoria.cooldowns.CooldownManager;
import me.devoria.spells.Spell;
import me.devoria.utils.FastUtils;
import me.devoria.utils.ParticleUtils;
import org.bukkit.entity.Player;
import org.bukkit.util.Vector;

public class EnergyBurst extends Spell {
    @Override
    public void cast(Player p, CooldownManager cooldownManager) {
        // Create Sphere

        double radian1 = Math.toRadians(90);
        Vector vec = new Vector(FastUtils.cos(radian1) * 0.325, 0, FastUtils.sin(radian1) * 0.325);
        vec = ParticleUtils.rotateXAxis(vec, -90);
    }

    @Override
    public String toString() {
        return "EnergyBurst";
    }
}
