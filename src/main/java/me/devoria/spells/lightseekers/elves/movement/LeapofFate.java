package me.devoria.spells.lightseekers.elves.movement;

import me.devoria.Devoria;
import me.devoria.cooldowns.CooldownManager;
import me.devoria.spells.Spell;
import me.devoria.utils.BlockUtils;
import org.bukkit.Color;
import org.bukkit.Particle;
import org.bukkit.Sound;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;

public class LeapofFate extends Spell {
    @Override
    public void cast(Player player, CooldownManager cooldownManager) {
        player.getWorld().spawnParticle(Particle.FIREWORKS_SPARK, player.getLocation(), 20);
        double radius = 10;
        player.setVelocity(new Vector(0D, 2, 0D));

        for (Entity nearbyEntity : player.getNearbyEntities(radius, radius, radius)) {
            if (!(nearbyEntity instanceof LivingEntity) || nearbyEntity instanceof Player) continue;
            ((LivingEntity) nearbyEntity).addPotionEffect(new PotionEffect(PotionEffectType.GLOWING, 100, 1));
        }
        new BukkitRunnable() {
            @Override
            public void run() {
                player.getWorld().spawnParticle(Particle.REDSTONE, player.getLocation(), 1, new Particle.DustOptions(Color.WHITE, 2));
                player.getWorld().spawnParticle(Particle.VILLAGER_HAPPY, player.getLocation(), 10);

                if (player.isOnGround() || BlockUtils.isWaterSource(player.getLocation().getBlock().getState())) {
                    this.cancel();
                }
                player.addPotionEffect(new PotionEffect(PotionEffectType.SLOW_FALLING, 10, 1));
            }
        }.runTaskTimer(Devoria.getInstance(), 20L, 5L);

        player.getWorld().playSound(player.getLocation(), Sound.ITEM_TRIDENT_RIPTIDE_3, 0.1f, 1.6f);
        player.getWorld().playSound(player.getLocation(), Sound.ENTITY_HORSE_JUMP, 0.6f, 1f);
        player.getWorld().playSound(player.getLocation(), Sound.BLOCK_CONDUIT_AMBIENT_SHORT, 2f, 2f);
    }

    @Override
    public String toString() {
        return "LeapOfFate";
    }
}