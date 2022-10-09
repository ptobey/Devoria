package me.devoria.core.damageSystem;

import me.devoria.core.Core;
import me.devoria.core.MapData;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.Bukkit;
import org.bukkit.Statistic;
import org.bukkit.entity.*;
import org.bukkit.metadata.FixedMetadataValue;

import java.util.HashMap;


public class ChangeHealth {
    public static void change(Entity e, int healthChange, Entity damager, boolean canKill) {
        int xp = 0;

        String healthStats = e.getMetadata("healthStats").get(0).asString();
        HashMap<String,String> healthStatsMap = MapData.map(healthStats);


        String playerStats = e.getMetadata("attributes").get(0).asString();
        HashMap<String,String> entityStatsMap = MapData.map(playerStats);


        int currentHealth = Integer.parseInt(healthStatsMap.get("currentHealth"));
        int maxHealth = Integer.parseInt(entityStatsMap.get("health"));

        if(entityStatsMap.get("xp") != null) {
            xp = Integer.parseInt(entityStatsMap.get("xp"));
        }

        if(healthChange+currentHealth >= maxHealth) {
            e.setMetadata("healthStats", new FixedMetadataValue(Core.getInstance(), ",currentHealth:"+maxHealth));
        }
        else if(healthChange+currentHealth <= 0 && canKill) {
            updateChangeHealthStats(e, damager, -currentHealth);
            e.setMetadata("healthStats", new FixedMetadataValue(Core.getInstance(), ",currentHealth:0"));
            death(e, String.valueOf(maxHealth), damager, xp);
        }
        else if(healthChange+currentHealth <= 0 && !canKill) {
            e.setMetadata("healthStats", new FixedMetadataValue(Core.getInstance(), ",currentHealth:1"));
        }
        else {
            updateChangeHealthStats(e, damager, healthChange);
            currentHealth += healthChange;
            e.setMetadata("healthStats", new FixedMetadataValue(Core.getInstance(), ",currentHealth:"+currentHealth));
        }

    }

    public static void updateChangeHealthStats(Entity e, Entity damager, int healthChange) {
        if (healthChange < 0 && damager.getType() == EntityType.PLAYER) {
            Player p = (Player) damager;
            String damagersStatsString = "";
            HashMap<String, String> damagersStatsMap;
            int combinedDamage = 0;
            boolean inList = false;

            if(e.getMetadata("damagers").size() == 0) {
                e.setMetadata("damagers", new FixedMetadataValue(Core.getInstance(), ",total:0"));
            }

            String damagersStats = e.getMetadata("damagers").get(0).asString();
            damagersStatsMap = MapData.map(damagersStats);

            if (damagersStatsMap.get(damager.getUniqueId().toString()) != null) {
                combinedDamage = Integer.parseInt(damagersStatsMap.get(damager.getUniqueId().toString())) + healthChange;
            }


            for (String d : damagersStatsMap.keySet()) {
                if (d.equals(damager.getUniqueId().toString())) {
                    damagersStatsString += "," + d + ":" + combinedDamage;
                    inList = true;
                }
                else if (d.equals("total")) {
                    int totalDamage = Integer.parseInt(damagersStatsMap.get("total")) + healthChange;
                    damagersStatsString += ",total:" + totalDamage;
                }
                else {
                    damagersStatsString += "," + d + ":" + damagersStatsMap.get(d);
                }
            }
            if(!inList) {
                damagersStatsString += "," + p.getUniqueId() + ":" + healthChange;
            }


            e.setMetadata("damagers", new FixedMetadataValue(Core.getInstance(), damagersStatsString));
        }
        else {
            //heal?
        }

    }

    public static void death(Entity e, String maxHealth, Entity damager, int xp) {
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
        else if (e instanceof Mob) {
            Mob m = (Mob) e;
            UpdateHealthBar.update(e);
            m.setHealth(0);


/*
            String playerStats = e.getMetadata("damagers").get(0).asString();
            HashMap<String,String> damagersMap = MapData.map(playerStats);


            for(String d : damagersMap.keySet()) {

                if(!d.equals("total")) {
                    if ((Integer.parseInt(damagersMap.get(d)) <= (Integer.parseInt(damagersMap.get("total")) * 0.15))) {
                        Bukkit.getPlayer((UUID.fromString(d))).sendMessage("You got an item for your kill because you did " + damagersMap.get(d) + " of " + damagersMap.get("total") + " damage!");
                        // Item drops
                    }
                    int percentXp = (int) (xp * (Double.parseDouble(damagersMap.get(d)) / Double.parseDouble(damagersMap.get("total"))));
                    Bukkit.getPlayer((UUID.fromString(d))).sendMessage("+"+percentXp+" xp!");

                }
            }


 */
          //  if(m.getPassengers().get(0) instanceof ArmorStand) {
           //    ArmorStand a = (ArmorStand) m.getPassengers().get(0);
           //    a.remove();
          //  }
        }

    }
}
