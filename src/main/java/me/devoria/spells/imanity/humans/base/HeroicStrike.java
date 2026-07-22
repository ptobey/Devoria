package me.devoria.spells.imanity.humans.base;

import me.devoria.cooldowns.CooldownManager;
import me.devoria.spells.CastTargetLedger;
import me.devoria.spells.Spell;
import me.devoria.utils.ParticleUtils;
import org.bukkit.Location;
import org.bukkit.Particle;
import org.bukkit.Sound;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.util.Vector;

public class HeroicStrike extends Spell {

    @Override
    public void cast(Player p, CooldownManager cooldownManager) {
        CastTargetLedger targets = new CastTargetLedger();
        slash(p, 5, targets);
        slash(p, 4, targets);
        slash(p, 3, targets);

        p.getWorld().playSound(p.getLocation(), Sound.BLOCK_ENDER_CHEST_OPEN, 1.0f, 1.0f);
        p.getWorld().playSound(p.getLocation(), Sound.ENTITY_DROWNED_SHOOT, 1.2f, 0.1f);
        p.getWorld().playSound(p.getLocation(), Sound.ENTITY_PLAYER_ATTACK_SWEEP, 1.5f, 0.1f);
        p.getWorld().playSound(p.getLocation(), Sound.ENTITY_FIREWORK_ROCKET_BLAST, 1.2f, 0.1f);
        p.getWorld().playSound(p.getLocation(), Sound.BLOCK_ENCHANTMENT_TABLE_USE, 1f, 2f);
    }

    @Override
    public String toString() {
        return "HeroicStrike";
    }

    private void slash(Player p, int controlMult, CastTargetLedger targets) {
        // Calculate start, control, and end points
        Vector start = ParticleUtils.rotateYAxis(ParticleUtils.rotateXAxis(p.getEyeLocation().getDirection().multiply(5), -45), 90);
        Vector control = p.getEyeLocation().getDirection().multiply(controlMult);
        Vector end = ParticleUtils.rotateYAxis(ParticleUtils.rotateXAxis(p.getEyeLocation().getDirection().multiply(5), 45), -90);

        // Spawn particles for first part of curve
        for (int i = 0; i <= 20; i++) {
            double t = (double) i / 20.0;
            Location particleLoc = p.getEyeLocation().add(ParticleUtils.getQuadraticBezierPoint(start, control, end, t));
            p.getWorld().spawnParticle(Particle.CRIT, particleLoc, 3, 0, 0, 0, 0);
            p.getWorld().spawnParticle(Particle.TOTEM, particleLoc, 10, 0, 0, 0, 0);
            for (Entity entity : particleLoc.getNearbyEntities(2, 2, 2)) {
                if (!(entity instanceof LivingEntity target) || entity.equals(p)
                        || !targets.claim(entity.getUniqueId())) {
                    continue;
                }
                target.damage(5, p);
            }
        }

        // Calculate new start and end points for second part of curve
        start = ParticleUtils.rotateYAxis(ParticleUtils.rotateXAxis(p.getEyeLocation().getDirection().multiply(5), 45), 90);
        end = ParticleUtils.rotateYAxis(ParticleUtils.rotateXAxis(p.getEyeLocation().getDirection().multiply(5), -45), -90);

        // Spawn particles for second part of curve
        for (int i = 0; i <= 20; i++) {
            double t = (double) i / 20.0;
            Location particleLoc = p.getEyeLocation().add(ParticleUtils.getQuadraticBezierPoint(start, control, end, t));
            p.getWorld().spawnParticle(Particle.CRIT, particleLoc, 3, 0, 0, 0, 0);
            p.getWorld().spawnParticle(Particle.TOTEM, particleLoc, 10, 0, 0, 0, 0);
            for (Entity entity : particleLoc.getNearbyEntities(2, 2, 2)) {
                if (!(entity instanceof LivingEntity target) || entity.equals(p)
                        || !targets.claim(entity.getUniqueId())) {
                    continue;
                }
                target.damage(5, p);
            }
        }
    }
}
