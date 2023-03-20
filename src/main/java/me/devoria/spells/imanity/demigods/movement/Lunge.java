package me.devoria.spells.imanity.demigods.movement;

import me.devoria.Devoria;
import me.devoria.cooldowns.CooldownManager;
import me.devoria.spells.Spell;
import org.bukkit.Location;
import org.bukkit.Particle;
import org.bukkit.Sound;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;

public class Lunge extends Spell {
    public void cast(Player player, CooldownManager cooldownManager) {
        player.setVelocity(new Vector(0, 1, 0)); // Launch player forward and up
        new BukkitRunnable() {
            Location mSavedLocation = player.getLocation();
            boolean mHasHitEntity = false;
            int mTick = 0;

            @Override
            public void run() {
                mTick++;

                if (player.isDead() || !player.isOnline()) {
                    this.cancel();
                    return;
                }
                player.getWorld().spawnParticle(Particle.SOUL_FIRE_FLAME, player.getLocation(), 3, 0, 0, 0, 0.1);

                if (player.getLocation().getY() < mSavedLocation.getY() && !mHasHitEntity) {
                    // Player is still jumping up, check if nearest entity is in range
                    Entity nearest = getNearestValidEntity(player, 10, 10, 10);
                    if (nearest != null) {
                        mHasHitEntity = true;
                        Vector direction = nearest.getLocation().toVector().subtract(player.getLocation().toVector()).normalize();
                        player.setVelocity(direction.multiply(2)); // Set player velocity towards nearest entity
                    } else {
                        mHasHitEntity = true;
                        player.setVelocity(player.getLocation().getDirection().multiply(2));
                    }
                } else if (player.isOnGround() && mTick > 1) {
                    player.getWorld().spawnParticle(Particle.FLAME, player.getLocation(), 175, 0, 0, 0, 0.175);
                    player.getWorld().spawnParticle(Particle.SMOKE_LARGE, player.getLocation(), 50, 0, 0, 0, 0.3);
                    player.getWorld().spawnParticle(Particle.EXPLOSION_NORMAL, player.getLocation(), 50, 0, 0, 0, 0.3);
                    player.getWorld().playSound(player.getLocation(), Sound.ENTITY_GENERIC_EXPLODE, 1f, 1f);
                    for (Entity nearbyEntity : player.getLocation().getNearbyEntities(2, 2, 2)) {
                        if (!(nearbyEntity instanceof LivingEntity target) || nearbyEntity.equals(player)) continue;
                        target.damage(5, player);
                    }
                    this.cancel();
                }

                mSavedLocation = player.getLocation();
            }
        }.runTaskTimer(Devoria.getInstance(), 0L, 1L);
    }


    private Entity getNearestValidEntity(Player player, double radiusX, double radiusY, double radiusZ) {
        Entity target = null;
        double nearestDistanceSquared = Double.MAX_VALUE;
        for (Entity entity : player.getNearbyEntities(radiusX, radiusY, radiusZ)) {
            if (!(entity instanceof LivingEntity) || entity.isDead() || !player.hasLineOfSight(entity)) {
                continue;
            }
            double distanceSquared = player.getLocation().distanceSquared(entity.getLocation());
            if (distanceSquared < nearestDistanceSquared) {
                nearestDistanceSquared = distanceSquared;
                target = entity;
            }
        }
        return target;
    }


    @Override
    public String toString() {
        return "Lunge";
    }

}
