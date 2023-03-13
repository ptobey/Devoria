package me.devoria.spells.imanity.humans.movement;

import me.devoria.Devoria;
import me.devoria.cooldowns.CooldownManager;
import me.devoria.spells.Spell;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Particle;
import org.bukkit.Sound;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;

public class Dash extends Spell {
    @Override
    public void cast(Player p, CooldownManager cooldownManager) {
        Location loc = p.getLocation();
        Vector direction = loc.getDirection();
        Vector yVelocity = new Vector(0, direction.getY() * 0.2 + 0.2, 0);
        p.setVelocity(direction.multiply(3).add(yVelocity));

        new BukkitRunnable() {
            boolean mTickOne = true;

            @Override
            public void run() {
                if (p.isDead() || !p.isOnline() || !p.getLocation().isChunkLoaded()) {
                    this.cancel();
                    return;
                }

                Material block = p.getLocation().getBlock().getType();
                if (!mTickOne && (p.isOnGround() || block == Material.WATER || block == Material.LAVA || block == Material.LADDER)) {
                    p.getWorld().spawnParticle(Particle.FLAME, p.getLocation(), 175, 0, 0, 0, 0.175);
                    p.getWorld().spawnParticle(Particle.SMOKE_LARGE, p.getLocation(), 50, 0, 0, 0, 0.3);
                    p.getWorld().spawnParticle(Particle.EXPLOSION_NORMAL, p.getLocation(), 50, 0, 0, 0, 0.3);
                    p.getWorld().playSound(p.getLocation(), Sound.ENTITY_GENERIC_EXPLODE, 1f, 1f);
                    for (Entity nearbyEntity : p.getLocation().getNearbyEntities(2, 2, 2)) {
                        if (!(nearbyEntity instanceof LivingEntity target) || nearbyEntity.equals(p)) continue;
                        target.damage(5, p);
                    }
                    this.cancel();
                    return;
                }

                mTickOne = false;
            }
        }.runTaskTimer(Devoria.getInstance(), 0, 1);

        p.getWorld().playSound(p.getLocation(), Sound.ITEM_TRIDENT_RIPTIDE_3, 0.6f, 1.8f);
    }

    @Override
    public String toString() {
        return "Dash";
    }
}
