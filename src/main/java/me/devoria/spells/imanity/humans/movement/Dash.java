package me.devoria.spells.imanity.humans.movement;

import me.devoria.Devoria;
import me.devoria.cooldowns.CooldownManager;
import me.devoria.spells.Spell;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;

public class Dash extends Spell {
    @Override
    public void cast(Player p, CooldownManager cooldownManager) {
        Vector velocity = p.getVelocity();
        Vector direction = new Vector(p.getEyeLocation().getDirection().getX(), 0, p.getEyeLocation().getDirection().getZ()).normalize().multiply(20);
        direction.setY(0.4);
        p.setVelocity(direction);
        new BukkitRunnable() {
            @Override
            public void run() {
                p.setVelocity(velocity);
            }
        }.runTaskLater(Devoria.getInstance(), 5L);
        //Particles
    }

    @Override
    public String toString() {
        return "Dash";
    }
}
