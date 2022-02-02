package me.devoria.core.damageSystem;

import me.devoria.core.Core;
import me.devoria.core.MapData;
import org.bukkit.entity.Player;
import org.bukkit.metadata.FixedMetadataValue;

import java.util.HashMap;

public class ChangeHealth {
    public static void change(Player p, int healthChange, boolean canKill) {

        String healthStats = p.getMetadata("healthStats").get(0).asString();
        HashMap<String,String> healthStatsMap = MapData.map(healthStats);


        String playerStats = p.getMetadata("attributes").get(0).asString();
        HashMap<String,String> playerStatsMap = MapData.map(playerStats);


        int currentHealth = Integer.parseInt(healthStatsMap.get("currentHealth"));
        int maxHealth = Integer.parseInt(playerStatsMap.get("health"));

        if(healthChange+currentHealth >= maxHealth) {
            p.setMetadata("healthStats", new FixedMetadataValue(Core.getInstance(), ",currentHealth:"+maxHealth));
        }
        else if(healthChange+currentHealth <= 0 && canKill) {
            p.sendMessage("You died!");
        }
        else if(healthChange+currentHealth <= 0 && !canKill) {
            p.setMetadata("healthStats", new FixedMetadataValue(Core.getInstance(), ",currentHealth:1"));
        }
        else {
            currentHealth += healthChange;
            p.setMetadata("healthStats", new FixedMetadataValue(Core.getInstance(), ",currentHealth:"+currentHealth));
        }





    }
}
