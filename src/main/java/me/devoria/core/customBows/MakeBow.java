package me.devoria.core.customBows;

import me.devoria.core.CalculateStatsWithRange;
import me.devoria.core.WeightedPercentageGenerator;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.Objects;

public class MakeBow {

//makes a custom bow using stats pulled from the yml
    public static ItemStack makeBow(Object name, Object tradeable, Object rarity, Object attackSpeed, Object attackRange, Object runeSlots, Object damage, Object earthDamage, Object fireDamage, Object waterDamage, Object lightDamage, Object darkDamage){

        ChatColor rarityColor = ChatColor.WHITE;
        ChatColor attackSpeedColor = ChatColor.WHITE;

        String rarityType = "";
        String attackSpeedType = "";
        String plusOrMinus = "";
        ChatColor statColor = ChatColor.WHITE;


        String itemInfo = "name:"+name+",tradeable:"+tradeable+",rarity:"+rarity+",attackSpeed:"+attackSpeed+",damage:"+damage;


        ItemStack bow = new ItemStack(Material.PINK_WOOL);
        ItemMeta bowMeta = bow.getItemMeta();
        ArrayList<String> lore = new ArrayList<>();

        if(Objects.equals(rarity, "common")) {
            rarityColor = ChatColor.WHITE;
            rarityType = "Common";
        }
        else if(Objects.equals(rarity, "rare")) {
            rarityColor = ChatColor.BLUE;
            rarityType = "Rare";
        }
        else if(Objects.equals(rarity, "epic")) {
            rarityColor = ChatColor.GREEN;
            rarityType = "Epic";
        }
        else if(Objects.equals(rarity, "legendary")) {
            rarityColor = ChatColor.GOLD;
            rarityType = "Legendary";
        }
        else if(Objects.equals(rarity, "mythic")) {
            rarityColor = ChatColor.DARK_PURPLE;
            rarityType = "Mythic";
        }
        if((Integer) attackSpeed == -3) {
            attackSpeedColor = ChatColor.RED;
            attackSpeedType = "Super Slow";
        }
        else if((Integer) attackSpeed == -2) {
            attackSpeedColor = ChatColor.RED;
            attackSpeedType = "Very Slow";
        }
        else if((Integer) attackSpeed == -1) {
            attackSpeedColor = ChatColor.RED;
            attackSpeedType = "Slow";
        }
        else if(attackSpeed == (Integer) 0) {
            attackSpeedColor = ChatColor.WHITE;
            attackSpeedType = "Normal";
        }
        else if((Integer) attackSpeed == 1) {
            attackSpeedColor = ChatColor.GREEN;
            attackSpeedType = "Fast";
        }
        else if((Integer) attackSpeed == 2) {
            attackSpeedColor = ChatColor.GREEN;
            attackSpeedType = "Very Fast";
        }
        else if((Integer) attackSpeed == 3) {
            attackSpeedColor = ChatColor.GREEN;
            attackSpeedType = "Super Fast";
        }


        assert bowMeta != null;
        bowMeta.setDisplayName(rarityColor + "" + name);

        lore.add("");
        lore.add(ChatColor.GOLD+"✸ Damage: "+damage);
        lore.add(ChatColor.GRAY+"   Attack Speed: "+attackSpeedColor+attackSpeedType);
        lore.add("");

        //Earth Damage
        if(earthDamage != null) {

            String earthDamagePercentage = WeightedPercentageGenerator.generate();
            String calculatedEarthDamage = CalculateStatsWithRange.calculate(earthDamage, earthDamagePercentage);

            if(Integer.parseInt(calculatedEarthDamage) < 0) {
                statColor = ChatColor.RED;
                plusOrMinus = "";
            }
            else if(Integer.parseInt(calculatedEarthDamage) > 0) {
                statColor = ChatColor.GREEN;
                plusOrMinus = "+";
            }
            else {
                statColor = ChatColor.WHITE;
                plusOrMinus = "";
            }
            lore.add(ChatColor.DARK_GREEN+"✿ Earth Damage: "+statColor+plusOrMinus+calculatedEarthDamage+" ["+earthDamagePercentage+"%]");
            itemInfo += ",earthDamage:"+earthDamage;
        }

        //Fire Damage
        if(fireDamage != null) {

            String fireDamagePercentage = WeightedPercentageGenerator.generate();
            String calculatedFireDamage = CalculateStatsWithRange.calculate(fireDamage, fireDamagePercentage);

            if(Integer.parseInt(calculatedFireDamage) < 0) {
                statColor = ChatColor.RED;
                plusOrMinus = "";
            }
            else if(Integer.parseInt(calculatedFireDamage) > 0) {
                statColor = ChatColor.GREEN;
                plusOrMinus = "+";
            }
            else {
                statColor = ChatColor.WHITE;
                plusOrMinus = "";
            }
            lore.add(ChatColor.DARK_RED+"✹ Fire Damage: "+statColor+plusOrMinus+calculatedFireDamage+" ["+fireDamagePercentage+"%]");
            itemInfo += ",fireDamage:"+calculatedFireDamage;
        }

        //Water Damage
        if(waterDamage != null) {

            String waterDamagePercentage = WeightedPercentageGenerator.generate();
            String calculatedWaterDamage = CalculateStatsWithRange.calculate(waterDamage, waterDamagePercentage);

            if(Integer.parseInt(calculatedWaterDamage) < 0) {
                statColor = ChatColor.RED;
                plusOrMinus = "";
            }
            else if(Integer.parseInt(calculatedWaterDamage) > 0) {
                statColor = ChatColor.GREEN;
                plusOrMinus = "+";
            }
            else {
                statColor = ChatColor.WHITE;
                plusOrMinus = "";
            }
            lore.add(ChatColor.AQUA+"❆ Water Damage: "+statColor+plusOrMinus+calculatedWaterDamage+" ["+waterDamagePercentage+"%]");
            itemInfo += ",waterDamage:"+calculatedWaterDamage;
        }

        //Light Damage
        if(lightDamage != null) {

            String lightDamagePercentage = WeightedPercentageGenerator.generate();
            String calculatedLightDamage = CalculateStatsWithRange.calculate(lightDamage, lightDamagePercentage);

            if(Integer.parseInt(calculatedLightDamage) < 0) {
                statColor = ChatColor.RED;
                plusOrMinus = "";
            }
            else if(Integer.parseInt(calculatedLightDamage) > 0) {
                statColor = ChatColor.GREEN;
                plusOrMinus = "+";
            }
            else {
                statColor = ChatColor.WHITE;
                plusOrMinus = "";
            }
            lore.add(ChatColor.YELLOW+"✦ Light Damage: "+statColor+plusOrMinus+calculatedLightDamage+" ["+lightDamagePercentage+"%]");
            itemInfo += ",lightDamage:"+calculatedLightDamage;
        }

        //Dark Damage
        if(darkDamage != null) {

            String darkDamagePercentage = WeightedPercentageGenerator.generate();
            String calculatedDarkDamage = CalculateStatsWithRange.calculate(darkDamage, darkDamagePercentage);

            if(Integer.parseInt(calculatedDarkDamage) < 0) {
                statColor = ChatColor.RED;
                plusOrMinus = "";
            }
            else if(Integer.parseInt(calculatedDarkDamage) > 0) {
                statColor = ChatColor.GREEN;
                plusOrMinus = "+";
            }
            else {
                statColor = ChatColor.WHITE;
                plusOrMinus = "";
            }
            lore.add(ChatColor.DARK_GRAY+"✺ Dark Damage: "+statColor+plusOrMinus+calculatedDarkDamage+" ["+darkDamagePercentage+"%]");
            itemInfo += ",darkDamage:"+calculatedDarkDamage;
        }







        lore.add("");
        lore.add(rarityColor+"✯ "+rarityType+" Quality");

        bowMeta.setLore(lore);

        bowMeta.setLocalizedName(itemInfo);
        bow.setItemMeta(bowMeta);

        return bow;
    }

}
