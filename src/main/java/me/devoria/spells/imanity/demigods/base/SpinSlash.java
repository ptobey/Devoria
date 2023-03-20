package me.devoria.spells.imanity.demigods.base;

import me.devoria.Devoria;
import me.devoria.cooldowns.CooldownManager;
import me.devoria.spells.Spell;
import me.devoria.utils.ParticleUtils;
import org.bukkit.Color;
import org.bukkit.Location;
import org.bukkit.Particle;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

public class SpinSlash extends Spell {
    @Override
    public void cast(Player p, CooldownManager cooldownManager) {
        ParticleUtils.summonCircle(p.getLocation(), 3, Particle.FLAME);
        ParticleUtils.summonCircle(p.getLocation(), 3, Particle.SOUL_FIRE_FLAME);
        ParticleUtils.summonCircle(p.getLocation(), 3, Particle.FALLING_OBSIDIAN_TEAR);
        ParticleUtils.summonCircle(p.getLocation(), 3, new Particle.DustOptions(Color.PURPLE, 2));
        new BukkitRunnable() {
            int mTicks = 0;

            @Override
            public void run() {
                if (mTicks >= 90) this.cancel();
                Location particleLoc = p.getLocation().clone();
                // Cosine for X
                particleLoc.setX(particleLoc.getX() + Math.cos(mTicks) * 3);
                // Sine for Z
                particleLoc.setZ(particleLoc.getZ() + Math.sin(mTicks) * 3);
                p.getWorld().spawnParticle(Particle.SWEEP_ATTACK, particleLoc, 1, 0, 0, 0);
                mTicks += 3;
            }
        }.runTaskTimer(Devoria.getInstance(), 0L, 1L);
    }

    @Override
    public String toString() {
        return "SpinSlash";
    }
}
