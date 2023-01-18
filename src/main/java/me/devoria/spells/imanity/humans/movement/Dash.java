package me.devoria.spells.imanity.humans.movement;

import me.devoria.Devoria;
import me.devoria.cooldowns.CooldownManager;
import me.devoria.spells.Spell;
import org.bukkit.Color;
import org.bukkit.Particle;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;

public class Dash extends Spell {
    @Override
    public void cast(Player p, CooldownManager cooldownManager) {
        Vector velocity = p.getVelocity();
        Vector direction = new Vector(p.getEyeLocation().getDirection().getX(), 0, p.getEyeLocation().getDirection().getZ()).multiply(20).setY(0.4);
        p.setVelocity(direction);

        new BukkitRunnable() {
            int ticks = 0;

            @Override
            public void run() {
                ticks++;
                if (ticks >= 3) {
                    p.setVelocity(velocity);
                    cancel();
                }
                p.getWorld().spawnParticle(Particle.REDSTONE, p.getLocation().add(0, 1, 0), 10, 0.2, 0.2, 0.2, 0.1, new Particle.DustOptions(Color.AQUA, 1));
                p.getWorld().spawnParticle(Particle.SMOKE_NORMAL, p.getLocation().add(0, 1, 0), 10, 0.2, 0.2, 0.2, 0.1);
            }
        }.runTaskTimer(Devoria.getInstance(), 0L, 1L);
    }

    @Override
    public String toString() {
        return "Dash";
    }
}
