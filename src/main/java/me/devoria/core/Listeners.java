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

import java.util.ArrayList;
import java.util.UUID;


public class Listeners implements Listener {

    public static ArrayList<me.devoria.core.Player> players = new ArrayList<>();

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
        UUID uuid = event.getPlayer().getUniqueId();
        String name = event.getPlayer().getDisplayName();
        if(lookUpPlayer(uuid) == null) {
            players.add(new me.devoria.core.Player(uuid, name, null));
        }
    }


    @EventHandler
    public void onUse(PlayerInteractEvent e) {
        if(e.getMaterial().equals(Material.PINK_WOOL)){
            if(lookUpPlayer(e.getPlayer().getUniqueId()).getType().equals("huntsman") ||lookUpPlayer(e.getPlayer().getUniqueId()).getType().equals("bard") ) {
                if (e.getAction() == Action.RIGHT_CLICK_AIR || e.getAction() == Action.RIGHT_CLICK_BLOCK) {
                    e.setCancelled(true);
                    Player p = e.getPlayer();
                    Arrow arrow = p.getWorld().spawn(p.getEyeLocation(),
                            Arrow.class);
                    arrow.setShooter(p);
                    arrow.setVelocity(p.getLocation().getDirection().multiply(2.25));
                }
            }
            else {
                e.getPlayer().sendMessage("You can't use this item! You are a "+lookUpPlayer(e.getPlayer().getUniqueId()).getType()+"!");
            }
        }

    }

    public static me.devoria.core.Player lookUpPlayer(UUID uuid) {
        for (me.devoria.core.Player player : players) {
            if (player.getUuid().equals(uuid)) {
                return player;
            }
        }
        return null;
    }
}