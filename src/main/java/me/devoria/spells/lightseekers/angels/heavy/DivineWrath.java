package me.devoria.spells.lightseekers.angels.heavy;

import java.util.Collection;
import java.util.List;
import me.devoria.cooldowns.CooldownManager;
import me.devoria.spells.Spell;
import me.devoria.utils.FastUtils;
import me.devoria.utils.ParticleUtils;
import org.bukkit.Location;
import org.bukkit.Particle;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.util.Vector;

public class DivineWrath extends Spell {
    @Override
    public void cast(Player player, CooldownManager cooldownManager) {
        Vector direction = player.getEyeLocation().getDirection().normalize();
        List<Vector> helixPoints = ParticleUtils.getHelixPoints(direction, 1, 20, 50, true);

        // Loop through each point in the helix
        for (Vector point : helixPoints) {
            Location pointLocation = player.getEyeLocation().add(point);
            // Get nearby entities
            Collection<Entity> nearbyEntities = pointLocation.getNearbyEntities(1, 1, 1);
            for (Entity entity : nearbyEntities) {
                // Check if entity is not the player and is a living entity
                if (!entity.equals(player) && entity instanceof LivingEntity) {
                    summonCircles((LivingEntity) entity);
                    return;
                }
            }

            pointLocation.getWorld().spawnParticle(Particle.TOTEM, pointLocation, 10, 0, 0, 0, 0);
        }
    }

    private void summonCircles(LivingEntity entity) {
        int circleCount = FastUtils.randomIntInRange(5, 11);
        Vector[] centers = new Vector[circleCount];

        for (int i = 0; i < circleCount; i++) {
            List<Vector> circlePoints = ParticleUtils.getCirclePoints(2);
            double x = FastUtils.randomDoubleInRange(0, 360);
            double y = FastUtils.randomDoubleInRange(0, 360);
            double z = FastUtils.randomDoubleInRange(0, 360);
            Vector offset = new Vector(0, entity.getHeight() / 2, 0);

            Vector center = new Vector().subtract(offset).rotateAroundX(x).rotateAroundY(y).rotateAroundZ(z);
            centers[i] = center;

            for (Vector point : circlePoints) {
                point.subtract(offset);
                point = point.rotateAroundX(x).rotateAroundY(y).rotateAroundZ(z);
                Location location = entity.getLocation().add(point);
                location.getWorld().spawnParticle(Particle.TOTEM, location, 10, 0, 0, 0, 0);
            }
        }

        summonLines(centers, entity);
    }

    private void summonLines(Vector[] centers, LivingEntity target) {
        for (Vector center : centers) {
            for (Vector linePoint : ParticleUtils.getLinePoints(center, new Vector(), 20)) {
                Location particleLoc = target.getLocation().clone().add(linePoint);
                particleLoc.getWorld().spawnParticle(Particle.TOTEM, particleLoc, 20, 0, 0, 0, 0);

                for (Entity entity : particleLoc.getNearbyEntities(1, 1, 1)) {
                    if (entity.equals(target)) {
                        target.damage(5);
                        return;
                    }
                }
            }
        }
    }

    @Override
    public String toString() {
        return "DivineWrath";
    }
}