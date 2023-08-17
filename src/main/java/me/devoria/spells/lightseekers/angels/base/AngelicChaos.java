package me.devoria.spells.lightseekers.angels.base;

import me.devoria.Devoria;
import me.devoria.cooldowns.CooldownManager;
import me.devoria.spells.Spell;
import me.devoria.utils.FastUtils;
import me.devoria.utils.ParticleUtils;
import me.devoria.utils.PlayerUtils;
import org.bukkit.Color;
import org.bukkit.Location;
import org.bukkit.Particle;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;

public class AngelicChaos extends Spell {
    @Override
    public void cast(Player p, CooldownManager cooldownManager) {
        Arrow[] arrows = new Arrow[5];
        new BukkitRunnable() {
            int mTicks = 0;

            @Override
            public void run() {
                if (mTicks >= 5) {
                    this.cancel();
                    return;
                }

                Vector velocity = p.getLocation().getDirection().multiply(3);
                Arrow arrow = p.launchProjectile(Arrow.class, velocity);
                arrow.addScoreboardTag("nodamage");

                new BukkitRunnable() {
                    int ticks = 0;

                    @Override
                    public void run() {
                        ticks += 1;
                        if (!p.isOnline()) {
                            this.cancel();
                            arrow.remove();
                            return;
                        }
                        if (arrow.isOnGround()) {
                            effect(arrow.getLocation(), p);
                            arrow.remove();
                            this.cancel();
                            return;
                        }
                        if (arrow.isDead()) {
                            this.cancel();
                            return;
                        }
                        arrow.getWorld().spawnParticle(Particle.HEART, arrow.getLocation(), 3, 0, 0, 0, 0);
                        arrow.getWorld().spawnParticle(Particle.REDSTONE, arrow.getLocation(), 3, 0, 0, 0, 0, new Particle.DustOptions(Color.fromRGB(245, 156, 255), 2));
                        if (ticks >= 100) {
                            this.cancel();
                            arrow.remove();
                            return;
                        }
                    }
                }.runTaskTimer(Devoria.getInstance(), 0L, 1L);

                mTicks += 1;
            }
        }.runTaskTimer(Devoria.getInstance(), 0L, 10L);
    }

    public void effect(Location loc, Player damager) {

        new BukkitRunnable() {
            int mTicks = 0;

            @Override
            public void run() {
                if (mTicks == 140) this.cancel();
                if (mTicks % 7 == 0) {
                    for (Vector vector : ParticleUtils.getCirclePoints(3)) {
                        loc.getWorld().spawnParticle(Particle.END_ROD, loc.add(vector), 0, 0, 0, 0.125);
                    }
                }
                for (Entity e : loc.getNearbyEntities(3, 3, 3)) {
                    if (e instanceof Player) {
                        Player p = (Player) e;
                        double maxHealth = Integer.parseInt(FastUtils.map(p.getMetadata("attributes").get(0).asString()).get("health"));
                        PlayerUtils.changeHealth(p, (int) (maxHealth * 0.02), damager, false);
                    } else if (e instanceof LivingEntity) {
                        ((LivingEntity) e).damage(5, damager);
                    }
                }

                for (Vector vector : ParticleUtils.getCirclePoints(3)) {
                    loc.getWorld().spawnParticle(Particle.REDSTONE, loc.add(vector), 0, 0, 0, 0.125, new Particle.DustOptions(Color.fromRGB(245, 156, 255), 2));
                }
                mTicks += 5;
            }
        }.runTaskTimer(Devoria.getInstance(), 0L, 5L);
    }

    @Override
    public String toString() {
        return "AngelicChaos";
    }
}
