package me.devoria.utils;

import java.util.Random;
import me.devoria.Devoria;
import me.devoria.combat.DamageRoll;
import me.devoria.combat.DamageType;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.ArmorStand;

public class MiscellaneousUtils {
    public void spawnDamageIndicator(World world, DamageRoll damages, Location location) {
        double distance = 0.25;
        int delay = 10;

        for (DamageType type : DamageType.values()) {
            int amount = damages.amount(type);
            if (amount == 0) {
                continue;
            }

            ArmorStand indicator = world.spawn(location.add(0, distance, 0), ArmorStand.class);
            indicator.setInvisible(true);
            indicator.setCustomNameVisible(true);
            indicator.setMarker(true);
            indicator.setCustomName(color(type) + "-" + amount + " " + symbol(type));
            Bukkit.getScheduler().runTaskLater(Devoria.getInstance(), indicator::remove, delay);
        }
    }

    private ChatColor color(DamageType type) {
        return switch (type) {
            case PHYSICAL -> ChatColor.GOLD;
            case EARTH -> ChatColor.DARK_GREEN;
            case FIRE -> ChatColor.DARK_RED;
            case ARCANE -> ChatColor.AQUA;
            case LIGHT -> ChatColor.YELLOW;
            case DARK -> ChatColor.DARK_PURPLE;
            case WIND -> ChatColor.GRAY;
            case ELECTRIC -> ChatColor.YELLOW;
        };
    }

    private String symbol(DamageType type) {
        return switch (type) {
            case PHYSICAL -> "✸";
            case EARTH -> "✿";
            case FIRE -> "✹";
            case ARCANE -> "❆";
            case LIGHT -> "✦";
            case DARK -> "✺";
            case WIND -> "➶";
            case ELECTRIC -> "⚡";
        };
    }

    public static String generatePercentage() {
        Random randomNumbers = new Random();
        int randomNumber1 = randomNumbers.ints(0, 101).findAny().getAsInt();
        return String.valueOf(randomNumber1);
    }
}
