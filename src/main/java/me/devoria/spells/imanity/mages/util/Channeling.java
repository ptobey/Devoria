package me.devoria.spells.imanity.mages.util;

import me.devoria.Devoria;
import me.devoria.cooldowns.CooldownManager;
import me.devoria.spells.Spell;
import me.devoria.utils.CastingUtils;
import me.devoria.utils.FastUtils;
import net.md_5.bungee.api.ChatColor;

import org.bukkit.entity.ArmorStand;
import org.bukkit.Color;
import org.bukkit.Location;
import org.bukkit.Particle;
import org.bukkit.Sound;
import org.bukkit.block.Block;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.metadata.FixedMetadataValue;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;

public class Channeling extends Spell {
    @Override
    public void cast(Player p, CooldownManager cooldownManager) {
    	
    	if (CastingUtils.contains("channeling", p)) {
    		return;
    	}
    	
    	CastingUtils.add("channeling", p);
    	 Location loc = p.getLocation();
    	 Location blockLoc = loc.clone().subtract(0, 1, 0);
    	 boolean allBlocksAreAir = true;
    	 
    	 for (int i = 0; i < 6; i++) {
             Block block = blockLoc.getBlock();

             if (!block.getType().isAir()) {
            	 loc = blockLoc.add(0, 1, 0);
                 allBlocksAreAir = false;
                 break;
             }
             blockLoc.subtract(0, 1, 0);
         }
    	 
         final Location finalLoc = loc;
    	 
         if (allBlocksAreAir) {
             p.sendMessage(ChatColor.RED + "You're too high up to cast this spell!");
         	CastingUtils.remove("channeling", p);
             return;
         }
    	 
         double radius = 4.5;
         Particle.DustTransition blueToDarkBlue = new Particle.DustTransition(Color.fromRGB(33, 135, 196), Color.fromRGB(25, 53, 104), 1.0F);
         
         double angle1 = 0;
         double angle2 = (2 * Math.PI) / 3; // 120 degrees
         double angle3 = (4 * Math.PI) / 3; // 240 degrees
         
         Location vertex1 = loc.clone().add(radius, 0, 0);
         Location vertex2 = loc.clone().add(radius * Math.cos(angle2), 0, radius * Math.sin(angle2));
         Location vertex3 = loc.clone().add(radius * Math.cos(angle3), 0, radius * Math.sin(angle3));
         
         double angle4 = angle1 + Math.PI;
         double angle5 = angle2 + Math.PI;
         double angle6 = angle3 + Math.PI; 
         
         Location vertex4 = loc.clone().add(radius * Math.cos(angle4), 0, radius * Math.sin(angle4));
         Location vertex5 = loc.clone().add(radius * Math.cos(angle5), 0, radius * Math.sin(angle5));
         Location vertex6 = loc.clone().add(radius * Math.cos(angle6), 0, radius * Math.sin(angle6));
         
         ArmorStand armorStand = p.getWorld().spawn(finalLoc, ArmorStand.class);

         armorStand.setInvisible(true);
         armorStand.setGravity(false);
         armorStand.setGlowing(true);
         armorStand.setMarker(true);

         armorStand.setMetadata("caster", new FixedMetadataValue(Devoria.getInstance(), p.getUniqueId().toString()));

         new BukkitRunnable() {
             int mTick = 0;
             int rInc1 = 33;
             int gInc1 = 135;
             int rInc2 = 33;
             int gInc2 = 135;
             double inc1 = 0;
             double inc2 = 0;

             @Override
             public void run() {
                 if (p.isDead() || !p.isOnline() || !p.getLocation().isChunkLoaded() || mTick >= 300) {
                     armorStand.remove();
                 	CastingUtils.remove("channeling", p);
                     this.cancel();
                     return;
                 }
                 if (mTick >= 20 && mTick  % 20 == 0) {
                	 for (Entity e : finalLoc.getNearbyEntities(4.5, 4.5, 4.5)) {
                         if (!(e instanceof LivingEntity target) || e.equals(p)) continue;
                         ((LivingEntity) e).damage(1, p);
                         new BukkitRunnable() {
                        	 @Override
                        	 public void run() {
                                 ((LivingEntity) e).setVelocity(new Vector(0, 0, 0));
                                 this.cancel();
                                 return;
                        	 }
                         }.runTaskTimer(Devoria.getInstance(), 0L, 1L);
                     }
                 }
                 
                 if (mTick % 50 == 0) {
                     p.getWorld().playSound(p.getLocation(), Sound.BLOCK_PORTAL_AMBIENT, 0.8f, 0.6f);
                 }
                 for (int i = 0; i <= 60; i++) {
                     
                  	Double angle = (i*Math.PI*2)/60;
                  	Double x = Math.cos(angle)*radius;
                      Double z = Math.sin(angle)*radius;
                      Location points = new Location(p.getWorld(),finalLoc.getX() + x + FastUtils.randomDoubleInRange(-0.05, 0.05) , finalLoc.getY(),finalLoc.getZ() + z + FastUtils.randomDoubleInRange(-0.05, 0.05));
                      
                      p.getWorld().spawnParticle(Particle.DUST_COLOR_TRANSITION, points, 1, blueToDarkBlue);
                      
                      if (mTick <= 8) {
                    	  Double decreasingY = 1 - mTick * 0.12;
                          p.getWorld().spawnParticle(Particle.FIREWORKS_SPARK, finalLoc.getX() + x + FastUtils.randomDoubleInRange(-0.05, 0.05), finalLoc.getY(), finalLoc.getZ() + z + FastUtils.randomDoubleInRange(-0.05, 0.05), 0, 0, decreasingY, 0, FastUtils.randomDoubleInRange(0.45, 0.8));
                      }
             }
                 for (int i = 0; i<=30; i++) {
                   	Double angle = (i*Math.PI*2)/30;
                   	Double x = Math.cos(angle)*radius;
                       Double z = Math.sin(angle)*radius;
                     Location points = new Location(p.getWorld(),finalLoc.getX() + x + FastUtils.randomDoubleInRange(-0.05, 0.05) , finalLoc.getY(),finalLoc.getZ() + z + FastUtils.randomDoubleInRange(-0.05, 0.05));
                	 DustFlyingUpwards(p, points, inc1, rInc1, gInc1);
                     if (mTick >= 15) {
                     DustFlyingUpwards(p, points, inc2, rInc2, gInc2);
                     }
                 }
                      drawTriangle(p, vertex1, vertex2, blueToDarkBlue);
                      drawTriangle(p, vertex2, vertex3, blueToDarkBlue);
                      drawTriangle(p, vertex3, vertex1, blueToDarkBlue);
                      drawTriangle(p, vertex4, vertex5, blueToDarkBlue);
                      drawTriangle(p, vertex5, vertex6, blueToDarkBlue);
                      drawTriangle(p, vertex6, vertex4, blueToDarkBlue);
                 
                 if (inc1 >= 3) inc1=0;
                 if (rInc1 >= 152) rInc1=33;
                 if (gInc1 <= 45) gInc1=135;
                 inc1+=0.1;
                 rInc1 += 4;
                 gInc1 -=3;
                 if (mTick >= 15) {
                	 if (inc2 >= 3) inc2=0;
                     if (rInc2 >= 152) rInc2=33;
                     if (gInc2 <= 45) gInc2=135;
                	 
                	 inc2+=0.1;
                     rInc2 += 4;
                     gInc2 -=3;
                 }
                 
                 mTick++;
             }
         }.runTaskTimer(Devoria.getInstance(), 0L, 1L);

         p.getWorld().playSound(p.getLocation(), Sound.BLOCK_END_PORTAL_SPAWN, 0.6f, 1f);
     	}

    @Override
    public String toString() {
        return "Channeling";
    }
    
    private void drawTriangle(Player player, Location start, Location end, Particle.DustTransition blueToDarkBlue) {
        double length = start.distance(end);
        int numParticles = (int) (length * 3); // Adjust this multiplier as needed for particle density.
        Vector direction = end.toVector().subtract(start.toVector()).normalize();

        for (int i = 0; i < numParticles; i++) {
            Location particleLocation = start.clone().add(direction.clone().multiply(length * i / numParticles));
            player.getWorld().spawnParticle(Particle.DUST_COLOR_TRANSITION, particleLocation, 1, blueToDarkBlue);
        }
    }
    
    private void DustFlyingUpwards(Player player, Location points, double inc, int r, int g) {
        Location yInc = new Location(player.getWorld(), points.x(), points.y() + inc, points.z());
        Particle.DustOptions blueToPurple = new Particle.DustOptions(Color.fromRGB(r, g, 196), 1.0F);
        player.getWorld().spawnParticle(Particle.REDSTONE, yInc, 1, blueToPurple);
        
    }
}
