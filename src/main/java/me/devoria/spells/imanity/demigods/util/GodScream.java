package me.devoria.spells.imanity.demigods.util;

import java.util.Collection;
import java.util.concurrent.ThreadLocalRandom;
import me.devoria.cooldowns.CooldownManager;
import me.devoria.spells.Spell;
import me.devoria.utils.ParticleUtils;
import org.bukkit.Color;
import org.bukkit.Location;
import org.bukkit.Particle;
import org.bukkit.Sound;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.util.BoundingBox;
import org.bukkit.util.Vector;

public class GodScream extends Spell {
    @Override
    public void cast(Player player, CooldownManager cooldownManager) {
        double range = 3;
        double distance = 30;
        double collision = 1;
        double damage = 5;
        double kb = ThreadLocalRandom.current().nextDouble(0.3, 0.5);
        Location viewPos = player.getEyeLocation();
        Vector viewDir = viewPos.getDirection();
        for (double t = 0; t < distance; t += 1) {
            double x = viewDir.getX() * t;
            double y = viewDir.getY() * t;
            double z = viewDir.getZ() * t;
            viewPos.add(x, y, z);
            player.getWorld().spawnParticle(Particle.SONIC_BOOM, viewPos, 1, 0, 0, 0);
            Collection<Entity> closebyMonsters = player.getWorld().getNearbyEntities(viewPos, range, range, range);
            for (Entity closebyMonster : closebyMonsters) {
                // make sure it's a living entity, not an armor stand or something, continue skips the current loop
                if (!(closebyMonster instanceof LivingEntity) || (closebyMonster == player)) continue;
                LivingEntity livingMonster = (LivingEntity) closebyMonster;
                // Get the entity's collision box
                BoundingBox monsterBoundingBox = livingMonster.getBoundingBox();
                BoundingBox collisionBox = BoundingBox.of(viewPos, collision, collision, collision);
                if (!(monsterBoundingBox.overlaps(collisionBox))) continue;
                livingMonster.damage(damage, player);
                livingMonster.addPotionEffect(new PotionEffect(PotionEffectType.SLOW, 60, 2));
                livingMonster.addPotionEffect(new PotionEffect(PotionEffectType.WEAKNESS, 60, 2));
                Vector viewNormalized = (viewDir.normalize()).multiply(kb);
                livingMonster.setVelocity(viewNormalized);
            }
            viewPos.subtract(x, y, z);
        }
        for (Vector vector : ParticleUtils.getSpiralPoints(1, 0.05)) {
            player.getWorld().spawnParticle(Particle.REDSTONE, player.getLocation().add(vector), 1, 0, 0, 0, new Particle.DustOptions(Color.fromRGB(99, 111, 224), 2));
            player.getWorld().spawnParticle(Particle.REDSTONE, player.getLocation().add(vector), 1, 0, 0, 0, new Particle.DustOptions(Color.fromRGB(230, 90, 97), 2));
        }
        player.getWorld().playSound(player.getLocation(), Sound.ITEM_TRIDENT_RIPTIDE_1, 2, 0.1f);
        player.getWorld().playSound(player.getLocation(), Sound.ENTITY_FIREWORK_ROCKET_LAUNCH, 0.6f, 0.7f);
        player.getWorld().playSound(player.getLocation(), Sound.BLOCK_BEACON_DEACTIVATE, 1.1f, 1.2f);
    }

    @Override
    public String toString() {
        return "GodScream";
    }
}
