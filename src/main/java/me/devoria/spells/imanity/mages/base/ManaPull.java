package me.devoria.spells.imanity.mages.base;

import me.devoria.Devoria;
import me.devoria.cooldowns.CooldownManager;
import me.devoria.spells.Spell;
import me.devoria.utils.FastUtils;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Color;
import org.bukkit.Location;
import org.bukkit.Particle;
import org.bukkit.Sound;
import org.bukkit.block.Block;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;

public class ManaPull extends Spell {
    @Override
    public void cast(Player p, CooldownManager cooldownManager) {
    	
        Location loc = p.getLocation().add(0, 1.8, 0);
        Vector direction = loc.getDirection();
        List<Location> movements = new ArrayList<>();
        List<Boolean> onGrounds = new ArrayList<>();
        
        for (int i = 0; i < 3; i++) {
            movements.add(loc.clone());
            onGrounds.add(false);
        }
        
 	   float yaw = (float)Math.toRadians(loc.getYaw()+90);
	    float pitch = (float)Math.toRadians(loc.getPitch());
        
        new BukkitRunnable() {
            int mTick = 0;
            int r = 33;
            int g = 135;
            int b = 196;
            Vector pos;

            @Override
            public void run() {
                if (p.isDead() || !p.isOnline() || !p.getLocation().isChunkLoaded() || mTick >= 60) {
                    this.cancel();
                    return;
                }
                for (int a = 0; a < 3; a++) {
                    Location movement = movements.get(a);
                    boolean onGround = onGrounds.get(a);
                    
                    if (onGround == true) continue;
                    
                	double ang = Math.toRadians(loc.getYaw());
                	double nextX = Math.cos(ang)*0.35;
                	double nextZ = Math.sin(ang)*0.35;
                	double initialAngle2 = 0;
                	if (a==1) {
                		initialAngle2 = Math.toRadians(10);
                	pos = new Vector(nextX, 0, nextZ);
                	} else if (a==2) {
                		initialAngle2 = Math.toRadians(-10);
                    pos = new Vector(nextX, 0, nextZ).multiply(-1);
                	} else {
                		initialAngle2 = Math.toRadians(0);
                        pos = new Vector(0, 0, 0);
                	}
                	
            	    Block block = movement.clone().add(direction.clone().multiply(1.5+4*0.12)).getBlock();
                	
            	    if (!block.getType().isAir()) {
            	    	onGround = true;
            	    	onGrounds.set(a, onGround);
            	    }
            	    
                for (int j = 0; j <= 4; j++) {
                    double initialAngle = j*20.0;
                for (int i = 0; i <= 15; i++) {
                	
                	double angle = Math.toRadians(initialAngle/2 + (i * (180.0 - initialAngle) / 15.0));
            	    double radius = 1.5+j*0.12;
            	    double x = radius * Math.sin(angle+pitch) * Math.cos(yaw);
            	    double y = radius * Math.cos(angle+pitch);
            	    double z = radius * Math.sin(angle+pitch) * Math.sin(yaw);
                	
                    Particle.DustOptions blueToLightBlue = new Particle.DustOptions(Color.fromRGB(r, g, b), 1F);
            	    
            	    if (onGround) {
            	    	p.getWorld().spawnParticle(Particle.SOUL_FIRE_FLAME, movement.clone().add(pos).add(new Vector(x, y, z).clone().rotateAroundAxis(new Vector(Math.cos(yaw)*Math.sin(pitch), Math.cos(pitch), Math.sin(yaw)*Math.sin(pitch)), initialAngle2)), 0, 0, 1, 0, FastUtils.randomDoubleInRange(0.15, 0.35));
            	    } else{
            	    	p.getWorld().spawnParticle(Particle.REDSTONE, movement.clone().add(pos).add(new Vector(x, y, z).clone().rotateAroundAxis(new Vector(Math.cos(yaw)*Math.sin(pitch), Math.cos(pitch), Math.sin(yaw)*Math.sin(pitch)), initialAngle2)), 1, blueToLightBlue);
            	    }
                    }
                r += 20;
                g += 18;
                b += 10;
                }
                r = 33;
                g = 135;
                b = 196;
                
                movement = movement.add(direction.clone().rotateAroundAxis(new Vector(Math.cos(yaw), Math.cos(pitch), Math.sin(yaw)), initialAngle2).multiply(0.65));
                movements.set(a, movement);
                
           	     for (Entity e : movement.clone().add(pos).getNearbyEntities(1.5, 1.5, 1.5)) {
                 if (!(e instanceof LivingEntity target) || e.equals(p)) continue;
                 ((LivingEntity) e).damage(1, p);
           	                }
                    }
                mTick++;
            }
        }.runTaskTimer(Devoria.getInstance(), 0L, 1L);

        p.getWorld().playSound(p.getLocation(), Sound.ENTITY_WITHER_SHOOT, 0.35f, 1f);
        p.getWorld().playSound(p.getLocation(), Sound.ENTITY_WITHER_SHOOT, 0.35f, 0.9f);
        p.getWorld().playSound(p.getLocation(), Sound.ENTITY_WITHER_SHOOT, 0.35f, 0.8f);
    }

    @Override
    public String toString() {
        return "ManaPull";
    }
}
