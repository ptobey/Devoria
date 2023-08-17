package me.devoria.spells.lightseekers.angels.util;

import me.devoria.Devoria;
import me.devoria.cooldowns.CooldownManager;
import me.devoria.spells.Spell;
import me.devoria.utils.FastUtils;
import me.devoria.utils.ParticleUtils;
import me.devoria.utils.PlayerUtils;
import org.bukkit.Color;
import org.bukkit.Location;
import org.bukkit.Particle;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;

public class Lament extends Spell {
    @Override
    public void cast(Player p, CooldownManager cooldownManager) {
        new BukkitRunnable() {
            int mTicks = 0;
            Location loc = p.getLocation();

            @Override
            public void run() {
                if (mTicks == 100) this.cancel();
                mTicks += 10;
                for (Entity entity : loc.getNearbyEntities(5, 5, 5)) {
                    if (!(entity instanceof LivingEntity livingEntity)) continue;
                    if (livingEntity instanceof Player player) {
                        double maxHealth = Integer.parseInt(FastUtils.map(p.getMetadata("attributes").get(0).asString()).get("health"));
                        PlayerUtils.changeHealth(p, (int) (maxHealth * 0.01), p, false);
                    } else {
                        Vector kb = ParticleUtils.getDirection(loc, livingEntity.getLocation());
                        livingEntity.setVelocity(kb);
                        livingEntity.damage(1, p);
                    }
                }
                for (Vector vector : ParticleUtils.getSpherePoints(5)) {
                    loc.getWorld().spawnParticle(Particle.REDSTONE, loc.add(vector), 1, new Particle.DustOptions(Color.fromRGB(245, 156, 255), 2));
                    loc.getWorld().spawnParticle(Particle.ELECTRIC_SPARK, loc.add(vector), 1);
                    loc.getWorld().spawnParticle(Particle.CRIT_MAGIC, loc.add(vector), 1);
                    loc.getWorld().spawnParticle(Particle.FALLING_SPORE_BLOSSOM, loc.add(vector), 1);
                }
            }
        }.runTaskTimer(Devoria.getInstance(), 0L, 10L);
    }

    @Override
    public String toString() {
        return "Lament";
    }
}
