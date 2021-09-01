package me.devoria.core.itemSystem;

import me.devoria.core.WeightedPercentageGenerator;
import net.kyori.adventure.text.TextComponent;
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

public class UpdateWeapon {

    public static ItemStack update(String itemData) throws FileNotFoundException {

        HashMap<String,String> map = new HashMap<>();
        String[] separatedStats = itemData.split(",");

        for(int i=1;i<separatedStats.length;i++){
            String[] arr = separatedStats[i].split(":");
            map.put(arr[0], arr[1]);
        }

        Map<String, Object> attributes = FindItemFile.parse(map.get("fileName"));
        return updateWeapon(itemData, attributes.get("file_name"), attributes.get("name"), attributes.get("tradeable"), attributes.get("rarity"), attributes.get("attack_speed"), attributes.get("attack_range"), attributes.get("rune_slots"), attributes.get("damage"), attributes.get("earth_damage"), attributes.get("fire_damage"), attributes.get("water_damage"), attributes.get("light_damage"), attributes.get("dark_damage"));
    }

    //makes a custom bow using stats pulled from the yml
    public static ItemStack updateWeapon(String itemData, Object fileName, Object name, Object tradeable, Object rarity, Object attackSpeed, Object attackRange, Object runeSlots, Object damage, Object earthDamage, Object fireDamage, Object waterDamage, Object lightDamage, Object darkDamage){

        HashMap<String,String> map = new HashMap<>();

        ChatColor rarityColor = ChatColor.WHITE;
        ChatColor attackSpeedColor = ChatColor.WHITE;
        ChatColor attackRangeColor = ChatColor.WHITE;
        ChatColor statColor;

        String stars = "";
        ChatColor starsColor = ChatColor.WHITE;
        String attackSpeedType = "Normal";
        String attackRangeType = "Normal";
        String plusOrMinus;
        float totalPercent = 0;
        float numberOfStats = 0;

        String[] seperatedStats = itemData.split(",");

        for(int i=1;i<seperatedStats.length;i++){
            String[] arr = seperatedStats[i].split(":");
            map.put(arr[0], arr[1]);
        }
        
        String itemInfo = ",fileName:"+fileName+",name:"+name+",tradeable:"+tradeable+",rarity:"+rarity+",attackSpeed:"+attackSpeed+",damage:"+damage;


        ItemStack weapon = new ItemStack(Material.PINK_WOOL);
        ItemMeta weaponMeta = weapon.getItemMeta();
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
        lore.add(ChatColor.GOLD+"✸ Damage: "+damage);
        lore.add(ChatColor.GRAY+"   Attack Speed: "+attackSpeedColor+attackSpeedType);
        lore.add(ChatColor.GRAY+"   Attack Range: "+attackRangeColor+attackRangeType);
        lore.add(ChatColor.GRAY+"   Rune Slots: ["+ChatColor.WHITE+"0/"+runeSlots+ChatColor.GRAY+"]");

        if(earthDamage != null || fireDamage != null || waterDamage != null || lightDamage != null || darkDamage != null) {
            lore.add("");
        }

        //Earth Damage
        if(earthDamage != null) {

            String earthDamagePercentage;
            int earthPercentNumber;

            if(map.get("earthDamagePercentage") != null) {
                earthDamagePercentage = map.get("earthDamagePercentage");
            }
            else {
                earthDamagePercentage = WeightedPercentageGenerator.generate();
            }

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

            earthPercentNumber = Integer.parseInt(earthDamagePercentage);

            if (earthPercentNumber <= 30)  {

                starsColor = ChatColor.DARK_RED;
            }
            else if (earthPercentNumber <= 55)  {

                starsColor = ChatColor.RED;
            }
            else if (earthPercentNumber <= 80)  {

                starsColor = ChatColor.YELLOW;
            }
            else if (earthPercentNumber <= 94)  {

                starsColor = ChatColor.GREEN;
            }
            else {
                starsColor = ChatColor.AQUA;
            }

            lore.add(ChatColor.DARK_GREEN+"✿ Earth Damage: "+statColor+plusOrMinus+calculatedEarthDamage+starsColor+" ✯");
            totalPercent += Integer.parseInt(earthDamagePercentage);
            numberOfStats += 1;

            itemInfo += ",earthDamage:"+calculatedEarthDamage+",earthDamagePercentage:"+earthDamagePercentage;
        }

        //Fire Damage
        if(fireDamage != null) {

            String fireDamagePercentage;
            int firePercentNumber;

            if(map.get("fireDamagePercentage") != null) {
                fireDamagePercentage = map.get("fireDamagePercentage");
            }
            else {
                fireDamagePercentage = WeightedPercentageGenerator.generate();
            }

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


            firePercentNumber = Integer.parseInt(fireDamagePercentage);

            if (firePercentNumber <= 30)  {

                starsColor = ChatColor.DARK_RED;
            }
            else if (firePercentNumber <= 55)  {

                starsColor = ChatColor.RED;
            }
            else if (firePercentNumber <= 80)  {

                starsColor = ChatColor.YELLOW;
            }
            else if (firePercentNumber <= 94)  {

                starsColor = ChatColor.GREEN;
            }
            else {
                starsColor = ChatColor.AQUA;
            }


            lore.add(ChatColor.DARK_RED+"✹ Fire Damage: "+statColor+plusOrMinus+calculatedFireDamage+starsColor+" ✯");
            totalPercent += Integer.parseInt(fireDamagePercentage);
            numberOfStats += 1;

            itemInfo += ",fireDamage:"+calculatedFireDamage+",fireDamagePercentage:"+fireDamagePercentage;
        }

        //Water Damage
        if(waterDamage != null) {


            String waterDamagePercentage;
            int waterPercentNumber;

            if(map.get("waterDamagePercentage") != null) {
                waterDamagePercentage = map.get("waterDamagePercentage");
            }
            else {
                waterDamagePercentage = WeightedPercentageGenerator.generate();
            }

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

            waterPercentNumber = Integer.parseInt(waterDamagePercentage);

            if (waterPercentNumber <= 30)  {

                starsColor = ChatColor.DARK_RED;
            }
            else if (waterPercentNumber <= 55)  {

                starsColor = ChatColor.RED;
            }
            else if (waterPercentNumber <= 80)  {

                starsColor = ChatColor.YELLOW;
            }
            else if (waterPercentNumber <= 94)  {

                starsColor = ChatColor.GREEN;
            }
            else {
                starsColor = ChatColor.AQUA;
            }

            lore.add(ChatColor.AQUA+"❆ Water Damage: "+statColor+plusOrMinus+calculatedWaterDamage+starsColor+" ✯");
            totalPercent += Integer.parseInt(waterDamagePercentage);
            numberOfStats += 1;

            itemInfo += ",waterDamage:"+calculatedWaterDamage+",waterDamagePercentage:"+waterDamagePercentage;
        }

        //Light Damage
        if(lightDamage != null) {


            String lightDamagePercentage;
            int lightPercentNumber;

            if(map.get("lightDamagePercentage") != null) {
                lightDamagePercentage = map.get("lightDamagePercentage");
            }
            else {
                lightDamagePercentage = WeightedPercentageGenerator.generate();
            }

            lightPercentNumber = Integer.parseInt(lightDamagePercentage);

            if (lightPercentNumber <= 30)  {

                starsColor = ChatColor.DARK_RED;
            }
            else if (lightPercentNumber <= 55)  {

                starsColor = ChatColor.RED;
            }
            else if (lightPercentNumber <= 80)  {

                starsColor = ChatColor.YELLOW;
            }
            else if (lightPercentNumber <= 94)  {

                starsColor = ChatColor.GREEN;
            }
            else {
                starsColor = ChatColor.AQUA;
            }

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
            lore.add(ChatColor.YELLOW+"✦ Light Damage: "+statColor+plusOrMinus+calculatedLightDamage+starsColor+" ✯");
            totalPercent += Integer.parseInt(lightDamagePercentage);
            numberOfStats += 1;

            itemInfo += ",lightDamage:"+calculatedLightDamage+",lightDamagePercentage:"+lightDamagePercentage;
        }

        //Dark Damage
        if(darkDamage != null) {

            String darkDamagePercentage;
            int darkPercentNumber;

            if(map.get("darkDamagePercentage") != null) {
                darkDamagePercentage = map.get("darkDamagePercentage");
            }
            else {
                darkDamagePercentage = WeightedPercentageGenerator.generate();
            }

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

            darkPercentNumber = Integer.parseInt(darkDamagePercentage);

            if (darkPercentNumber <= 30)  {

                starsColor = ChatColor.DARK_RED;
            }
            else if (darkPercentNumber <= 55)  {

                starsColor = ChatColor.RED;
            }
            else if (darkPercentNumber <= 80)  {

                starsColor = ChatColor.YELLOW;
            }
            else if (darkPercentNumber <= 94)  {

                starsColor = ChatColor.GREEN;
            }
            else {
                starsColor = ChatColor.AQUA;
            }
            lore
                    .add(ChatColor.DARK_GRAY+"✺ Dark Damage: "+statColor+plusOrMinus+calculatedDarkDamage+starsColor+" ✯");
            totalPercent += Integer.parseInt(darkDamagePercentage);
            numberOfStats += 1;

            itemInfo += ",darkDamage:"+calculatedDarkDamage+",darkDamagePercentage:"+darkDamagePercentage;
        }

        int itemPercentage = Math.round(totalPercent/numberOfStats);


        if(Objects.equals(rarity, "common")) {
            stars = "";
        }
        else if (itemPercentage <= 30)  {
            stars = " ✯";
            starsColor = ChatColor.DARK_RED;
        }
        else if (itemPercentage <= 55)  {
            stars = " ✯✯";
            starsColor = ChatColor.RED;
        }
        else if (itemPercentage <= 80)  {
            stars = " ✯✯✯";
            starsColor = ChatColor.YELLOW;
        }
        else if (itemPercentage <= 94)  {
            stars = " ✯✯✯✯";
            starsColor = ChatColor.GREEN;
        }
        else {
            stars = " ✯✯✯✯✯";
            starsColor = ChatColor.AQUA;
        }


        lore.add("");
        lore.add(rarityColor+StringUtils.capitalize(String.valueOf(rarity))+" Quality");


        weaponMeta.setDisplayName(rarityColor +"" +name+starsColor+stars);
        weaponMeta.setLore(lore);


        weaponMeta.setLocalizedName(itemInfo);
        weapon.setItemMeta(weaponMeta);

        return weapon;
    }

}