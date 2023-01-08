package me.devoria.spells.imanity.humans.base;

import me.devoria.cooldowns.CooldownManager;
import me.devoria.spells.Spell;
import org.bukkit.Color;
import org.bukkit.Location;
import org.bukkit.Particle;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;

public class HeroicStrike extends Spell {
    private static final double AOE_RADIUS = 5.0;
    private static final double DAMAGE = 5.0;
    private static final Particle.DustOptions GREEN_DUST_OPTIONS = new Particle.DustOptions(Color.GREEN, 1.0f);
    private static final Particle.DustOptions YELLOW_DUST_OPTIONS = new Particle.DustOptions(Color.YELLOW, 1.0f);

    @Override
    public void cast(Player p, CooldownManager cooldownManager) {
        // Get the location of the player
        Location playerLocation = p.getLocation();

        // Spawn green and yellow particles in a circle around the player
        for (double theta = 0; theta < 2 * Math.PI; theta += Math.PI / 16) {
            double x = AOE_RADIUS * Math.cos(theta);
            double z = AOE_RADIUS * Math.sin(theta);
            playerLocation.add(x, 0, z);
            p.getWorld().spawnParticle(Particle.REDSTONE, playerLocation, 1, GREEN_DUST_OPTIONS);
            playerLocation.subtract(x, 0, z);
        }
        for (double theta = 0; theta < 2 * Math.PI; theta += Math.PI / 16) {
            double x = AOE_RADIUS * Math.cos(theta);
            double z = AOE_RADIUS * Math.sin(theta);
            playerLocation.add(x, 0, z);
            p.getWorld().spawnParticle(Particle.REDSTONE, playerLocation, 1, YELLOW_DUST_OPTIONS);
            playerLocation.subtract(x, 0, z);
        }

        // Damage all players within the AOE radius
        for (Entity e : playerLocation.getWorld().getNearbyEntities(playerLocation, AOE_RADIUS, AOE_RADIUS, AOE_RADIUS)) {
            if (e instanceof Player) {
                Player target = (Player) e;
                if (target != p) {
                    target.damage(DAMAGE);
                }
            }
        }

    }

    @Override
    public String toString() {
        return "HeroicStrike";
    }
}
