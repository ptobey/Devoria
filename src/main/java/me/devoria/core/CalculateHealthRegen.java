package me.devoria.core;

import org.bukkit.entity.Player;

import java.util.HashMap;

public class CalculateHealthRegen {

    public static int calculate(Player p) {

        String playerStats = p.getMetadata("attributes").get(0).asString();
        String healthStats = p.getMetadata("healthStats").get(0).asString();

        HashMap<String,String> statsMap = MapData.map(playerStats);
        HashMap<String,String> healthStatsMap = MapData.map(healthStats);

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
}
