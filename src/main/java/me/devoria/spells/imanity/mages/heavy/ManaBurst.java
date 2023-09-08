package me.devoria.spells.imanity.mages.heavy;

import me.devoria.Devoria;
import me.devoria.cooldowns.CooldownManager;
import me.devoria.spells.Spell;
import me.devoria.utils.FastUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;

import org.bukkit.Color;
import org.bukkit.Location;
import org.bukkit.Particle;
import org.bukkit.Sound;
import org.bukkit.block.Block;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.metadata.MetadataValue;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;

public class ManaBurst extends Spell {
    @Override
    public void cast(Player p, CooldownManager cooldownManager) {
   	 Location loc = p.getLocation().add(new Vector(0, 1, 0));
     int bullets = 10;
     List<Vector> randomVectors = new ArrayList<>();
     List<Location> bulletLocs = new ArrayList<>();
     List<Location> randomHoming = new ArrayList<>();

     for (int i = 0; i < bullets; i++) {
         double radius = FastUtils.randomDoubleInRange(0.1, 0.35);
    	 
    	 Double randAngle1 = FastUtils.randomDoubleInRange(0, Math.PI*2);
    	 Double randAngle2 = FastUtils.randomDoubleInRange(0, Math.PI*2);
    	 Double x = radius * Math.sin(randAngle1) * Math.cos(randAngle2);
    	 Double y = Math.abs(radius * Math.sin(randAngle1) * Math.sin(randAngle2)) + FastUtils.randomDoubleInRange(-0.2, 0);
    	 Double z = radius * Math.cos(randAngle1);
    	 
         Vector randomVector = new Vector(
             x,
             y,
             z
         );
         randomVectors.add(randomVector);
         bulletLocs.add(loc.clone());
         randomHoming.add(new Location(p.getWorld(), FastUtils.randomDoubleInRange(-2.5, 2.5), FastUtils.randomDoubleInRange(0.5, 2), FastUtils.randomDoubleInRange(-2.5, 2.5)));
     }
     
     AtomicBoolean foundArmorStand = new AtomicBoolean(false);
     
        new BukkitRunnable() {
        	int mTick = 0;

            @Override
            public void run() {
                if (p.isDead() || !p.isOnline() || !p.getLocation().isChunkLoaded() || mTick >= 30) {
                    this.cancel();
                    return;
                }
            	
                for (int i = 0; i < bullets; i++) {
                    Location bulletLocation = bulletLocs.get(i);
                    Vector randomVector = randomVectors.get(i);

                    int r = 29+mTick*5;
                    int g = 103+mTick*5;
                    int b = 197+mTick;
                    
                    Particle.DustOptions blueToCyan = new Particle.DustOptions(Color.fromRGB(r, g, b), 2f);
                    p.getWorld().spawnParticle(Particle.REDSTONE, bulletLocation, 1, 0, 0, 0, 1, blueToCyan);

                    
                    if (mTick >= 29) {
                        double radius = 1;
                        double increment = Math.PI / 10;
                        for (double theta = 0; theta <= Math.PI; theta += increment) {
                            double sinTheta = Math.sin(theta);
                            double cosTheta = Math.cos(theta);
                            for (double phi = 0; phi <= 2 * Math.PI; phi += increment) {
                                double sinPhi = Math.sin(phi);
                                double cosPhi = Math.cos(phi);
                                Vector vector = new Vector(radius * sinTheta * cosPhi, radius * sinTheta * sinPhi, radius * cosTheta);
                                p.getWorld().spawnParticle(Particle.SOUL_FIRE_FLAME, bulletLocation, 0, vector.getX(), vector.getY(), vector.getZ(), 0.1);
                            }
                        }
                    	
                    for (Entity nearbyEntity : bulletLocation.getNearbyEntities(2.5, 2.5, 2.5)) {
                        if (!(nearbyEntity instanceof LivingEntity target) || nearbyEntity.equals(p)) continue;
                        ((LivingEntity) nearbyEntity).damage(1, p);
                        }
                    
                    p.getWorld().playSound(bulletLocation, Sound.ENTITY_GENERIC_EXPLODE, 0.5f, 2f);
                    p.getWorld().playSound(bulletLocation, Sound.ENTITY_GENERIC_EXPLODE, 0.5f, 1.5f);
                    }
                    
                    	if (mTick >= 10) {
                    	 for (Entity e : bulletLocation.getNearbyEntities(16, 16, 16)) {
                             if (!(e instanceof ArmorStand)) continue;
                             if (e.hasMetadata("caster")) {
                                 List<MetadataValue> metadata = e.getMetadata("caster");
                                 if (metadata.size() > 0) {
                                     String casterUUIDString = metadata.get(0).asString();

                                     // Compare the caster's UUID with the UUID of the player who cast the spell.
                                     if (casterUUIDString.equals(p.getUniqueId().toString())) {
                                    	 Vector direction = e.getLocation().add(randomHoming.get(i)).toVector().subtract(bulletLocation.toVector()).normalize();
                                    	 Vector homingVector = direction.multiply(0.35f+(double)mTick/80);
                                      	bulletLocation.add(homingVector);
                                    	 foundArmorStand.set(true);
                                         bulletLocs.set(i, bulletLocation);
                                         
                                         Block block = bulletLocation.getBlock();
                                         if (!block.getType().isAir()) {
                                           	bulletLocation.add(homingVector.multiply(-1));
                                            bulletLocs.set(i, bulletLocation);
                                         }
                                    	 break;
                                     } else {
                                    	 foundArmorStand.set(false);
                                     }
                                 }
                             }
                    	 }
                    }
                    
                    if(!foundArmorStand.get()) {
                     	bulletLocation.add(randomVector);
                     	
                        Block block = bulletLocation.getBlock();
                        if (!block.getType().isAir()) {
                          	bulletLocation.add(randomVector.multiply(-1));
                           bulletLocs.set(i, bulletLocation);
                        }
                     }
                    bulletLocs.set(i, bulletLocation);
                }
                
                mTick++;
            }
        }.runTaskTimer(Devoria.getInstance(), 0L, 1L);

        p.getWorld().playSound(p.getLocation(), Sound.ENTITY_FIREWORK_ROCKET_LAUNCH, 0.8f, 1f);
        p.getWorld().playSound(p.getLocation(), Sound.ENTITY_FIREWORK_ROCKET_LAUNCH, 0.8f, 0.8f);
        p.getWorld().playSound(p.getLocation(), Sound.ENTITY_FIREWORK_ROCKET_TWINKLE, 0.8f, 2f);
        p.getWorld().playSound(p.getLocation(), Sound.ITEM_CROSSBOW_SHOOT, 0.8f, 1f);
    	}

    @Override
    public String toString() {
        return "ManaBurst";
    }
}
