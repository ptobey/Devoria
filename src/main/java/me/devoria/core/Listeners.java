package me.devoria.core;

import me.devoria.core.DataBase.ClassTable;
import me.devoria.core.damageSystem.SpawnDamageIndicator;
import me.devoria.core.itemSystem.*;
import me.devoria.core.onLogin.Registration;
import org.apache.commons.lang.ObjectUtils;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Chest;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.entity.ProjectileHitEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerItemHeldEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerLoginEvent;

import java.io.FileNotFoundException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Map;
import java.util.Objects;
import java.util.UUID;


public class Listeners implements Listener {


    @EventHandler
    public void onOpenChest(PlayerInteractEvent e) {
        try {
            World world = e.getPlayer().getWorld();
            Location location = new Location(world, -780, 4, 703);

            if (Objects.requireNonNull(e.getClickedBlock()).getLocation().equals(location)) {

                Map<String, Object> attributes;


                if (location.getBlock().getType() == Material.CHEST) {
                    Chest c1 = (Chest) location.getBlock().getState();

                    for (int i = 0; i < 27; i++) {
                        try {
                            attributes = GenerateLoot.generate("huntsman", 0, "15");
                            if (attributes.get("rarity").equals("common")) {

                                UpdateWeapon.update(",fileName:"+attributes.get("file_name"));
                                c1.getInventory().setItem(i, UpdateWeapon.update(",fileName:"+attributes.get("file_name")));

                            } else {
                                c1.getInventory().setItem(i, MakeUnidentifiedItem.makeUnidentifiedItem(attributes.get("file_name"), attributes.get("rarity"), attributes.get("type"), attributes.get("level")));
                            }


                        } catch (FileNotFoundException err) {
                            err.printStackTrace();
                        }

                    }
                }
            }
        }
        catch(NullPointerException ignore) {
        }
    }



    //Makes arrows despawn
    @EventHandler
    public void onProjectileHit(ProjectileHitEvent ev) {
        Entity entity = ev.getEntity();
        if (entity.getType() == EntityType.ARROW) {
            entity.remove();
        }
    }
    //on login events!
    @EventHandler
    public void onPlayerLogin(PlayerLoginEvent event) {
        UUID uuid = event.getPlayer().getUniqueId();
        String username = event.getPlayer().getName();
        //String c_class = ClassTable.FindCurrentClass(uuid);
        //Registering players on login
        //ClassTable.SetCurrentClass(uuid,"huntsman");
        Registration.registerUser(uuid,username);
        //Bukkit.getLogger().info(c_class);

    }
    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent e) {
        Player p = e.getPlayer();
        Core.getClassSelectGUI().openInventory(p);
        p.sendMessage("§aWelcome to Devoria!");
    }

    @EventHandler
    public void updateIem(PlayerItemHeldEvent e) throws FileNotFoundException {
        try {
            Player p = e.getPlayer();
            String stats = p.getInventory().getItemInMainHand().getItemMeta().getLocalizedName();
            p.getInventory().setItemInMainHand((UpdateWeapon.update(stats)));
        }
        catch(NullPointerException ignore){
        }

    }



//Makes pink wool shoot arrows if you're a huntsman or bard
    @EventHandler
    public void onUse(PlayerInteractEvent e) throws FileNotFoundException {
        if(e.getMaterial().equals(Material.PINK_WOOL)){

            Player p = e.getPlayer();
            String stats = p.getInventory().getItemInMainHand().getItemMeta().getLocalizedName();

            try {
                p.getInventory().setItemInMainHand((UpdateWeapon.update(stats)));


            e.setCancelled(true);

                if (e.getAction() == Action.RIGHT_CLICK_AIR || e.getAction() == Action.RIGHT_CLICK_BLOCK) {

                    SpawnDamageIndicator damageIndicator = new SpawnDamageIndicator();

                    ArrayList<String> damages = OutputDamageSystem.getDamage(stats);

                    damageIndicator.spawn(p.getWorld(),damages,p.getLocation().add(1,1, 0));



                    p.sendMessage(damages.get(6));

                    Arrow arrow = p.getWorld().spawn(p.getEyeLocation(),
                            Arrow.class);
                    arrow.setShooter(p);
                    arrow.setVelocity(p.getLocation().getDirection().multiply(2.25));
                }
            }
            catch(NullPointerException ignore){
            }
        }

    }
//Temp method for finding a player object using UUID

}