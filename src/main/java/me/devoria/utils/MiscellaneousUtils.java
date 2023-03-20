package me.devoria.utils;

import java.util.ArrayList;
import java.util.Random;
import me.devoria.Devoria;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.ArmorStand;

public class MiscellaneousUtils {
    public void spawnDamageIndicator(World world, ArrayList<String> damages, Location location) {
        double distance = 0.25;
        int delay = 10;

        if (Integer.parseInt(damages.get(0)) > 0) {
            ArmorStand as1 = world.spawn(location.subtract(0, distance, 0), ArmorStand.class);
            as1.setInvisible(true);
            as1.setCustomNameVisible(true);
            as1.setMarker(true);
            as1.setCustomName(ChatColor.GOLD + "-" + damages.get(0) + " ✸");

            Bukkit.getScheduler().runTaskLater(Devoria.getInstance(), as1::remove, delay);
        }
        if (Integer.parseInt(damages.get(1)) > 0) {
            ArmorStand as2 = world.spawn(location.subtract(0, distance, 0), ArmorStand.class);
            as2.setInvisible(true);
            as2.setCustomNameVisible(true);
            as2.setMarker(true);
            as2.setCustomName(ChatColor.DARK_GREEN + "-" + damages.get(1) + " ✿");

            Bukkit.getScheduler().runTaskLater(Devoria.getInstance(), as2::remove, delay);
        }
        if (Integer.parseInt(damages.get(2)) > 0) {
            ArmorStand as3 = world.spawn(location.subtract(0, distance, 0), ArmorStand.class);
            as3.setInvisible(true);
            as3.setCustomNameVisible(true);
            as3.setMarker(true);
            as3.setCustomName(ChatColor.DARK_RED + "-" + damages.get(2) + " ✹");

            Bukkit.getScheduler().runTaskLater(Devoria.getInstance(), as3::remove, delay);
        }
        if (Integer.parseInt(damages.get(3)) > 0) {
            ArmorStand as4 = world.spawn(location.subtract(0, distance, 0), ArmorStand.class);
            as4.setInvisible(true);
            as4.setCustomNameVisible(true);
            as4.setMarker(true);
            as4.setCustomName(ChatColor.AQUA + "-" + damages.get(3) + " ❆");

            Bukkit.getScheduler().runTaskLater(Devoria.getInstance(), as4::remove, delay);
        }
        if (Integer.parseInt(damages.get(4)) > 0) {
            ArmorStand as5 = world.spawn(location.subtract(0, distance, 0), ArmorStand.class);
            as5.setInvisible(true);
            as5.setCustomNameVisible(true);
            as5.setMarker(true);
            as5.setCustomName(ChatColor.YELLOW + "-" + damages.get(4) + " ✦");

            Bukkit.getScheduler().runTaskLater(Devoria.getInstance(), as5::remove, delay);
        }
        if (Integer.parseInt(damages.get(5)) > 0) {
            ArmorStand as6 = world.spawn(location.subtract(0, distance, 0), ArmorStand.class);
            as6.setInvisible(true);
            as6.setCustomNameVisible(true);
            as6.setMarker(true);
            as6.setCustomName(ChatColor.DARK_PURPLE + "-" + damages.get(5) + " ✺ ");

            Bukkit.getScheduler().runTaskLater(Devoria.getInstance(), as6::remove, delay);
        }
    }

    public static String generatePercentage() {
        Random randomNumbers = new Random();
        int randomNumber1 = randomNumbers.ints(0, 101).findAny().getAsInt();
        return String.valueOf(randomNumber1);
    }
}
