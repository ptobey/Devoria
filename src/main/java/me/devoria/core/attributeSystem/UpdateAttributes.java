package me.devoria.core.attributeSystem;

import me.devoria.core.Core;;
import me.devoria.core.MapData;
import me.devoria.core.itemSystem.CalculateStatsWithRange;
import org.bukkit.attribute.Attribute;
import org.bukkit.entity.Player;
import org.bukkit.metadata.FixedMetadataValue;

import java.util.HashMap;

public class UpdateAttributes {

    public static void update(Player p, String weaponItemData, String helmetItemData, String chestplateItemData, String leggingsItemData, String bootsItemData) {

        String attributes = "";
        int walkSpeed = 0;
        int maxHealth = 5;
        int hpr = 0;
        int hprPercent = 0;
        int currentHealthHpr = 0;
        int maxHealthHpr = 0;
        int healthPercent = 0;


        if(weaponItemData != null) {

            HashMap<String,String> weaponStatsMap = MapData.map(weaponItemData);

            if (weaponStatsMap.get("walkSpeed") != null) {
                walkSpeed += Integer.parseInt(weaponStatsMap.get("walkSpeed"));
            }
            if (weaponStatsMap.get("healthBonus") != null) {
                maxHealth += Integer.parseInt(weaponStatsMap.get("healthBonus"));
            }
            if (weaponStatsMap.get("hpr") != null) {
                hpr += Integer.parseInt(weaponStatsMap.get("hpr"));
            }
            if (weaponStatsMap.get("hprPercent") != null) {
                hprPercent += Integer.parseInt(weaponStatsMap.get("hprPercent"));
            }
            if (weaponStatsMap.get("currentHealthHpr") != null) {
                currentHealthHpr += Integer.parseInt(weaponStatsMap.get("currentHealthHpr"));
            }
            if (weaponStatsMap.get("maxHealthHpr") != null) {
                maxHealthHpr += Integer.parseInt(weaponStatsMap.get("maxHealthHpr"));
            }
            if (weaponStatsMap.get("healthPercent") != null) {
                healthPercent += Integer.parseInt(weaponStatsMap.get("healthPercent"));
            }
        }

        if(helmetItemData != null) {

            HashMap<String,String> helmetStatsMap = MapData.map(helmetItemData);

            if (helmetStatsMap.get("walkSpeed") != null) {
                walkSpeed += Integer.parseInt(helmetStatsMap.get("walkSpeed"));
            }
            if (helmetStatsMap.get("health") != null) {
                maxHealth += Integer.parseInt(helmetStatsMap.get("health"));
            }
            if (helmetStatsMap.get("healthBonus") != null) {
                maxHealth += Integer.parseInt(helmetStatsMap.get("healthBonus"));
            }
            if (helmetStatsMap.get("hpr") != null) {
                hpr += Integer.parseInt(helmetStatsMap.get("hpr"));
            }
            if (helmetStatsMap.get("hprPercent") != null) {
                hprPercent += Integer.parseInt(helmetStatsMap.get("hprPercent"));
            }
            if (helmetStatsMap.get("currentHealthHpr") != null) {
                currentHealthHpr += Integer.parseInt(helmetStatsMap.get("currentHealthHpr"));
            }
            if (helmetStatsMap.get("maxHealthHpr") != null) {
                maxHealthHpr += Integer.parseInt(helmetStatsMap.get("maxHealthHpr"));
            }
            if (helmetStatsMap.get("healthPercent") != null) {
                healthPercent += Integer.parseInt(helmetStatsMap.get("healthPercent"));
            }
        }

        if(chestplateItemData != null) {

            HashMap<String,String> chestplateStatsMap = MapData.map(chestplateItemData);

            if (chestplateStatsMap.get("walkSpeed") != null) {
                walkSpeed += Integer.parseInt(chestplateStatsMap.get("walkSpeed"));
            }
            if (chestplateStatsMap.get("health") != null) {
                maxHealth += Integer.parseInt(chestplateStatsMap.get("health"));
            }
            if (chestplateStatsMap.get("healthBonus") != null) {
                maxHealth += Integer.parseInt(chestplateStatsMap.get("healthBonus"));
            }
            if (chestplateStatsMap.get("hpr") != null) {
                hpr += Integer.parseInt(chestplateStatsMap.get("hpr"));
            }
            if (chestplateStatsMap.get("hprPercent") != null) {
                hprPercent += Integer.parseInt(chestplateStatsMap.get("hprPercent"));
            }
            if (chestplateStatsMap.get("currentHealthHpr") != null) {
                currentHealthHpr += Integer.parseInt(chestplateStatsMap.get("currentHealthHpr"));
            }
            if (chestplateStatsMap.get("maxHealthHpr") != null) {
                maxHealthHpr += Integer.parseInt(chestplateStatsMap.get("maxHealthHpr"));
            }
            if (chestplateStatsMap.get("healthPercent") != null) {
                healthPercent += Integer.parseInt(chestplateStatsMap.get("healthPercent"));
            }

        }

        if(leggingsItemData != null) {

            HashMap<String,String> leggingsStatsMap = MapData.map(leggingsItemData);

            if (leggingsStatsMap.get("walkSpeed") != null) {
                walkSpeed += Integer.parseInt(leggingsStatsMap.get("walkSpeed"));
            }
            if (leggingsStatsMap.get("health") != null) {
                maxHealth += Integer.parseInt(leggingsStatsMap.get("health"));
            }
            if (leggingsStatsMap.get("healthBonus") != null) {
                maxHealth += Integer.parseInt(leggingsStatsMap.get("healthBonus"));
            }
            if (leggingsStatsMap.get("hpr") != null) {
                hpr += Integer.parseInt(leggingsStatsMap.get("hpr"));
            }
            if (leggingsStatsMap.get("hprPercent") != null) {
                hprPercent += Integer.parseInt(leggingsStatsMap.get("hprPercent"));
            }
            if (leggingsStatsMap.get("currentHealthHpr") != null) {
                currentHealthHpr += Integer.parseInt(leggingsStatsMap.get("currentHealthHpr"));
            }
            if (leggingsStatsMap.get("maxHealthHpr") != null) {
                maxHealthHpr += Integer.parseInt(leggingsStatsMap.get("maxHealthHpr"));
            }
            if (leggingsStatsMap.get("healthPercent") != null) {
                healthPercent += Integer.parseInt(leggingsStatsMap.get("healthPercent"));
            }
        }

        if(bootsItemData != null) {

            HashMap<String,String> bootsStatsMap = MapData.map(bootsItemData);

            if (bootsStatsMap.get("walkSpeed") != null) {
                walkSpeed += Integer.parseInt(bootsStatsMap.get("walkSpeed"));
            }
            if (bootsStatsMap.get("health") != null) {
                maxHealth += Integer.parseInt(bootsStatsMap.get("health"));
            }
            if (bootsStatsMap.get("healthBonus") != null) {
                maxHealth += Integer.parseInt(bootsStatsMap.get("healthBonus"));
            }
            if (bootsStatsMap.get("hpr") != null) {
                hpr += Integer.parseInt(bootsStatsMap.get("hpr"));
            }
            if (bootsStatsMap.get("hprPercent") != null) {
                hprPercent += Integer.parseInt(bootsStatsMap.get("hprPercent"));
            }
            if (bootsStatsMap.get("currentHealthHpr") != null) {
                currentHealthHpr += Integer.parseInt(bootsStatsMap.get("currentHealthHpr"));
            }
            if (bootsStatsMap.get("maxHealthHpr") != null) {
                maxHealthHpr += Integer.parseInt(bootsStatsMap.get("maxHealthHpr"));
            }
            if (bootsStatsMap.get("healthPercent") != null) {
                healthPercent += Integer.parseInt(bootsStatsMap.get("healthPercent"));
            }
        }


        if (maxHealth < 5) {
            maxHealth = 5;
        }

        maxHealth += maxHealth*(healthPercent/100.0);

        attributes += ",health:"+maxHealth+",walkSpeed:"+walkSpeed+",hpr:"+hpr+",maxHealthHpr:"+maxHealthHpr+",currentHealthHpr:"+currentHealthHpr+",hprPercent:"+hprPercent+",healthPercent:"+healthPercent;

        p.setMetadata("attributes", new FixedMetadataValue(Core.getInstance(), attributes));
        String s = CalculateStatsWithRange.calculate("12-30", String.valueOf(walkSpeed), true);
        p.getAttribute(Attribute.GENERIC_MOVEMENT_SPEED).setBaseValue((float)Integer.parseInt(s)/100);
    }
}
