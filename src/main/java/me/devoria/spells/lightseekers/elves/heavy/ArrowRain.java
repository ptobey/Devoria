package me.devoria.spells.lightseekers.elves.heavy;

import java.util.ArrayList;
import java.util.List;
import me.devoria.Devoria;
import me.devoria.cooldowns.CooldownManager;
import me.devoria.spells.Spell;
import me.devoria.utils.ParticleUtils;
import org.bukkit.Color;
import org.bukkit.Location;
import org.bukkit.Particle;
import org.bukkit.Sound;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;

public class ArrowRain extends Spell {
    @Override
    public void cast(Player player, CooldownManager cooldownManager) {
        Location location = player.getEyeLocation().add(0, 10, 0);
        new BukkitRunnable() {
            int ticks = 0;

            @Override
            public void run() {
                List<Arrow> arrows = new ArrayList<>();
                for (Vector vector : ParticleUtils.getFilledCirclePoints(10, 20)) {
                    player.getWorld().spawnParticle(Particle.CLOUD, location.add(vector), 1);
                    arrows.add(player.getWorld().spawnArrow(location.add(vector), new Vector(0, -1, 0), 1, 1));
                }

                for (Arrow arrow : arrows) {
                    arrow.addScoreboardTag(player.getUniqueId().toString());
                    arrow.addScoreboardTag("arrowRainArrow");
                    new BukkitRunnable() {
                        public void run() {
                            arrow.getWorld().spawnParticle(Particle.REDSTONE, arrow.getLocation(), 1, new Particle.DustOptions(Color.AQUA, 1));
                            if (arrow.isDead() || arrow.isOnGround()) {
                                for (Entity entity : arrow.getNearbyEntities(2, 2, 2)) {
                                    if (!(entity instanceof LivingEntity) || entity.equals(player)) continue;
                                    LivingEntity livingEntity = (LivingEntity) entity;
                                    livingEntity.damage(5, player);
                                }
                                arrow.remove();
                                this.cancel();
                            }
                        }
                    }.runTaskTimer(Devoria.getInstance(), 0L, 1L);
                }
                ticks += 1;
                if (ticks >= 60) this.cancel();
            }
        }.runTaskTimer(Devoria.getInstance(), 0L, 1L);

        player.getWorld().playSound(player.getLocation(), Sound.ENTITY_FIREWORK_ROCKET_LAUNCH, 1.5f, 1.2f);
        player.getWorld().playSound(player.getLocation(), Sound.ENTITY_FIREWORK_ROCKET_LAUNCH, 1.5f, 0.8f);
        player.getWorld().playSound(player.getLocation(), Sound.ENTITY_ALLAY_AMBIENT_WITHOUT_ITEM, 1f, 2f);
        player.getWorld().playSound(player.getLocation(), Sound.ITEM_TRIDENT_RIPTIDE_3, 0.4f, 1.1f);
    }

    @Override
    public String toString() {
        return "ArrowRain";
    }
}
