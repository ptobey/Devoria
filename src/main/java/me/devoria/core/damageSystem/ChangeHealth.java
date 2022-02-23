package me.devoria.core.damageSystem;

import me.devoria.core.Core;
import me.devoria.core.Listeners;
import me.devoria.core.MapData;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Server;
import org.bukkit.Statistic;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Mob;
import org.bukkit.entity.Player;
import org.bukkit.metadata.FixedMetadataValue;

import java.util.HashMap;

public class ChangeHealth {
    public static void change(Entity p, int healthChange, Entity damager, boolean canKill) {


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
            death(p, String.valueOf(maxHealth), damager);
        }
        else if(healthChange+currentHealth <= 0 && !canKill) {
            p.setMetadata("healthStats", new FixedMetadataValue(Core.getInstance(), ",currentHealth:1"));
        }
        else {
            currentHealth += healthChange;
            p.setMetadata("healthStats", new FixedMetadataValue(Core.getInstance(), ",currentHealth:"+currentHealth));
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
        else if ( e instanceof  Mob) {
            Mob m = (Mob) e;
            m.setHealth(0);
            damager.sendMessage("You got xp for your kill!");
            if(m.getPassengers().get(0)instanceof ArmorStand) {
               ArmorStand a = (ArmorStand) m.getPassengers().get(0);
               a.remove();
            }
        }

    }
}
