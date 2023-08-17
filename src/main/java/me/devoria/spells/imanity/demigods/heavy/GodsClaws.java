package me.devoria.spells.imanity.demigods.heavy;

import me.devoria.Devoria;
import me.devoria.cooldowns.CooldownManager;
import me.devoria.spells.Spell;
import me.devoria.utils.FastUtils;
import org.bukkit.Location;
import org.bukkit.Particle;
import org.bukkit.Sound;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;

public class GodsClaws extends Spell {
    @Override
    public void cast(Player p, CooldownManager cooldownManager) {
        p.getWorld().playSound(p.getLocation(), Sound.BLOCK_BELL_RESONATE, 2f, 2f);
        p.getWorld().playSound(p.getLocation(), Sound.BLOCK_BEACON_ACTIVATE, 0.2f, 2f);
        new BukkitRunnable() {
            int time = 0;

            @Override
            public void run() {
                time++;
                if (time == 25) {
                    p.getWorld().playSound(p.getLocation(), Sound.ITEM_TRIDENT_RIPTIDE_3, 0.6f, 1.2f);
                    p.getWorld().playSound(p.getLocation(), Sound.ENTITY_SHULKER_SHOOT, 0.6f, 1.2f);
                    p.getWorld().playSound(p.getLocation(), Sound.ENTITY_SHULKER_BULLET_HIT, 0.8f, 0.5f);
                    p.getWorld().playSound(p.getLocation(), Sound.ENTITY_PLAYER_BREATH, 2f, 2f);
                    p.getWorld().playSound(p.getLocation(), Sound.ENTITY_ENDER_DRAGON_SHOOT, 0.7f, 2f);
                    p.getWorld().playSound(p.getLocation(), Sound.ENTITY_FIREWORK_ROCKET_LAUNCH, 2f, 1.4f);
                    p.getWorld().spawnParticle(Particle.FLASH, p.getLocation(), 5, 0, 0, 0, 0.175);
                    p.getWorld().spawnParticle(Particle.SOUL_FIRE_FLAME, p.getEyeLocation(), 200, 0, 0, 0, 1);

                    for (Entity nearbyEntity : p.getLocation().getNearbyEntities(2, 2, 2)) {
                        if (!(nearbyEntity instanceof LivingEntity) || nearbyEntity.equals(p)) continue;
                        LivingEntity target = (LivingEntity) nearbyEntity;
                        target.damage(10, p);
                    }
                    this.cancel();
                } else if (time > 10) {
                } else if (time > 5) {
                    effect(p.getLocation(), 3);
                } else {
                    effect(p.getLocation(), 1);
                }
            }
        }.runTaskTimer(Devoria.getInstance(), 0L, 1L);
    }

    private static void effect(Location loc, int particleMultiplier) {
        int minimum = -6; // Easier changing the minimum
        int maximum = 6; // Easier changing the maximum

        for (int i = 0; i < 15 * particleMultiplier; i++) {
            double x = FastUtils.randomDoubleInRange(minimum, maximum);
            double y = FastUtils.randomDoubleInRange(minimum, maximum);
            double z = FastUtils.randomDoubleInRange(minimum, maximum);
            Location locclone = loc.clone();
            locclone.add(x, y, z);
            Vector dir = loc.toVector().subtract(locclone.toVector()).normalize();
            double dx = dir.getX();
            double dy = dir.getY();
            double dz = dir.getZ();
            loc.getWorld().spawnParticle(Particle.DRAGON_BREATH, locclone, 0, (float) dx, (float) dy, (float) dz, 0.3 / particleMultiplier);
        }
    }

    @Override
    public String toString() {
        return "GodsClaws";
    }
}
