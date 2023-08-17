package me.devoria.spells.imanity.humans.heavy;

import me.devoria.Devoria;
import me.devoria.cooldowns.CooldownManager;
import me.devoria.spells.Spell;
import me.devoria.utils.FastUtils;
import me.devoria.utils.ParticleUtils;
import org.bukkit.Location;
import org.bukkit.Particle;
import org.bukkit.Sound;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;

public class EnergyBurst extends Spell {
    @Override
    public void cast(Player p, CooldownManager cooldownManager) {
        new BukkitRunnable() {
            int mTick = 0;

            @Override
            public void run() {
                if (mTick >= 10) this.cancel();
                slash(p);
                p.getWorld().playSound(p.getLocation(), Sound.ENTITY_PLAYER_ATTACK_SWEEP, 1f, 1f);
                mTick++;
            }
        }.runTaskTimer(Devoria.getInstance(), 0L, 1L);
    }

    private Vector randomPoint(Vector pView) {
        double distance = FastUtils.randomDoubleInRange(1, 5);
        double rotateX = FastUtils.randomDoubleInRange(-90, 90);
        double rotateY = FastUtils.randomDoubleInRange(-90, 90);
        double rotateZ = FastUtils.randomDoubleInRange(-90, 90);
        return ParticleUtils.rotateXAxis(ParticleUtils.rotateYAxis(ParticleUtils.rotateZAxis(pView.multiply(distance), rotateZ), rotateY), rotateX);
    }

    private void slash(Player p) {
        // Calculate start, control, and end points
        Vector start = randomPoint(p.getLocation().getDirection());
        Vector control = randomPoint(p.getLocation().getDirection());
        Vector end = randomPoint(p.getLocation().getDirection());

        // Spawn particles for first part of curve
        for (int i = 0; i <= 20; i++) {
            double t = (double) i / 20.0;
            Location particleLoc = p.getEyeLocation().add(ParticleUtils.getQuadraticBezierPoint(start, control, end, t));
            p.getWorld().spawnParticle(Particle.CRIT, particleLoc, 3, 0, 0, 0, 0);
            p.getWorld().spawnParticle(Particle.FLAME, particleLoc, 1, 0, 0, 0, 0);
            p.getWorld().spawnParticle(Particle.TOTEM, particleLoc, 10, 0, 0, 0, 0);
            for (Entity entity : particleLoc.getNearbyEntities(2, 2, 2)) {
                if (!(entity instanceof LivingEntity) || entity.equals(p)) continue;
                ((LivingEntity) entity).damage(5, p);
            }
        }
    }

    @Override
    public String toString() {
        return "EnergyBurst";
    }
}
