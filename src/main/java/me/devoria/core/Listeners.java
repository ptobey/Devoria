package me.devoria.core;

import org.bukkit.Material;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.entity.ProjectileHitEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerLoginEvent;


public class Listeners implements Listener {

    @EventHandler
    public void onProjectileHit(ProjectileHitEvent ev) {
        Entity entity = ev.getEntity();
        if (entity.getType() == EntityType.ARROW) {
            entity.remove();
        }
    }

    @EventHandler
    public void onPlayerLogin(PlayerLoginEvent event) {
        event.getPlayer().sendMessage("Welcome to the server!");

    }


    @EventHandler
    public void onUse(PlayerInteractEvent e) {
        if(e.getMaterial().equals(Material.PINK_WOOL)) {
            if(e.getAction() == Action.RIGHT_CLICK_AIR || e.getAction() == Action.RIGHT_CLICK_BLOCK) {
                e.setCancelled(true);
                Player p = e.getPlayer();
                Arrow arrow = p.getWorld().spawn(p.getEyeLocation(),
                        Arrow.class);
                arrow.setShooter(p);
                arrow.setVelocity(p.getLocation().getDirection().multiply(2));
            }
        }

    }

}