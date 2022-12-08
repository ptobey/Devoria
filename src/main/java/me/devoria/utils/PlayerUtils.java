package me.devoria.utils;

import com.ticxo.modelengine.api.ModelEngineAPI;
import com.ticxo.modelengine.api.model.ModeledEntity;
import me.devoria.Devoria;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Statistic;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Mob;
import org.bukkit.entity.Player;

import java.util.HashMap;
import org.bukkit.metadata.FixedMetadataValue;

public class PlayerUtils {
    public static void changeHealth(Entity e, int healthChange, Entity damager, boolean canKill) {
        int xp = 0;

        String healthStats = e.getMetadata("healthStats").get(0).asString();
        HashMap<String,String> healthStatsMap = FastUtils.map(healthStats);


        String playerStats = e.getMetadata("attributes").get(0).asString();
        HashMap<String,String> entityStatsMap = FastUtils.map(playerStats);


        int currentHealth = Integer.parseInt(healthStatsMap.get("currentHealth"));
        int maxHealth = Integer.parseInt(entityStatsMap.get("health"));

        if(entityStatsMap.get("xp") != null) {
            xp = Integer.parseInt(entityStatsMap.get("xp"));
        }

        if(healthChange+currentHealth >= maxHealth) {
            e.setMetadata("healthStats", new FixedMetadataValue(Devoria.getInstance(), ",currentHealth:"+maxHealth));
        }
        else if(healthChange+currentHealth <= 0 && canKill) {
            updateChangeHealthStats(e, damager, -currentHealth);
            e.setMetadata("healthStats", new FixedMetadataValue(Devoria.getInstance(), ",currentHealth:0"));
            killEntity(e, String.valueOf(maxHealth), damager, xp);
        }
        else if(healthChange+currentHealth <= 0 && !canKill) {
            e.setMetadata("healthStats", new FixedMetadataValue(Devoria.getInstance(), ",currentHealth:1"));
        }
        else {
            updateChangeHealthStats(e, damager, healthChange);
            currentHealth += healthChange;
            e.setMetadata("healthStats", new FixedMetadataValue(Devoria.getInstance(), ",currentHealth:"+currentHealth));
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
                e.setMetadata("damagers", new FixedMetadataValue(Devoria.getInstance(), ",total:0"));
            }

            String damagersStats = e.getMetadata("damagers").get(0).asString();
            damagersStatsMap = FastUtils.map(damagersStats);

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


            e.setMetadata("damagers", new FixedMetadataValue(Devoria.getInstance(), damagersStatsString));
        }
        else {
            //heal?
        }

    }

    public static void killEntity(Entity e, String maxHealth, Entity damager, int xp) {
        if(e instanceof Player) {
            Player p = (Player) e;
            p.sendMessage("You died!");
            p.setMetadata("healthStats", new FixedMetadataValue(Devoria.getInstance(), ",currentHealth:"+maxHealth));
            PlayerUtils.updateHealthBar(p);
            Bukkit.broadcast(new TextComponent(p.getName()+" was killed by "+damager.getName()));
            p.setHealth(0);
            int deaths = p.getStatistic(Statistic.DEATHS);
            p.setStatistic(Statistic.DEATHS, deaths-1);
        }
        else if (e instanceof Mob) {
            Mob m = (Mob) e;
            PlayerUtils.updateHealthBar(e);
            m.setHealth(0);


/*
            String playerStats = e.getMetadata("damagers").get(0).asString();
            HashMap<String,String> damagersMap = FastUtils.map(playerStats);


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

    public static int calculate(Player p) {

        String playerStats = p.getMetadata("attributes").get(0).asString();
        String healthStats = p.getMetadata("healthStats").get(0).asString();

        HashMap<String,String> statsMap = FastUtils.map(playerStats);
        HashMap<String,String> healthStatsMap = FastUtils.map(healthStats);

        int maxHealth = 0;
        int hpr = 0;
        int hprPercent = 0;
        int maxHealthHpr = 0;
        int currentHealth = 0;
        int currentHealthHpr = 0;
        int base = 5;



        if (statsMap.get("health") != null) {
            maxHealth = Integer.parseInt(statsMap.get("health"));
        }
        if (healthStatsMap.get("currentHealth") != null) {
            currentHealth = Integer.parseInt(healthStatsMap.get("currentHealth"));
        }
        if (statsMap.get("hpr") != null) {
            hpr = Integer.parseInt(statsMap.get("hpr"));
        }
        if (statsMap.get("hprPercent") != null) {
            hprPercent = Integer.parseInt(statsMap.get("hprPercent"));
        }
        if (statsMap.get("maxHealthHpr") != null) {
            maxHealthHpr = Integer.parseInt(statsMap.get("maxHealthHpr"));
        }
        if (statsMap.get("currentHealthHpr") != null) {
            currentHealthHpr = Integer.parseInt(statsMap.get("currentHealthHpr"));
        }


        int rawHpr = base + hpr;
        double multiplier;

        if(rawHpr < 0) {
            multiplier = (100 - hprPercent) / 100.0;
        }
        else {
            multiplier = (100 + hprPercent) / 100.0;
        }

        int normalHpr = (int) (rawHpr*multiplier);

        double fractionMaxHpr = maxHealth*(maxHealthHpr/100.0);

        double fractionCurrentHpr = currentHealth*(currentHealthHpr/100.0);


        return (int) (normalHpr+fractionMaxHpr+fractionCurrentHpr);
    }

    public static void updateHealthBar(Player p) {

        //Max Health
        String playerStats = p.getMetadata("attributes").get(0).asString();
        HashMap<String, String> playerStatsMap = FastUtils.map(playerStats);

        //Current Health
        String healthStats = p.getMetadata("healthStats").get(0).asString();
        HashMap<String, String> healthStatsMap = FastUtils.map(healthStats);

        String maxHealth = playerStatsMap.get("health");
        String currentHealth = healthStatsMap.get("currentHealth");


        p.sendActionBar(ChatColor.DARK_RED + "❤ " + currentHealth + "/" + maxHealth);
    }

    public static void updateHealthBar(Entity e) {

            //Max Health
            String playerStats = e.getMetadata("attributes").get(0).asString();
            HashMap<String, String> playerStatsMap = FastUtils.map(playerStats);

            //Current Health
            String healthStats = e.getMetadata("healthStats").get(0).asString();
            HashMap<String, String> healthStatsMap = FastUtils.map(healthStats);

            String maxHealth = playerStatsMap.get("health");
            String currentHealth = healthStatsMap.get("currentHealth");

            ModeledEntity m = ModelEngineAPI.getModeledEntity(e.getUniqueId());

            m.getNametagHandler().setCustomName("healthbar", ChatColor.DARK_RED + "❤ " + currentHealth + "/" + maxHealth);


        }
    }

