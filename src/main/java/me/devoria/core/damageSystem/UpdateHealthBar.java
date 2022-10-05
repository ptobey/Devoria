package me.devoria.core.damageSystem;

import com.ticxo.modelengine.api.ModelEngineAPI;
import com.ticxo.modelengine.api.model.ModeledEntity;
import me.devoria.core.MapData;
import org.bukkit.ChatColor;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;

import java.util.HashMap;

public class UpdateHealthBar {

    public static void update(Player p) {

        //Max Health
        String playerStats = p.getMetadata("attributes").get(0).asString();
        HashMap<String, String> playerStatsMap = MapData.map(playerStats);

        //Current Health
        String healthStats = p.getMetadata("healthStats").get(0).asString();
        HashMap<String, String> healthStatsMap = MapData.map(healthStats);

        String maxHealth = playerStatsMap.get("health");
        String currentHealth = healthStatsMap.get("currentHealth");


        p.sendActionBar(ChatColor.DARK_RED + "❤ " + currentHealth + "/" + maxHealth);
    }

    public static void update(Entity e) {

            //Max Health
            String playerStats = e.getMetadata("attributes").get(0).asString();
            HashMap<String, String> playerStatsMap = MapData.map(playerStats);

            //Current Health
            String healthStats = e.getMetadata("healthStats").get(0).asString();
            HashMap<String, String> healthStatsMap = MapData.map(healthStats);

            String maxHealth = playerStatsMap.get("health");
            String currentHealth = healthStatsMap.get("currentHealth");

            ModeledEntity m = ModelEngineAPI.getModeledEntity(e.getUniqueId());

            m.getNametagHandler().setCustomName("healthbar", ChatColor.DARK_RED + "❤ " + currentHealth + "/" + maxHealth);


        }
    }

