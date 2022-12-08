package me.devoria.listeners;

import com.destroystokyo.paper.event.player.PlayerArmorChangeEvent;
import me.devoria.Devoria;
import me.devoria.utils.FastUtils;
import me.devoria.utils.ItemUtils;
import me.devoria.utils.MiscellaneousUtils;
import me.devoria.utils.PlayerUtils;
import org.bukkit.*;
import org.bukkit.entity.*;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.entity.*;
import org.bukkit.event.player.*;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.metadata.FixedMetadataValue;
import org.bukkit.scheduler.BukkitRunnable;

import java.io.FileNotFoundException;
import java.util.*;



public class Listeners implements Listener {


    //Makes arrows despawn
    @EventHandler
    public void onProjectileHit(ProjectileHitEvent ev) {
        Entity entity = ev.getEntity();
        if (entity.getType() == EntityType.ARROW) {
            entity.remove();
        }
    }

    @EventHandler
    public void onMobDeath(EntityDeathEvent event) {
        event.getDrops().clear();
        LivingEntity e = event.getEntity();

        String damagers = e.getMetadata("damagers").get(0).asString();
        HashMap<String,String> damagersMap = FastUtils.map(damagers);

        String entityStats = e.getMetadata("attributes").get(0).asString();
        HashMap<String,String> entityStatsMap = FastUtils.map(entityStats);

        int xp = Integer.parseInt(entityStatsMap.get("xp"));


        for(String d : damagersMap.keySet()) {

            if(!d.equals("total")) {
                if ((Integer.parseInt(damagersMap.get(d)) <= (Integer.parseInt(damagersMap.get("total")) * 0.15))) {
                    try {
                        Collection<ItemStack> drops = ItemUtils.generate("huntsman", "15");

                        for(ItemStack drop : drops) {

                            ItemMeta itemMeta = drop.getItemMeta();
                            String itemInfo = drop.getItemMeta().getLocalizedName() + ",owner:" + d;
                            itemMeta.setLocalizedName(itemInfo);
                            drop.setItemMeta(itemMeta);

                            e.getLocation().getWorld().dropItemNaturally(e.getLocation(), drop);
                        }


                    } catch (FileNotFoundException err) {
                        err.printStackTrace();
                    }
                }
                int percentXp = (int) (xp * (Double.parseDouble(damagersMap.get(d)) / Double.parseDouble(damagersMap.get("total"))));
                Bukkit.getPlayer(UUID.fromString(d)).sendMessage("+"+percentXp+" xp!");

            }
        }
    }

    @EventHandler
    public void onItemPickUp(PlayerAttemptPickupItemEvent event) {

        HashMap<String,String> map = FastUtils.map(event.getItem().getItemStack().getItemMeta().getLocalizedName());

        if(!event.getPlayer().equals(Bukkit.getPlayer(UUID.fromString(map.get("owner"))))) {
            event.setCancelled(true);
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

                PlayerUtils.updateHealthBar(p);
            }

        }.runTaskTimer(Devoria.getInstance(), 0L, 40L);


        //HPR
        new BukkitRunnable() {

            final int log = p.getStatistic(Statistic.LEAVE_GAME);

            @Override
            public void run() {



                if (p.getStatistic(Statistic.LEAVE_GAME) != log) {
                    cancel(); // this cancels it when they leave
                }

                int hpr = PlayerUtils.calculate(p);

                PlayerUtils.changeHealth(p, hpr, null, false);
                PlayerUtils.updateHealthBar(p);
            }

        }.runTaskTimer(Devoria.getInstance(), 100L, 100L);



        if(p.getMetadata("healthStats").size() == 0) {

            p.setMetadata("healthStats", new FixedMetadataValue(Devoria.getInstance(), ",currentHealth:1000"));
        }
        updateAttributes(p, -1);

    }




    @EventHandler
    public void onPlayerLeave(PlayerQuitEvent e) {
    }

    @EventHandler
    public void onArmorChange(PlayerArmorChangeEvent e) {

        Player p = e.getPlayer();
        updateAttributes(p, -1);
        PlayerUtils.changeHealth(p,0,null,false);
        PlayerUtils.updateHealthBar(p);
    }


    @EventHandler
    public void hit(EntityDamageByEntityEvent e) {

        e.setDamage(0);

        String damagerStats = "";
        boolean isPlayer = false;
        Entity damagerEntity = e.getDamager();
                Entity victim = e.getEntity();



        if(e.getDamager() instanceof Player) {
            Player damager = (Player) e.getDamager();
            damagerStats = damager.getPlayer().getInventory().getItemInMainHand().getItemMeta().getLocalizedName();
            isPlayer = true;

        }



        else {
            e.getDamager();
            Entity damager = e.getDamager();


            damagerStats = String.valueOf(damager.getMetadata("attributes").get(0).asString());

            System.out.println(damagerStats);

        }


        ArrayList<String> damages = ItemUtils.getItemDamage(damagerStats);

        if(isPlayer) {
            MiscellaneousUtils damageIndicator = new MiscellaneousUtils();

            damageIndicator.spawnDamageIndicator(victim.getWorld(),damages,victim.getLocation().add(1,1, 0));
            e.getDamager().sendMessage(damages.get(6));
        }

        PlayerUtils.changeHealth(victim, Integer.parseInt("-"+damages.get(6)), damagerEntity, true);

        if(victim instanceof Player) {
            PlayerUtils.updateHealthBar((Player) e.getEntity());
        }
        else {
            PlayerUtils.updateHealthBar(victim);
        }

    }


    @EventHandler
    public void updateIem(PlayerItemHeldEvent e) {
     Player p = e.getPlayer();
     updateAttributes(p, e.getNewSlot());

 /*

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


                if(!weaponStatsMap.get("unidentified").equals("1")) {

                    p.sendMessage(weaponStatsMap.get("unidentified"));
                    String type = weaponStatsMap.get("type");


                    if (type.equals("bow") || type.equals("sword")) {

                        weaponStats= p.getInventory().getItem(slot).getItemMeta().getLocalizedName();
                    }
                }

           }


        }
        catch(Exception ignore){
        }

        UpdateAttributes.update(p, weaponStats, helmetStats, chestplateStats, leggingsStats, bootsStats);

*/
    }




    @EventHandler
    public void onUse(PlayerInteractEvent e) throws FileNotFoundException {
        if(e.getMaterial().equals(Material.PINK_WOOL)){

            Player p = e.getPlayer();
            String stats = p.getInventory().getItemInMainHand().getItemMeta().getLocalizedName();

            try {
                p.getInventory().setItemInMainHand((ItemUtils.updateItem(stats)));


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

    public static void updateAttributes(Player p, int slot) {
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
            if(slot == -1) {
                if(p.getInventory().getItemInMainHand().getType() != Material.AIR && !p.getInventory().getItemInMainHand().getItemMeta().getLocalizedName().isEmpty()) {

                String stats = p.getInventory().getItemInMainHand().getItemMeta().getLocalizedName();
                HashMap<String, String> weaponStatsMap = FastUtils.map(stats);


                if(weaponStatsMap.get("unidentified") == null) {
                    p.getInventory().setItemInMainHand(ItemUtils.updateItem(stats));


                    String type = weaponStatsMap.get("type");

                    if (type.equals("bow") || type.equals("sword")) {
                        weaponStats = p.getInventory().getItemInMainHand().getItemMeta().getLocalizedName();

                    }
                }
                }
            }
            else {
                if(p.getInventory().getItem(slot) != null && !p.getInventory().getItem(slot).getItemMeta().getLocalizedName().isEmpty()) {

                    String stats = p.getInventory().getItem(slot).getItemMeta().getLocalizedName();
                    HashMap<String, String> weaponStatsMap = FastUtils.map(stats);


                    if(weaponStatsMap.get("unidentified") == null) {
                        p.getInventory().setItem(slot, ItemUtils.updateItem(stats));
                    }


                        String type = weaponStatsMap.get("type");


                        if (type.equals("bow") || type.equals("sword")) {

                            weaponStats = p.getInventory().getItem(slot).getItemMeta().getLocalizedName();
                        }



                }
            }
        }
        catch(Exception ignore) {
        }
        ItemUtils.updateAttributes(p, weaponStats,helmetStats,chestplateStats,leggingsStats,bootsStats);
        PlayerUtils.changeHealth(p,0, null, false);
        PlayerUtils.updateHealthBar(p);
    }

}
