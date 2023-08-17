package me.devoria.spells.imanity.demigods.base;

import me.devoria.Devoria;
import me.devoria.cooldowns.CooldownManager;
import me.devoria.spells.Spell;
import org.bukkit.Bukkit;
import org.bukkit.Color;
import org.bukkit.Location;
import org.bukkit.Particle;
import org.bukkit.Sound;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

public class SpinSlash extends Spell {
    @Override
    public void cast(Player p, CooldownManager cooldownManager) {

        new BukkitRunnable() {
            int mTicks = 0;
            double yOffset = 0;
            double radius = 0;

            @Override
            public void run() {
                for (Entity e : p.getNearbyEntities(3, 3, 3)) {
                    if (!(e instanceof LivingEntity)) continue;
                    ((LivingEntity) e).damage(2, p);
                }
                Location location = p.getLocation();

                if (mTicks == 0) {
                    p.playSound(location, Sound.ENTITY_DROWNED_SHOOT, 2f, 0.2f);
                    p.playSound(location, Sound.ENTITY_PLAYER_ATTACK_SWEEP, 2f, 0.2f);
                    p.playSound(location, Sound.ITEM_TRIDENT_THUNDER, 0.2f, 2f);
                    p.playSound(location, Sound.ENTITY_VILLAGER_AMBIENT, 1f, 2f);
                    p.playSound(location, Sound.BLOCK_ENCHANTMENT_TABLE_USE, 1f, 0.7f);
                    p.playSound(location, Sound.ENTITY_SHULKER_BULLET_HIT, 2f, 0.7f);
                    p.playSound(location, Sound.ENTITY_BLAZE_HURT, 0.6f, 0.1f);

                    Bukkit.getScheduler().runTaskLater(Devoria.getInstance(), () -> {
                        p.playSound(location, Sound.ENTITY_DROWNED_SHOOT, 1f, 1f);
                        p.playSound(location, Sound.ENTITY_PLAYER_ATTACK_SWEEP, 1f, 1f);
                        p.playSound(location, Sound.ITEM_TRIDENT_RIPTIDE_1, 1f, 0.7f);
                    }, 1);
                }

                mTicks += 3;

                if (radius >= 3) {
                    this.cancel();
                }

                Location particleLoc = location.clone().add(0, yOffset, 0);
                particleLoc.setX(location.getX() + Math.cos(mTicks) * radius);
                particleLoc.setZ(location.getZ() + Math.sin(mTicks) * radius);
                location.getWorld().spawnParticle(Particle.SWEEP_ATTACK, particleLoc, 1);
                location.getWorld().spawnParticle(Particle.FLAME, particleLoc, 1);
                location.getWorld().spawnParticle(Particle.SOUL_FIRE_FLAME, particleLoc, 1);
                location.getWorld().spawnParticle(Particle.FALLING_OBSIDIAN_TEAR, particleLoc, 1);
                location.getWorld().spawnParticle(Particle.REDSTONE, particleLoc, 1, new Particle.DustOptions(Color.PURPLE, 2));

                particleLoc.setX(location.getX() + Math.sin(mTicks) * radius);
                particleLoc.setZ(location.getZ() + Math.cos(mTicks) * radius);
                location.getWorld().spawnParticle(Particle.SWEEP_ATTACK, particleLoc, 1);


                radius += 0.2;
                yOffset += 0.15;
            }
        }.runTaskTimer(Devoria.getInstance(), 0L, 1L);
    }

    @Override
    public String toString() {
        return "SpinSlash";
    }
}
