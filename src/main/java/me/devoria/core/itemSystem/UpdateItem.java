package me.devoria.core.itemSystem;

import me.devoria.core.WeightedPercentageGenerator;
import org.apache.commons.lang.StringUtils;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class UpdateItem {

    public static ChatColor starsColor = ChatColor.WHITE;
    public static ChatColor statColor = ChatColor.WHITE;
    public static String plusOrMinus = "";


    public static void starsColorFinder(int percentNumber) {


        if (percentNumber <= 0) {

            starsColor = ChatColor.DARK_GRAY;
        } else if (percentNumber < 10) {

            starsColor = ChatColor.DARK_RED;
        } else if (percentNumber < 30) {

            starsColor = ChatColor.RED;
        } else if (percentNumber < 70) {

            starsColor = ChatColor.YELLOW;
        } else if (percentNumber < 90) {

            starsColor = ChatColor.GREEN;
        } else if (percentNumber < 100) {

            starsColor = ChatColor.DARK_GREEN;
        } else {
            starsColor = ChatColor.AQUA;
        }
    }

        public static void plusOrMinusFinder(String stat){

            if (Integer.parseInt(stat) < 0) {
                statColor = ChatColor.RED;
                plusOrMinus = "";
            } else if (Integer.parseInt(stat) > 0) {
                statColor = ChatColor.GREEN;
                plusOrMinus = "+";
            } else {
                statColor = ChatColor.WHITE;
                plusOrMinus = " ";
            }
        }




    //makes a custom item using stats pulled from the yml
    public static ItemStack update(String itemData) throws FileNotFoundException {

        HashMap<String,String> map = new HashMap<>();

        ChatColor rarityColor = ChatColor.WHITE;
        ChatColor attackSpeedColor = ChatColor.WHITE;
        ChatColor attackRangeColor = ChatColor.WHITE;

        String stars = "";
        String attackSpeedType = "Normal";
        String attackRangeType = "Normal";
        float totalPercent = 0;
        float numberOfStats = 0;

        String[] separatedStats = itemData.split(",");

        for(int i=1;i<separatedStats.length;i++){
            String[] arr = separatedStats[i].split(":");
            map.put(arr[0], arr[1]);
        }

        Map<String, Object> attributes = FindItemFile.parse(map.get("fileName"));

        Object fileName = attributes.get("file_name");
        Object type = attributes.get("type");
        Object name = attributes.get("name");
        Object health = attributes.get("health");
        Object tradeable = attributes.get("tradeable");
        Object rarity = attributes.get("rarity");
        Object attackSpeed = attributes.get("attack_speed");
        Object attackRange = attributes.get("attack_range");
        Object runeSlots = attributes.get("rune_slots");
        Object damage = attributes.get("damage");
        Object earthDamage = attributes.get("earth_damage");
        Object fireDamage = attributes.get("fire_damage");
        Object waterDamage = attributes.get("water_damage");
        Object lightDamage = attributes.get("light_damage");
        Object darkDamage = attributes.get("dark_damage");
        Object walkSpeed = attributes.get("walk_speed");



        
        String itemInfo = ",fileName:"+fileName+",type:"+type+",name:"+name+",tradeable:"+tradeable+",rarity:"+rarity;




        ArrayList<String> lore = new ArrayList<>();

        // Rarity conversion

        if(Objects.equals(rarity, "common")) {
            rarityColor = ChatColor.WHITE;

        }
        else if(Objects.equals(rarity, "rare")) {
            rarityColor = ChatColor.BLUE;

        }
        else if(Objects.equals(rarity, "epic")) {
            rarityColor = ChatColor.GREEN;

        }
        else if(Objects.equals(rarity, "legendary")) {
            rarityColor = ChatColor.GOLD;

        }
        else if(Objects.equals(rarity, "mythic")) {
            rarityColor = ChatColor.DARK_PURPLE;

        }

        // Attack speed conversion

        if(attackSpeed == null) {
            attackSpeedColor = ChatColor.WHITE;
            attackSpeedType = "Normal";
        }
        else if((Integer) attackSpeed == -3) {
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

        //Attack range conversion

        if(attackRange == null) {
            attackRangeColor = ChatColor.WHITE;
            attackRangeType = "Normal";
        }
        else if((Integer) attackRange == -3) {
            attackRangeColor = ChatColor.RED;
            attackRangeType = "Super Short";
        }
        else if((Integer) attackRange == -2) {
            attackRangeColor = ChatColor.RED;
            attackRangeType = "Very Short";
        }
        else if((Integer) attackRange == -1) {
            attackRangeColor = ChatColor.RED;
            attackRangeType = "Short";
        }
        else if((Integer) attackRange ==  0) {
            attackRangeColor = ChatColor.WHITE;
            attackRangeType = "Normal";
        }
        else if((Integer) attackRange == 1) {
            attackRangeColor = ChatColor.GREEN;
            attackRangeType = "Long";
        }
        else if((Integer) attackRange == 2) {
            attackRangeColor = ChatColor.GREEN;
            attackRangeType = "Very Long";
        }
        else if((Integer) attackRange == 3) {
            attackRangeColor = ChatColor.GREEN;
            attackRangeType = "Super Long";
        }

        // Rune slots conversion

        if(runeSlots == null) {
            runeSlots = 0;
        }

        // Adds initial lore

        lore.add("");

        if(type.equals("bow") || type.equals("sword")) {

            itemInfo += ",attackRange:"+attackRange+",attackSpeed:"+attackSpeed;

            if (damage != null) {
                lore.add(ChatColor.GOLD + "✸ Damage: " + damage);
                itemInfo += ",damage:" + damage;
            }
            if (earthDamage != null) {
                lore.add(ChatColor.DARK_GREEN + "✿ Earth " + ChatColor.GRAY + "Damage: " + earthDamage);
                itemInfo += ",earthDamage:" + earthDamage;
            }
            if (fireDamage != null) {
                lore.add(ChatColor.DARK_RED + "✹ Fire " + ChatColor.GRAY + "Damage: " + fireDamage);
                itemInfo += ",fireDamage:" + fireDamage;
            }
            if (waterDamage != null) {
                lore.add(ChatColor.AQUA + "❆ Water " + ChatColor.GRAY + "Damage: " + waterDamage);
                itemInfo += ",waterDamage:" + waterDamage;
            }
            if (lightDamage != null) {
                lore.add(ChatColor.YELLOW + "✦ Light " + ChatColor.GRAY + "Damage: " + lightDamage);
                itemInfo += ",lightDamage:" + lightDamage;
            }
            if (darkDamage != null) {
                lore.add(ChatColor.DARK_PURPLE + "✺ Darkness " + ChatColor.GRAY + "Damage: " + darkDamage);
                itemInfo += ",darkDamage:" + darkDamage;
            }
            lore.add("");
            lore.add(ChatColor.GRAY + "   Attack Speed: " + attackSpeedColor + attackSpeedType);
            lore.add(ChatColor.GRAY + "   Attack Range: " + attackRangeColor + attackRangeType);
            lore.add(ChatColor.GRAY + "   Rune Slots: [" + ChatColor.WHITE + "0/" + runeSlots + ChatColor.GRAY + "]");
        }
        else if (type.equals("helmet") || type.equals("chestplate") || type.equals("leggings") || type.equals("boots")) {

            if (health != null) {
                lore.add(ChatColor.DARK_RED + "❤ Health: +"+ health);
                itemInfo += ",health:" + health;
                lore.add("");
                lore.add(ChatColor.GRAY + "   Rune Slots: [" + ChatColor.WHITE + "0/" + runeSlots + ChatColor.GRAY + "]");
            }
        }


        if(walkSpeed != null) {
            lore.add("");
        }

        //Walk Speed
        if(walkSpeed != null) {

            String walkSpeedPercentage;

            if(map.get("walkSpeedPercentage") != null) {
                walkSpeedPercentage = map.get("walkSpeedPercentage");
            }
            else {
                walkSpeedPercentage = WeightedPercentageGenerator.generate();
            }

            String calculatedWalkSpeed = CalculateStatsWithRange.calculate(walkSpeed, walkSpeedPercentage);

            plusOrMinusFinder(calculatedWalkSpeed);

            starsColorFinder(Integer.parseInt(walkSpeedPercentage));

            lore.add(statColor+plusOrMinus+calculatedWalkSpeed+"% "+ChatColor.GRAY+"Walk Speed"+starsColor+" ✯");
            totalPercent += Integer.parseInt(walkSpeedPercentage);
            numberOfStats += 1;

            itemInfo += ",walkSpeed:"+calculatedWalkSpeed+",walkSpeedPercentage:"+walkSpeedPercentage;
        }



        int itemPercentage = Math.round(totalPercent/numberOfStats);


        if(Objects.equals(rarity, "common")) {
            stars = "";
        }
        else if (itemPercentage <= 0)  {
            stars = " ✯";
            starsColor = ChatColor.DARK_GRAY;
        }
        else if (itemPercentage < 10)  {
            stars = " ✯";
            starsColor = ChatColor.DARK_RED;
        }
        else if (itemPercentage < 30)  {
            stars = " ✯✯";
            starsColor = ChatColor.RED;
        }
        else if (itemPercentage < 70)  {
            stars = " ✯✯✯";
            starsColor = ChatColor.YELLOW;
        }
        else if (itemPercentage < 90)  {
            stars = " ✯✯✯✯";
            starsColor = ChatColor.GREEN;
        }
        else if (itemPercentage < 100)  {
            stars = " ✯✯✯✯✯";
            starsColor = ChatColor.DARK_GREEN;
        }
        else {
            stars = " ✯✯✯✯✯";
            starsColor = ChatColor.AQUA;
        }


        lore.add("");
        lore.add(rarityColor+StringUtils.capitalize(String.valueOf(rarity))+" "+StringUtils.capitalize(String.valueOf(type)));

        Material item = Material.STICK;

        if(type.equals("bow")) {
            item = Material.PINK_WOOL;
        }
        else if(type.equals("sword")) {
            item = Material.DIAMOND_SWORD;
        }
        else if(type.equals("helmet")) {
            item = Material.DIAMOND_HELMET;
        }
        else if(type.equals("chestplate")) {
            item = Material.DIAMOND_CHESTPLATE;
        }
        else if(type.equals("leggings")) {
            item = Material.DIAMOND_LEGGINGS;
        }
        else if(type.equals("boots")) {
            item = Material.DIAMOND_BOOTS;
        }

        ItemStack weapon = new ItemStack(item);
        ItemMeta weaponMeta = weapon.getItemMeta();

        weaponMeta.setDisplayName(rarityColor +"" +name+starsColor+stars);
        weaponMeta.setLore(lore);


        weaponMeta.setLocalizedName(itemInfo);
        weapon.setItemMeta(weaponMeta);

        return weapon;
    }

}