package me.devoria.spells.lightseekers.elves.util;

import me.devoria.Devoria;
import me.devoria.cooldowns.CooldownManager;
import me.devoria.spells.Spell;
import net.kyori.adventure.sound.SoundStop;
import org.bukkit.Color;
import org.bukkit.Location;
import org.bukkit.Particle;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

public class EyeofLight extends Spell {
    @Override
    public void cast(Player player, CooldownManager cooldownManager) {
        player.addScoreboardTag("homing");
        new BukkitRunnable() {
            int ticks = 0;
            double yOffset = 0;
            double radius = 3;
            @Override
            public void run() {
                Location location = player.getLocation();
                if (ticks++ >= 100 || location.getWorld() == null) {
                    player.removeScoreboardTag("homing");
                    this.cancel();
                    player.getWorld().playSound(player.getLocation(), Sound.ENTITY_BLAZE_HURT, 0.5f, 2f);
                    player.getWorld().playSound(player.getLocation(), Sound.ENTITY_ALLAY_AMBIENT_WITHOUT_ITEM, 1f, 1f);
                    player.getWorld().playSound(player.getLocation(), Sound.BLOCK_RESPAWN_ANCHOR_DEPLETE, 1f, 1.3f);
                    player.getWorld().stopSound(SoundStop.named(Sound.BLOCK_CONDUIT_AMBIENT));
                    player.getWorld().stopSound(SoundStop.named(Sound.BLOCK_AMETHYST_BLOCK_CHIME));
                    return;
                }

                if (radius <= 0) {
                    radius = 3;
                    yOffset = 0;
                }

                Location particleLoc = location.clone().add(0, yOffset, 0);
                particleLoc.setX(location.getX() + Math.cos(ticks) * radius);
                particleLoc.setZ(location.getZ() + Math.sin(ticks) * radius);
                location.getWorld().spawnParticle(Particle.REDSTONE, particleLoc, 1, new Particle.DustOptions(Color.fromRGB(255, 194, 254), 2));

                particleLoc.setX(location.getX() + Math.sin(ticks) * radius);
                particleLoc.setZ(location.getZ() + Math.cos(ticks) * radius);
                location.getWorld().spawnParticle(Particle.REDSTONE, particleLoc, 1, new Particle.DustOptions(Color.fromRGB(255, 194, 254), 2));


                radius -= 0.075;
                yOffset += 0.2;
            }
        }.runTaskTimer(Devoria.getInstance(), 0L, 1L);

        player.getWorld().playSound(player.getLocation(), Sound.BLOCK_CONDUIT_AMBIENT, 2f, 2f);
        player.getWorld().playSound(player.getLocation(), Sound.BLOCK_BEACON_ACTIVATE, 0.5f, 2f);
        player.getWorld().playSound(player.getLocation(), Sound.BLOCK_AMETHYST_BLOCK_CHIME, 2f, 0.1f);
        player.getWorld().playSound(player.getLocation(), Sound.BLOCK_BELL_RESONATE, 2f, 1.9f);
    }

    @Override
    public String toString() {
        return "EyeOfLight";
    }
}
