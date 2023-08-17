package me.devoria.spells.imanity.humans.util;

import me.devoria.Devoria;
import me.devoria.cooldowns.CooldownManager;
import me.devoria.spells.Spell;
import me.devoria.utils.FastUtils;
import org.bukkit.Location;
import org.bukkit.Particle;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;

public class AdventurersAura extends Spell {
    @Override
    public void cast(Player p, CooldownManager cooldownManager) {
        p.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, 200, 2));
        p.addPotionEffect(new PotionEffect(PotionEffectType.INCREASE_DAMAGE, 200, 1));
        new BukkitRunnable() {
            int mTicks = 0;

            @Override
            public void run() {
                if (mTicks == 40) this.cancel();
                int minimum = -6; // Easier changing the minimum
                int maximum = 6; // Easier changing the maximum

                for (int i = 0; i < 10; i++) {
                    Location loc = p.getLocation();
                    double x = FastUtils.randomDoubleInRange(minimum, maximum);
                    double y = FastUtils.randomDoubleInRange(minimum, maximum);
                    double z = FastUtils.randomDoubleInRange(minimum, maximum);
                    Location l = new Location(loc.getWorld(), x, y, z);
                    Location locclone = loc.clone();
                    locclone.add(l);
                    Vector dir = loc.toVector().subtract(locclone.toVector()).normalize();
                    double dx = dir.getX();
                    double dy = dir.getY();
                    double dz = dir.getZ();
                    loc.getWorld().spawnParticle(Particle.CLOUD, locclone, 0, (float) dx, (float) dy, (float) dz, 0.5);
                    loc.getWorld().spawnParticle(Particle.VILLAGER_HAPPY, locclone, 0, (float) dx, (float) dy, (float) dz, 0.5);
                    loc.getWorld().spawnParticle(Particle.SOUL_FIRE_FLAME, locclone, 0, (float) dx, (float) dy, (float) dz, 0.5);
                }
                mTicks++;
            }
        }.runTaskTimer(Devoria.getInstance(), 0L, 2L);
        p.getWorld().playSound(p.getLocation(), Sound.ENTITY_ENDER_DRAGON_GROWL, 0.2f, 0.8f);
        p.getWorld().playSound(p.getLocation(), Sound.ENTITY_ALLAY_HURT, 0.7f, 0.4f);
        p.getWorld().playSound(p.getLocation(), Sound.ENTITY_ALLAY_AMBIENT_WITH_ITEM, 1.2f, 0.4f);
        p.getWorld().playSound(p.getLocation(), Sound.ENTITY_ALLAY_AMBIENT_WITHOUT_ITEM, 0.7f, 0.4f);
        p.getWorld().playSound(p.getLocation(), Sound.BLOCK_BELL_USE, 1.2f, 0.4f);
    }

    @Override
    public String toString() {
        return "AdventurersAura";
    }
}
