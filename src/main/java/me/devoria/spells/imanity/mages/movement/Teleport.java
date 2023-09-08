package me.devoria.spells.imanity.mages.movement;

import me.devoria.Devoria;
import me.devoria.cooldowns.CooldownManager;
import me.devoria.spells.Spell;
import me.devoria.utils.CastingUtils;
import me.devoria.utils.FastUtils;

import org.bukkit.Color;
import org.bukkit.Location;
import org.bukkit.Particle;
import org.bukkit.Sound;
import org.bukkit.block.Block;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;

public class Teleport extends Spell {	
    @Override
    public void cast(Player p, CooldownManager cooldownManager) {
    	if (CastingUtils.contains("levitation", p)) {
    		return;
    	}
        CastingUtils.add("levitation", p);
    	
        Location loc = p.getLocation();
        Vector direction = loc.getDirection();
        Vector addVelocity = new Vector(direction.getX()*0.75, 0.6f, direction.getZ()*0.75);
        p.setVelocity(addVelocity);
        
        for (int i = 0; i <= 20; i++) {
            
        	Double angle = (i*Math.PI*2)/20;
        	Double xInc = Math.cos(angle)*0.5;
            Double zInc = Math.sin(angle)*0.5;
            p.getWorld().spawnParticle(Particle.END_ROD, p.getLocation(), 0, xInc, 0f, zInc, 0.25);
        
    	}

        new BukkitRunnable() {
            int mTick = 0;

            @Override
            public void run() {
                if (p.isDead() || !p.isOnline() || !p.getLocation().isChunkLoaded() || (mTick>=7 && p.isOnGround())) {
                	CastingUtils.remove("levitation", p);
                    this.cancel();
                    return;
                }               

                if (mTick >= 7 ) {
                	Particle.DustTransition dustTransition = new Particle.DustTransition(Color.fromRGB(33, 135, 196), Color.fromRGB(199, 222, 235), 1.25F);
                	
                	for (int i = 1; i <= 6; i++) {
                	Location randomLoc = new Location(p.getWorld(), FastUtils.randomFloatInRange(-0.75f,0.75f) + p.getLocation().getX(), FastUtils.randomFloatInRange(-0.1f,2.1f) + p.getLocation().getY(), FastUtils.randomFloatInRange(-0.75f,0.75f) + p.getLocation().getZ());
                	
                    p.getWorld().spawnParticle(Particle.DUST_COLOR_TRANSITION, randomLoc, 1, dustTransition);
                	}
                	
                	if (p.isSneaking()) {
                        Vector descend = new Vector(p.getVelocity().getX(),  -0.35f, p.getVelocity().getZ());
                        p.setVelocity(descend);
                	} else {
                        Vector direction2 = p.getLocation().getDirection();
                        Vector addVelocity = new Vector(direction2.clone().getX()*0.6, -0.07f, direction2.clone().getZ()*0.6);
                        p.setVelocity(addVelocity);
                	}
                	
                    for (Entity nearbyEntity : p.getLocation().getNearbyEntities(3, 3, 3)) {
                        if (!(nearbyEntity instanceof LivingEntity target) || nearbyEntity.equals(p)) continue;
                        target.addPotionEffect(new PotionEffect(PotionEffectType.LEVITATION, 50, 1));
                    }
                }
                mTick++;
            }
        }.runTaskTimer(Devoria.getInstance(), 0L, 1L);

        p.getWorld().playSound(p.getLocation(), Sound.ENTITY_FIREWORK_ROCKET_LAUNCH, 1f, 1.1f);
    	}

    @Override
    public String toString() {
        return "Teleport";
    }
}
