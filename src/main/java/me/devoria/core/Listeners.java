package me.devoria.core;

import com.destroystokyo.paper.event.player.PlayerArmorChangeEvent;
import com.ticxo.modelengine.api.model.ModeledEntity;
import me.devoria.core.attributeSystem.UpdateAttributes;
import me.devoria.core.damageSystem.ChangeHealth;
import me.devoria.core.damageSystem.SpawnDamageIndicator;
import me.devoria.core.damageSystem.UpdateHealthBar;
import me.devoria.core.itemSystem.*;
import me.devoria.core.onLogin.Registration;
import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.*;
import org.bukkit.block.Chest;
import org.bukkit.entity.*;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.ProjectileHitEvent;
import org.bukkit.event.player.*;
import org.bukkit.inventory.ItemStack;
import org.bukkit.metadata.FixedMetadataValue;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;

import java.io.FileNotFoundException;
import java.nio.charset.StandardCharsets;
import java.util.*;

import static java.lang.Thread.sleep;


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

                                UpdateItem.update(",fileName:"+attributes.get("file_name"));
                                c1.getInventory().setItem(i, UpdateItem.update(",fileName:"+attributes.get("file_name")));

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
        //IMPORTANT Registration.registerUser(uuid,username);
        //Bukkit.getLogger().info(c_class);



    }
    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent e) {
        Player p = e.getPlayer();
       // p.getInventory().clear();
        p.sendMessage("§aWelcome to Eternia!");


        //Health Bar
        new BukkitRunnable(){

            final int log = p.getStatistic(Statistic.LEAVE_GAME);

            @Override
            public void run(){
                if (p.getStatistic(Statistic.LEAVE_GAME) != log) {
                    cancel(); // this cancels it when they leave
                }

                UpdateHealthBar.update(p);
            }

        }.runTaskTimer(Core.getInstance(), 0L, 40L);


        //HPR
        new BukkitRunnable() {

            final int log = p.getStatistic(Statistic.LEAVE_GAME);

            @Override
            public void run() {



                if (p.getStatistic(Statistic.LEAVE_GAME) != log) {
                    cancel(); // this cancels it when they leave
                }

                int hpr = CalculateHealthRegen.calculate(p);

                ChangeHealth.change(p, hpr, null, false);
                UpdateHealthBar.update(p);
            }

        }.runTaskTimer(Core.getInstance(), 100L, 100L);

        updateAttributes(p);

        if(p.getMetadata("healthStats").size() == 0) {

            p.setMetadata("healthStats", new FixedMetadataValue(Core.getInstance(), ",currentHealth:1000"));
        }

    }




    @EventHandler
    public void onPlayerLeave(PlayerQuitEvent e) {
    }

    @EventHandler
    public void onArmorChange(PlayerArmorChangeEvent e) {

        Player p = e.getPlayer();
        updateAttributes(p);
        ChangeHealth.change(p,0,null,false);
        UpdateHealthBar.update(p);
    }

    @EventHandler
    public void hit(EntityDamageByEntityEvent e) {

        String damagerStats = "";
        boolean isPlayer = false;
        Entity damagerEntity = e.getDamager();
                Entity victim = e.getEntity();

        if(e.getDamager() instanceof Player) {
            Player damager = (Player) e.getDamager();
            damagerStats = damager.getPlayer().getInventory().getItemInMainHand().getItemMeta().getLocalizedName();
            isPlayer = true;
        }
        else if(e.getDamager() instanceof ModeledEntity) {
            ModeledEntity damager = (ModeledEntity) e.getDamager();

            damagerStats = damager.getEntity().getItemInMainHand().getItemMeta().getLocalizedName();

        }

        e.setDamage(0);

        ArrayList<String> damages = OutputDamageSystem.getDamage(damagerStats);

        ChangeHealth.change(victim, Integer.parseInt("-"+damages.get(6)), damagerEntity, true);

        if(victim instanceof Player) {
            UpdateHealthBar.update((Player) e.getEntity());
        }
        else {
            UpdateHealthBar.update(victim);
        }

        if(isPlayer) {
            SpawnDamageIndicator damageIndicator = new SpawnDamageIndicator();

            damageIndicator.spawn(victim.getWorld(),damages,victim.getLocation().add(1,1, 0));
            e.getDamager().sendMessage(damages.get(6));
        }
    }


    @EventHandler
    public void updateIem(PlayerItemHeldEvent e) {
        Player p = e.getPlayer();

        ItemStack helmet = p.getInventory().getHelmet();
        ItemStack chestplate = p.getInventory().getChestplate();
        ItemStack leggings = p.getInventory().getLeggings();
        ItemStack boots = p.getInventory().getBoots();
        String weaponStats = null;
        String helmetStats = null;
        String chestplateStats = null;
        String leggingsStats = null;
        String bootsStats = null;

        if(helmet != null && !helmet.getItemMeta().getLocalizedName().isEmpty()) {
            helmetStats = helmet.getItemMeta().getLocalizedName();
        }
        if(chestplate != null && !chestplate.getItemMeta().getLocalizedName().isEmpty()) {
            chestplateStats = chestplate.getItemMeta().getLocalizedName();
        }
        if(leggings != null && !leggings.getItemMeta().getLocalizedName().isEmpty()) {
            leggingsStats = leggings.getItemMeta().getLocalizedName();
        }
        if(boots != null && !boots.getItemMeta().getLocalizedName().isEmpty()) {
            bootsStats = boots.getItemMeta().getLocalizedName();
        }



        try {
            int slot = e.getNewSlot();

            if(p.getInventory().getItem(slot) != null && !p.getInventory().getItem(slot).getItemMeta().getLocalizedName().isEmpty()) {

                String stats = p.getInventory().getItem(slot).getItemMeta().getLocalizedName();
                p.getInventory().setItem(slot, UpdateItem.update(stats));

                HashMap<String, String> weaponStatsMap = MapData.map(stats);


                String type = weaponStatsMap.get("type");


                if(type.equals("bow") || type.equals("sword")) {

                    stats = p.getInventory().getItem(slot).getItemMeta().getLocalizedName();
                    weaponStats = stats;
                }

           }


        }
        catch(Exception ignore){
        }

        UpdateAttributes.update(p, weaponStats, helmetStats, chestplateStats, leggingsStats, bootsStats);
        ChangeHealth.change(p,0, null, false);
        UpdateHealthBar.update(p);

    }



//Makes pink wool shoot arrows if you're a huntsman or bard
    @EventHandler
    public void onUse(PlayerInteractEvent e) throws FileNotFoundException {
        if(e.getMaterial().equals(Material.PINK_WOOL)){

            Player p = e.getPlayer();
            String stats = p.getInventory().getItemInMainHand().getItemMeta().getLocalizedName();

            try {
                p.getInventory().setItemInMainHand((UpdateItem.update(stats)));


            e.setCancelled(true);

                if (e.getAction() == Action.RIGHT_CLICK_AIR || e.getAction() == Action.RIGHT_CLICK_BLOCK) {



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

    public static void updateAttributes(Player p) {
        ItemStack helmet = p.getInventory().getHelmet();
        ItemStack chestplate = p.getInventory().getChestplate();
        ItemStack leggings = p.getInventory().getLeggings();
        ItemStack boots = p.getInventory().getBoots();
        String weaponStats = null;
        String helmetStats = null;
        String chestplateStats = null;
        String leggingsStats = null;
        String bootsStats = null;

        if(helmet != null && !helmet.getItemMeta().getLocalizedName().isEmpty()) {
            helmetStats = helmet.getItemMeta().getLocalizedName();
        }
        if(chestplate != null && !chestplate.getItemMeta().getLocalizedName().isEmpty()) {
            chestplateStats = chestplate.getItemMeta().getLocalizedName();
        }
        if(leggings != null && !leggings.getItemMeta().getLocalizedName().isEmpty()) {
            leggingsStats = leggings.getItemMeta().getLocalizedName();
        }
        if(boots != null && !boots.getItemMeta().getLocalizedName().isEmpty()) {
            bootsStats = boots.getItemMeta().getLocalizedName();
        }

        try {


            if(p.getInventory().getItemInMainHand().getType() != Material.AIR && !p.getInventory().getItemInMainHand().getItemMeta().getLocalizedName().isEmpty()) {

                String stats = p.getInventory().getItemInMainHand().getItemMeta().getLocalizedName();
                p.getInventory().setItemInMainHand(UpdateItem.update(stats));



                HashMap<String, String> weaponStatsMap = MapData.map(stats);


                String type = weaponStatsMap.get("type");

                if(type.equals("bow") || type.equals("sword")) {
                    stats = p.getInventory().getItemInMainHand().getItemMeta().getLocalizedName();
                    weaponStats = stats;
                }
            }
        }
        catch(Exception ignore) {
        }
        UpdateAttributes.update(p, weaponStats,helmetStats,chestplateStats,leggingsStats,bootsStats);
    }

}
