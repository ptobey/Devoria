package me.devoria.listeners;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.UUID;
import me.devoria.utils.FastUtils;
import me.devoria.utils.ItemUtils;
import me.devoria.utils.MiscellaneousUtils;
import me.devoria.utils.PlayerUtils;
import org.bukkit.Bukkit;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.entity.ProjectileHitEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class EntityListener implements Listener {
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
    public void hit(EntityDamageByEntityEvent e) {
        e.setDamage(0);
        String damagerStats = "";
        boolean isPlayer = false;
        Entity damagerEntity = e.getDamager();
        Entity victim = e.getEntity();
        damagerStats = e.getDamager().getMetadata("attributes").get(0).asString();
        ArrayList<String> damages = ItemUtils.getItemDamage(damagerStats, false);

        if(e.getDamager() instanceof Player) {
            MiscellaneousUtils damageIndicator = new MiscellaneousUtils();

            damageIndicator.spawnDamageIndicator(victim.getWorld(),damages,victim.getLocation().add(1,1, 0));
            e.getDamager().sendMessage(damages.get(8));
        }

        PlayerUtils.changeHealth(victim, Integer.parseInt("-"+damages.get(8)), damagerEntity, true);

        if(victim instanceof Player) {
            PlayerUtils.updateHealthBar((Player) e.getEntity());
        }
        else {
            PlayerUtils.updateHealthBar(victim);
        }

    }
}
