package me.devoria.spells.imanity.humans.heavy;

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

public class EnergyBurst extends Spell {
    @Override
    public void cast(Player p, CooldownManager cooldownManager) {
        p.getWorld().playSound(p.getLocation(), Sound.BLOCK_BELL_RESONATE, 2f, 2f);
        p.getWorld().playSound(p.getLocation(), Sound.BLOCK_BEACON_ACTIVATE, 0.2f, 2f);
        new BukkitRunnable() {
            int time = 0;

            @Override
            public void run() {
                time++;
                if (time == 40) {
                    p.getWorld().playSound(p.getLocation(), Sound.ITEM_TRIDENT_RIPTIDE_3, 0.6f, 1.2f);
                    p.getWorld().playSound(p.getLocation(), Sound.ENTITY_SHULKER_SHOOT, 0.6f, 1.2f);
                    p.getWorld().playSound(p.getLocation(), Sound.ENTITY_SHULKER_BULLET_HIT, 0.8f, 0.5f);
                    p.getWorld().playSound(p.getLocation(), Sound.ENTITY_PLAYER_BREATH, 2f, 2f);
                    p.getWorld().playSound(p.getLocation(), Sound.ENTITY_ENDER_DRAGON_SHOOT, 0.7f, 2f);
                    p.getWorld().playSound(p.getLocation(), Sound.ENTITY_FIREWORK_ROCKET_LAUNCH, 2f, 1.4f);
                    p.getWorld().spawnParticle(Particle.FLASH, p.getLocation(), 5, 0, 0, 0, 0.175);
                    p.getWorld().spawnParticle(Particle.TOTEM, p.getEyeLocation(), 200, 0, 0, 0, 1);
                    this.cancel();
                }
                if (time > 30) {
                    effect(p.getLocation(), 5, 5, p);
                } else if (time > 20) {
                    effect(p.getLocation(), 3, 4, p);
                } else {
                    effect(p.getLocation(), 1, 3, p);
                }
            }
        }.runTaskTimer(Devoria.getInstance(), 0L, 1L);
    }

    private static void effect(Location loc, int particleMultiplier, int damage, Player damageDealer) {
        loc.getWorld().spawnParticle(Particle.FLAME, loc, 4 * particleMultiplier, 0, 0, 0, 0.175);
        loc.getWorld().spawnParticle(Particle.SMOKE_LARGE, loc, particleMultiplier, 0, 0, 0, 0.3);
        loc.getWorld().spawnParticle(Particle.EXPLOSION_NORMAL, loc, particleMultiplier, 0, 0, 0, 0.3);
        for (Entity nearbyEntity : loc.getNearbyEntities(2, 2, 2)) {
            if (!(nearbyEntity instanceof LivingEntity) || nearbyEntity.equals(damageDealer)) continue;
            LivingEntity target = (LivingEntity) nearbyEntity;
            target.damage(damage, damageDealer);
        }
    }

    @Override
    public String toString() {
        return "EnergyBurst";
    }
}
