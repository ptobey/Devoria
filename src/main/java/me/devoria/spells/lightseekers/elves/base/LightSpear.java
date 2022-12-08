package me.devoria.spells.lightseekers.elves.base;

import java.util.Collection;
import java.util.concurrent.ThreadLocalRandom;
import me.devoria.cooldowns.CooldownManager;
import me.devoria.spells.Spell;
import org.bukkit.Color;
import org.bukkit.Location;
import org.bukkit.Particle;
import org.bukkit.Sound;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.util.BoundingBox;
import org.bukkit.util.Vector;

public class LightSpear extends Spell {
    @Override
    public void cast(Player player, CooldownManager cooldownManager) {
        double range = 3;
        double distance = 30;
        double collision = 1;
        double damage = 5;
        double kb = ThreadLocalRandom.current().nextDouble(0.3, 0.5);
        Location viewPos = player.getEyeLocation();
        Vector viewDir = viewPos.getDirection();
        for (double t = 0; t < distance; t += 0.5) {
            double x = viewDir.getX() * t;
            double y = viewDir.getY() * t;
            double z = viewDir.getZ() * t;
            viewPos.add(x, y, z);
            player.getWorld().spawnParticle(Particle.FIREWORKS_SPARK, viewPos, 1, 0, 0, 0);
            player.getWorld().spawnParticle(Particle.REDSTONE, viewPos, 1, 0, 0, 0, new Particle.DustOptions(Color.fromRGB(252, 88, 241), 2));
            player.getWorld().spawnParticle(Particle.REDSTONE, viewPos, 1, 0, 0, 0, new Particle.DustOptions(Color.fromRGB(132, 3, 188), 2));
            player.getWorld().spawnParticle(Particle.END_ROD, viewPos, 1, 0, 0, 0);
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
                Vector viewNormalized = (viewDir.normalize()).multiply(kb);
                livingMonster.setVelocity(viewNormalized);
            }
            viewPos.subtract(x, y, z);
        }

        player.getWorld().playSound(player.getLocation(), Sound.ITEM_TRIDENT_RETURN, 1.5f, 1.2f);
        player.getWorld().playSound(player.getLocation(), Sound.BLOCK_RESPAWN_ANCHOR_DEPLETE, 1.5f, 2f);
        player.getWorld().playSound(player.getLocation(), Sound.BLOCK_RESPAWN_ANCHOR_CHARGE, 0.2f, 1f);
        player.getWorld().playSound(player.getLocation(), Sound.BLOCK_AMETHYST_CLUSTER_BREAK, 2f, 2f);
    }
}
