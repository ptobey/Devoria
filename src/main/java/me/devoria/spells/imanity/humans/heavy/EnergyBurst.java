package me.devoria.spells.imanity.humans.heavy;

import me.devoria.Devoria;
import me.devoria.cooldowns.CooldownManager;
import me.devoria.spells.Spell;
import org.bukkit.Location;
import org.bukkit.Particle;
import org.bukkit.Sound;
import org.bukkit.World;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

public class EnergyBurst extends Spell {
    private static final int RADIUS = 5;
    private static final int DAMAGE = 5;

    @Override
    public void cast(Player p, CooldownManager cooldownManager) {
        for (Entity entity : p.getNearbyEntities(RADIUS, RADIUS, RADIUS)) {
            if (!(entity instanceof LivingEntity) || entity == p) continue;
            ((LivingEntity) entity).damage(DAMAGE, p);
        }
        World world = p.getWorld();
        Location loc = p.getLocation();
        world.playSound(p.getLocation(), Sound.BLOCK_BEACON_ACTIVATE, 0.2f, 2f);
        world.playSound(p.getLocation(), Sound.BLOCK_BELL_RESONATE, 2f, 2f);
        new BukkitRunnable() {
            double t = Math.PI / 4;

            public void run() {
                t = t + 0.1 * Math.PI;
                for (double theta = 0; theta <= 2 * Math.PI; theta = theta + Math.PI / 32) {
                    double x = t * Math.cos(theta);
                    double y = 2 * Math.exp(-0.1 * t) * Math.sin(t) + 1.5;
                    double z = t * Math.sin(theta);
                    loc.add(x, y, z);
                    world.spawnParticle(Particle.FIREWORKS_SPARK, loc, 1, 0, 0, 0, 0);
                    loc.subtract(x, y, z);

                    theta = theta + Math.PI / 64;

                    x = t * Math.cos(theta);
                    y = 2 * Math.exp(-0.1 * t) * Math.sin(t) + 1.5;
                    z = t * Math.sin(theta);
                    loc.add(x, y, z);
                    world.spawnParticle(Particle.SPELL_WITCH, loc, 1, 0, 0, 0);
                    loc.subtract(x, y, z);
                }
                if (t > 8) {
                    for (Entity entity : p.getNearbyEntities(RADIUS, RADIUS, RADIUS)) {
                        if (!(entity instanceof LivingEntity) || entity == p) continue;
                        ((LivingEntity) entity).damage(DAMAGE, p);
                    }

                    world.playSound(p.getLocation(), Sound.ITEM_TRIDENT_RIPTIDE_3, 0.6f, 1.2f);
                    world.playSound(p.getLocation(), Sound.ENTITY_SHULKER_SHOOT, 0.6f, 1.2f);
                    world.playSound(p.getLocation(), Sound.ENTITY_SHULKER_BULLET_HIT, 0.8f, 0.5f);
                    world.playSound(p.getLocation(), Sound.ENTITY_PLAYER_BREATH, 2f, 2f);
                    world.playSound(p.getLocation(), Sound.ENTITY_ENDER_DRAGON_SHOOT, 0.7f, 2f);
                    world.playSound(p.getLocation(), Sound.ENTITY_FIREWORK_ROCKET_LAUNCH, 2f, 1.4f);
                    this.cancel();
                }
            }
        }.runTaskTimer(Devoria.getInstance(), 0L, 1L);
    }

    @Override
    public String toString() {
        return "EnergyBurst";
    }
}
