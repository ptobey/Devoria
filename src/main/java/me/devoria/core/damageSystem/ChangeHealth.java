package me.devoria.core.damageSystem;

import me.devoria.core.Core;
import me.devoria.core.MapData;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.Bukkit;
import org.bukkit.Statistic;
import org.bukkit.entity.*;
import org.bukkit.metadata.FixedMetadataValue;

import java.util.HashMap;
import java.util.UUID;

public class ChangeHealth {
    public static void change(Entity e, int healthChange, Entity damager, boolean canKill) {

        if (healthChange < 0 && damager.getType() == EntityType.PLAYER) {
            Player p = (Player) damager;
            String damagersStatsString = "";
            HashMap<String, String> damagersStatsMap = null;
            
            
            try {
                String damagersStats = e.getMetadata("damagers").get(0).asString();
                damagersStatsMap = MapData.map(damagersStats);
            }
            catch (Exception error){
            }
            



            if (damagersStatsMap != null && damagersStatsMap.get(damager.getUniqueId().toString()) != null) {

                int totalDamage = Integer.parseInt(damagersStatsMap.get(damager.getUniqueId().toString())) + healthChange;

                for (String d : damagersStatsMap.keySet()) {
                    if (d.equals(damager.getUniqueId().toString())) {
                        damagersStatsString += "," + d + ":" + totalDamage;
                    } else {
                        damagersStatsString += "," + d + ":" + damagersStatsMap.get(d);
                    }

                }
            }
            else {
                if (damagersStatsMap != null) {
                    for (String d : damagersStatsMap.keySet()) {
                        damagersStatsString += "," + d + ":" + damagersStatsMap.get(d);
                    }
                }
                damagersStatsString += "," + p.getUniqueId() + ":" + healthChange;
            }
            e.setMetadata("damagers", new FixedMetadataValue(Core.getInstance(), damagersStatsString));
        }

        else {
            //heal?
        }


        String healthStats = e.getMetadata("healthStats").get(0).asString();
        HashMap<String,String> healthStatsMap = MapData.map(healthStats);


        String playerStats = e.getMetadata("attributes").get(0).asString();
        HashMap<String,String> playerStatsMap = MapData.map(playerStats);


        int currentHealth = Integer.parseInt(healthStatsMap.get("currentHealth"));
        int maxHealth = Integer.parseInt(playerStatsMap.get("health"));

        if(healthChange+currentHealth >= maxHealth) {
            e.setMetadata("healthStats", new FixedMetadataValue(Core.getInstance(), ",currentHealth:"+maxHealth));
        }
        else if(healthChange+currentHealth <= 0 && canKill) {
            e.setMetadata("healthStats", new FixedMetadataValue(Core.getInstance(), ",currentHealth:0"));
            death(e, String.valueOf(maxHealth), damager);
        }
        else if(healthChange+currentHealth <= 0 && !canKill) {
            e.setMetadata("healthStats", new FixedMetadataValue(Core.getInstance(), ",currentHealth:1"));
        }
        else {
            currentHealth += healthChange;
            e.setMetadata("healthStats", new FixedMetadataValue(Core.getInstance(), ",currentHealth:"+currentHealth));
        }

    }
    public static void death(Entity e, String maxHealth, Entity damager) {
        if(e instanceof Player) {
            Player p = (Player) e;
            p.sendMessage("You died!");
            p.setMetadata("healthStats", new FixedMetadataValue(Core.getInstance(), ",currentHealth:"+maxHealth));
            UpdateHealthBar.update(p);
            Bukkit.broadcast(new TextComponent(p.getName()+" was killed by "+damager.getName()));
            p.setHealth(0);
            int deaths = p.getStatistic(Statistic.DEATHS);
            p.setStatistic(Statistic.DEATHS, deaths-1);
        }
        else if ( e instanceof Mob) {
            Mob m = (Mob) e;
            UpdateHealthBar.update(e);
            m.setHealth(0);
            int totalDamage = 0;

            String playerStats = e.getMetadata("damagers").get(0).asString();
            HashMap<String,String> damagersMap = MapData.map(playerStats);


            for(String d : damagersMap.keySet()) {
                if((Integer.parseInt(damagersMap.get(d)) <= (Integer.parseInt(maxHealth) * -0.15))) {
                    Bukkit.getPlayer((UUID.fromString(d))).sendMessage("You got xp for your kill because you did " + damagersMap.get(d) + " damage!");
                    // Item drops
                }
            }
            if(m.getPassengers().get(0) instanceof ArmorStand) {
               ArmorStand a = (ArmorStand) m.getPassengers().get(0);
               a.remove();
            }
        }

    }
}
