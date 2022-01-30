package me.devoria.core.attributeSystem;

import me.devoria.core.Core;;
import me.devoria.core.itemSystem.CalculateStatsWithRange;
import org.bukkit.attribute.Attribute;
import org.bukkit.entity.Player;
import org.bukkit.metadata.FixedMetadataValue;

import java.util.HashMap;

public class UpdateAttributes {

    public static void update(Player p, String weaponItemData, String helmetItemData, String chestplateItemData, String leggingsItemData, String bootsItemData) {


        String attributes = "";
        int walkSpeed = 0;
        int maxHealth = 0;


        HashMap<String,String> weaponStatsMap = new HashMap<>();
        HashMap<String,String> helmetStatsMap = new HashMap<>();
        HashMap<String,String> chestplateStatsMap = new HashMap<>();
        HashMap<String,String> leggingsStatsMap = new HashMap<>();
        HashMap<String,String> bootsStatsMap = new HashMap<>();

        if(weaponItemData != null) {
            String[] separatedWeaponStats = weaponItemData.split(",");

            for (int i = 1; i < separatedWeaponStats.length; i++) {
                String[] arr = separatedWeaponStats[i].split(":");
                weaponStatsMap.put(arr[0], arr[1]);
            }
            if (weaponStatsMap.get("walkSpeed") != null) {
                walkSpeed += Integer.parseInt(weaponStatsMap.get("walkSpeed"));
            }
        }

        if(helmetItemData != null) {
            String[] separatedHelmetStats = helmetItemData.split(",");

            for (int i = 1; i < separatedHelmetStats.length; i++) {
                String[] arr = separatedHelmetStats[i].split(":");
                helmetStatsMap.put(arr[0], arr[1]);
            }
            if (helmetStatsMap.get("walkSpeed") != null) {
                walkSpeed += Integer.parseInt(helmetStatsMap.get("walkSpeed"));
            }
            if (helmetStatsMap.get("health") != null) {
                maxHealth += Integer.parseInt(helmetStatsMap.get("health"));
            }
        }

        if(chestplateItemData != null) {
            String[] separatedChestplateStats = chestplateItemData.split(",");

            for (int i = 1; i < separatedChestplateStats.length; i++) {
                String[] arr = separatedChestplateStats[i].split(":");
                chestplateStatsMap.put(arr[0], arr[1]);
            }
            if (chestplateStatsMap.get("walkSpeed") != null) {
                walkSpeed += Integer.parseInt(chestplateStatsMap.get("walkSpeed"));
            }
            if (chestplateStatsMap.get("health") != null) {
                maxHealth += Integer.parseInt(chestplateStatsMap.get("health"));
            }
        }

        if(leggingsItemData != null) {
            String[] separatedLeggingsStats = leggingsItemData.split(",");

            for (int i = 1; i < separatedLeggingsStats.length; i++) {
                String[] arr = separatedLeggingsStats[i].split(":");
                leggingsStatsMap.put(arr[0], arr[1]);
            }
            if (leggingsStatsMap.get("walkSpeed") != null) {
                walkSpeed += Integer.parseInt(leggingsStatsMap.get("walkSpeed"));
            }
            if (leggingsStatsMap.get("health") != null) {
                maxHealth += Integer.parseInt(leggingsStatsMap.get("health"));
            }
        }

        if(bootsItemData != null) {
            String[] separatedBootsStats = bootsItemData.split(",");

            for (int i = 1; i < separatedBootsStats.length; i++) {
                String[] arr = separatedBootsStats[i].split(":");
                bootsStatsMap.put(arr[0], arr[1]);
            }
            if (bootsStatsMap.get("walkSpeed") != null) {
                walkSpeed += Integer.parseInt(bootsStatsMap.get("walkSpeed"));
            }
            if (bootsStatsMap.get("health") != null) {
                maxHealth += Integer.parseInt(bootsStatsMap.get("health"));
            }
        }


        if (maxHealth < 5) {
            maxHealth = 5;
        }

        attributes += ",health:"+maxHealth+",walkSpeed:"+walkSpeed;

        p.setMetadata("attributes", new FixedMetadataValue(Core.getInstance(), attributes));
        String s = CalculateStatsWithRange.calculate("12-50", String.valueOf(walkSpeed), true);
        p.getAttribute(Attribute.GENERIC_MOVEMENT_SPEED).setBaseValue((float)Integer.parseInt(s)/100);
  //  p.sendMessage(String.valueOf((float)Integer.parseInt(s)/100));
    }
}
