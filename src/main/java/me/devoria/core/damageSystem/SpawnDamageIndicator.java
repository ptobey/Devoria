package me.devoria.core.damageSystem;

import me.devoria.core.Core;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.ArmorStand;

import java.util.ArrayList;


public class SpawnDamageIndicator {

    public void spawn(World world, ArrayList<String> damages, Location location) {

        double distance = 0.125;
        int delay = 10;


        if (Integer.parseInt(damages.get(0)) > 0) {
            ArmorStand as1 = world.spawn(location.subtract(0, distance, 0), ArmorStand.class);
            as1.setInvisible(true);
            as1.setCustomNameVisible(true);
            as1.setMarker(true);
            as1.setCustomName(ChatColor.GOLD + "-" + damages.get(0) + " ✸");

            Bukkit.getScheduler().runTaskLater(Core.getInstance(), as1::remove, delay);
        }
        if (Integer.parseInt(damages.get(1)) > 0) {
            ArmorStand as2 = world.spawn(location.subtract(0, distance, 0), ArmorStand.class);
            as2.setInvisible(true);
            as2.setCustomNameVisible(true);
            as2.setMarker(true);
            as2.setCustomName(ChatColor.DARK_GREEN + "-" + damages.get(1) + " ✿");

            Bukkit.getScheduler().runTaskLater(Core.getInstance(), as2::remove, delay);
        }
        if (Integer.parseInt(damages.get(2)) > 0) {
            ArmorStand as3 = world.spawn(location.subtract(0, distance, 0), ArmorStand.class);
            as3.setInvisible(true);
            as3.setCustomNameVisible(true);
            as3.setMarker(true);
            as3.setCustomName(ChatColor.DARK_RED + "-" + damages.get(2) + " ✹");

            Bukkit.getScheduler().runTaskLater(Core.getInstance(), as3::remove, delay);
        }
        if (Integer.parseInt(damages.get(3)) > 0) {
            ArmorStand as4 = world.spawn(location.subtract(0, distance, 0), ArmorStand.class);
            as4.setInvisible(true);
            as4.setCustomNameVisible(true);
            as4.setMarker(true);
            as4.setCustomName(ChatColor.AQUA + "-" + damages.get(3) + " ❆");

            Bukkit.getScheduler().runTaskLater(Core.getInstance(), as4::remove, delay);
        }
        if (Integer.parseInt(damages.get(4)) > 0) {
            ArmorStand as5 = world.spawn(location.subtract(0, distance, 0), ArmorStand.class);
            as5.setInvisible(true);
            as5.setCustomNameVisible(true);
            as5.setMarker(true);
            as5.setCustomName(ChatColor.YELLOW + "-" + damages.get(4) + " ✦");

            Bukkit.getScheduler().runTaskLater(Core.getInstance(), as5::remove, delay);
        }
        if (Integer.parseInt(damages.get(5)) > 0) {
            ArmorStand as6 = world.spawn(location.subtract(0, distance, 0), ArmorStand.class);
            as6.setInvisible(true);
            as6.setCustomNameVisible(true);
            as6.setMarker(true);
            as6.setCustomName(ChatColor.DARK_GRAY + "-" + damages.get(5) + " ✺ ");

            Bukkit.getScheduler().runTaskLater(Core.getInstance(), as6::remove, delay);
        }

    }
}



