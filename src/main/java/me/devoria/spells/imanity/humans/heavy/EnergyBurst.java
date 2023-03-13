package me.devoria.spells.imanity.humans.heavy;

import me.devoria.Devoria;
import me.devoria.cooldowns.CooldownManager;
import me.devoria.spells.Spell;
import org.bukkit.Location;
import org.bukkit.Particle;
import org.bukkit.Sound;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

public class EnergyBurst extends Spell {
    @Override
    public void cast(Player p, CooldownManager cooldownManager) {
        final ArmorStand armorStand = p.getWorld().spawn(p.getLocation(), ArmorStand.class);
        armorStand.setMarker(true);
        armorStand.setVisible(false);
        p.getWorld().playSound(p.getLocation(), Sound.BLOCK_BELL_RESONATE, 2f, 2f);
        p.getWorld().playSound(p.getLocation(), Sound.BLOCK_BEACON_ACTIVATE, 0.2f, 2f);
        new BukkitRunnable() {
            int time = 0;

            @Override
            public void run() {
                time++;
                if (time > 40) {
                    this.cancel();
                    armorStand.remove();
                }
                if (time == 17) {
                    p.getWorld().playSound(p.getLocation(), Sound.ITEM_TRIDENT_RIPTIDE_3, 0.6f, 1.2f);
                    p.getWorld().playSound(p.getLocation(), Sound.ENTITY_SHULKER_SHOOT, 0.6f, 1.2f);
                    p.getWorld().playSound(p.getLocation(), Sound.ENTITY_SHULKER_BULLET_HIT, 0.8f, 0.5f);
                    p.getWorld().playSound(p.getLocation(), Sound.ENTITY_PLAYER_BREATH, 2f, 2f);
                    p.getWorld().playSound(p.getLocation(), Sound.ENTITY_ENDER_DRAGON_SHOOT, 0.7f, 2f);
                    p.getWorld().playSound(p.getLocation(), Sound.ENTITY_FIREWORK_ROCKET_LAUNCH, 2f, 1.4f);
                } else {
                    Location newLoc = armorStand.getLocation().setDirection(p.getLocation().getDirection());
                    armorStand.teleport(newLoc);
                    armorStand.teleport(armorStand.getLocation().add(armorStand.getLocation().getDirection()));
                    if (time > 17) {
                        armorStand.getWorld().spawnParticle(Particle.FLAME, armorStand.getLocation(), 20, 0, 0, 0, 0.175);
                        armorStand.getWorld().spawnParticle(Particle.SMOKE_LARGE, armorStand.getLocation(), 5, 0, 0, 0, 0.3);
                        armorStand.getWorld().spawnParticle(Particle.EXPLOSION_NORMAL, armorStand.getLocation(), 5, 0, 0, 0, 0.3);
                        for (Entity nearbyEntity : armorStand.getLocation().getNearbyEntities(2, 2, 2)) {
                            if (!(nearbyEntity instanceof LivingEntity) || nearbyEntity.equals(p)) continue;
                            LivingEntity target = (LivingEntity) nearbyEntity;
                            target.damage(5, p);
                        }
                    } else {
                        armorStand.getWorld().spawnParticle(Particle.FLAME, armorStand.getLocation(), 4, 0, 0, 0, 0.175);
                        armorStand.getWorld().spawnParticle(Particle.SMOKE_LARGE, armorStand.getLocation(), 1, 0, 0, 0, 0.3);
                        armorStand.getWorld().spawnParticle(Particle.EXPLOSION_NORMAL, armorStand.getLocation(), 1, 0, 0, 0, 0.3);
                        for (Entity nearbyEntity : armorStand.getLocation().getNearbyEntities(2, 2, 2)) {
                            if (!(nearbyEntity instanceof LivingEntity) || nearbyEntity.equals(p)) continue;
                            LivingEntity target = (LivingEntity) nearbyEntity;
                            target.damage(3, p);
                        }
                    }
                }
            }
        }.runTaskTimer(Devoria.getInstance(), 0L, 1L);
    }

    @Override
    public String toString() {
        return "EnergyBurst";
    }
}
