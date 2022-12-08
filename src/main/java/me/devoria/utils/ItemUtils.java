package me.devoria.utils;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.EnumSet;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Random;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import me.devoria.Devoria;
import org.apache.commons.lang3.StringUtils;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.attribute.Attribute;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.metadata.FixedMetadataValue;
import org.yaml.snakeyaml.Yaml;

public class ItemUtils {
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

    public static final Set<Material> weapons = EnumSet.of(
            Material.WOODEN_AXE,
            Material.STONE_AXE,
            Material.GOLDEN_AXE,
            Material.IRON_AXE,
            Material.DIAMOND_AXE,
            Material.NETHERITE_AXE,
            Material.WOODEN_SWORD,
            Material.STONE_SWORD,
            Material.GOLDEN_SWORD,
            Material.IRON_SWORD,
            Material.DIAMOND_SWORD,
            Material.NETHERITE_SWORD,
            Material.WOODEN_SHOVEL,
            Material.STONE_SHOVEL,
            Material.GOLDEN_SHOVEL,
            Material.IRON_SHOVEL,
            Material.DIAMOND_SHOVEL,
            Material.NETHERITE_SHOVEL,
            Material.WOODEN_HOE,
            Material.STONE_HOE,
            Material.GOLDEN_HOE,
            Material.IRON_HOE,
            Material.DIAMOND_HOE,
            Material.NETHERITE_HOE,
            Material.SHEARS
    );

    public static ItemStack getItem(ItemStack item, String name, String ... lore) {
        ItemMeta meta = item.getItemMeta();

        meta.setDisplayName(ChatColor.translateAlternateColorCodes('&', name));

        List<String> lores = new ArrayList<>();
        for (String s : lore) {
            lores.add(ChatColor.translateAlternateColorCodes('&', s));
        }

        meta.setLore(lores);

        item.setItemMeta(meta);

        return item;
    }

    public static void updateAttributes(Player p, String weaponItemData, String helmetItemData, String chestplateItemData, String leggingsItemData, String bootsItemData) {

        String attributes = "";
        int walkSpeed = 0;
        int maxHealth = 5;
        int hpr = 0;
        int hprPercent = 0;
        int currentHealthHpr = 0;
        int maxHealthHpr = 0;
        int healthPercent = 0;


        if(weaponItemData != null) {

            HashMap<String,String> weaponStatsMap = FastUtils.map(weaponItemData);

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

            HashMap<String,String> helmetStatsMap = FastUtils.map(helmetItemData);

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

            HashMap<String,String> chestplateStatsMap = FastUtils.map(chestplateItemData);

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

            HashMap<String,String> leggingsStatsMap = FastUtils.map(leggingsItemData);

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

            HashMap<String,String> bootsStatsMap = FastUtils.map(bootsItemData);

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

        p.setMetadata("attributes", new FixedMetadataValue(Devoria.getInstance(), attributes));
        String s = ItemUtils.calculateStatsWithRange("12-30", String.valueOf(walkSpeed), "-");
        p.getAttribute(Attribute.GENERIC_MOVEMENT_SPEED).setBaseValue((float)Integer.parseInt(s)/100);
    }

    //makes a custom item using stats pulled from the yml
    public static ItemStack updateItem(String itemData) throws FileNotFoundException {
        ChatColor rarityColor = ChatColor.WHITE;
        ChatColor attackSpeedColor = ChatColor.WHITE;
        ChatColor attackRangeColor = ChatColor.WHITE;

        String stars = "";
        String attackSpeedType = "Normal";
        String attackRangeType = "Normal";
        float totalPercent = 0;
        float numberOfStats = 0;

        HashMap<String,String> map = FastUtils.map(itemData);

        Map<String, String> attributes = parseItemFile("items",map.get("fileName"));

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
        Object healthBonus = attributes.get("health_bonus");
        Object hpr = attributes.get("hpr");
        Object hprPercent = attributes.get("hpr_percent");
        Object maxHealthHpr = attributes.get("max_health_hpr");
        Object currentHealthHpr = attributes.get("current_health_hpr");
        Object healthPercent = attributes.get("health_percent");




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
            rarityColor = ChatColor.LIGHT_PURPLE;

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

        if(currentHealthHpr != null || maxHealthHpr != null || healthPercent != null) {
            lore.add("");
        }

        //Current Health Hpr
        if(currentHealthHpr != null) {
            plusOrMinusFinder(currentHealthHpr.toString());
            lore.add(statColor+plusOrMinus+currentHealthHpr+"% "+ChatColor.GRAY+"of Current Health Regen");

            itemInfo += ",currentHealthHpr:"+currentHealthHpr;
        }

        //Max Health Hpr
        if(maxHealthHpr != null) {

            plusOrMinusFinder(maxHealthHpr.toString());
            lore.add(statColor+plusOrMinus+maxHealthHpr+"% "+ChatColor.GRAY+"of Max Health Regen");

            itemInfo += ",maxHealthHpr:"+maxHealthHpr;
        }

        //Health Percent
        if(healthPercent != null) {

            plusOrMinusFinder(healthPercent.toString());
            lore.add(statColor+plusOrMinus+healthPercent+"% "+ChatColor.GRAY+"Health Bonus");

            itemInfo += ",healthPercent:"+healthPercent;
        }

        if(walkSpeed != null || hpr != null|| hprPercent != null) {
            lore.add("");
        }

        //Walk Speed
        if(walkSpeed != null) {

            String walkSpeedPercentage;

            if(map.get("walkSpeedPercentage") != null) {
                walkSpeedPercentage = map.get("walkSpeedPercentage");
            }
            else {
                walkSpeedPercentage = MiscellaneousUtils.generateWeightedPercentage();
            }

            String calculatedWalkSpeed = calculateStatsWithRange(walkSpeed, walkSpeedPercentage, ",");

            plusOrMinusFinder(calculatedWalkSpeed);

            starsColorFinder(Integer.parseInt(walkSpeedPercentage));

            lore.add(statColor+plusOrMinus+calculatedWalkSpeed+"% "+ChatColor.GRAY+"Walk Speed"+starsColor+" ✯");
            totalPercent += Integer.parseInt(walkSpeedPercentage);
            numberOfStats += 1;

            itemInfo += ",walkSpeed:"+calculatedWalkSpeed+",walkSpeedPercentage:"+walkSpeedPercentage;
        }

        //Health Bonus
        if(healthBonus != null) {

            String healthBonusPercentage;

            if(map.get("healthBonusPercentage") != null) {
                healthBonusPercentage = map.get("healthBonusPercentage");
            }
            else {
                healthBonusPercentage = MiscellaneousUtils.generateWeightedPercentage();
            }

            String calculatedHealthBonus = calculateStatsWithRange(healthBonus, healthBonusPercentage, ",");

            plusOrMinusFinder(calculatedHealthBonus);

            starsColorFinder(Integer.parseInt(healthBonusPercentage));

            lore.add(statColor+plusOrMinus+calculatedHealthBonus+" "+ChatColor.GRAY+"Health Bonus"+starsColor+" ✯");
            totalPercent += Integer.parseInt(healthBonusPercentage);
            numberOfStats += 1;

            itemInfo += ",healthBonus:"+calculatedHealthBonus+",healthBonusPercentage:"+healthBonusPercentage;
        }

        //Raw Hpr
        if(hpr != null) {

            String hprPercentage;

            if(map.get("hprPercentage") != null) {
                hprPercentage = map.get("hprPercentage");
            }
            else {
                hprPercentage = MiscellaneousUtils.generateWeightedPercentage();
            }

            String calculatedHpr = calculateStatsWithRange(hpr, hprPercentage, ",");

            plusOrMinusFinder(calculatedHpr);

            starsColorFinder(Integer.parseInt(hprPercentage));

            lore.add(statColor+plusOrMinus+calculatedHpr+" "+ChatColor.GRAY+"Health Regen"+starsColor+" ✯");
            totalPercent += Integer.parseInt(hprPercentage);
            numberOfStats += 1;

            itemInfo += ",hpr:"+calculatedHpr+",hprPercentage:"+hprPercentage;
        }

        //Hpr Percent
        if(hprPercent != null) {

            String hprPercentPercentage;

            if(map.get("hprPercentPercentage") != null) {
                hprPercentPercentage = map.get("hprPercentPercentage");
            }
            else {
                hprPercentPercentage = MiscellaneousUtils.generateWeightedPercentage();
            }

            String calculatedHprPercent = calculateStatsWithRange(hprPercent, hprPercentPercentage, ",");

            plusOrMinusFinder(calculatedHprPercent);

            starsColorFinder(Integer.parseInt(hprPercentPercentage));

            lore.add(statColor+plusOrMinus+calculatedHprPercent+"% "+ChatColor.GRAY+"Health Regen"+starsColor+" ✯");
            totalPercent += Integer.parseInt(hprPercentPercentage);
            numberOfStats += 1;

            itemInfo += ",hprPercent:"+calculatedHprPercent+",hprPercentPercentage:"+hprPercentPercentage;
        }




        float itemPercentage = totalPercent/numberOfStats;



        if(numberOfStats == 0) {
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
        lore.add(rarityColor+ StringUtils.capitalize(String.valueOf(rarity))+" "+StringUtils.capitalize(String.valueOf(type)));

        Material item = Material.STICK;

        if(type.equals("bow")) {
            item = Material.BOW;
        }
        else if(type.equals("sword")) {
            item = Material.WOODEN_SWORD;
        }
        else if(type.equals("helmet")) {
            item = Material.LEATHER_HELMET;
        }
        else if(type.equals("chestplate")) {
            item = Material.LEATHER_CHESTPLATE;
        }
        else if(type.equals("leggings")) {
            item = Material.LEATHER_LEGGINGS;
        }
        else if(type.equals("boots")) {
            item = Material.LEATHER_BOOTS;
        }

        ItemStack weapon = new ItemStack(item);
        ItemMeta weaponMeta = weapon.getItemMeta();

        weaponMeta.setDisplayName(rarityColor +"" +name+starsColor+stars);
        weaponMeta.setLore(lore);


        weaponMeta.setLocalizedName(itemInfo);
        weapon.setItemMeta(weaponMeta);

        return weapon;
    }

    public static String calculateStatsWithRange(Object range, String percentage, String splitter) {
        String[] splitRange = String.valueOf(range).split(splitter);
        int min = Integer.parseInt(splitRange[0]);
        int max = Integer.parseInt(splitRange[1]);

        double eachPercent = ((max - min) / 100.0);
        double newStat = min + eachPercent * Integer.parseInt(percentage);
        return String.valueOf((int) Math.round(newStat));
    }

    public static ArrayList<String> getItemDamage(String itemData) {

        HashMap<String,String> map = new HashMap<>();
        ArrayList<String> damages = new ArrayList<>();

        int normalDamage;
        int earthDamage;
        int fireDamage;
        int waterDamage;
        int lightDamage;
        int darkDamage;


        String[] seperatedStats = itemData.split(",");

        for(int i=1;i<seperatedStats.length;i++){
            String[] arr = seperatedStats[i].split(":");
            map.put(arr[0], arr[1]);
        }

        String nd = map.get("damage");
        String ed = map.get("earthDamage");
        String fd = map.get("fireDamage");
        String wd = map.get("waterDamage");
        String ld = map.get("lightDamage");
        String dd = map.get("darkDamage");


        String damagePercentage = MiscellaneousUtils.generateWeightedPercentage();


        if(nd == null)
            normalDamage = 0;
        else {
            normalDamage = Integer.parseInt(ItemUtils.calculateStatsWithRange(map.get("damage"), damagePercentage, "-"));
        }

        damagePercentage = MiscellaneousUtils.generateWeightedPercentage();


        if(ed == null)
            earthDamage = 0;
        else {
            earthDamage = Integer.parseInt(ItemUtils.calculateStatsWithRange(map.get("earthDamage"), damagePercentage, "-"));
        }

        damagePercentage = MiscellaneousUtils.generateWeightedPercentage();

        if(fd == null)
            fireDamage = 0;
        else {
            fireDamage = Integer.parseInt(ItemUtils.calculateStatsWithRange(map.get("fireDamage"), damagePercentage, "-"));
        }

        damagePercentage = MiscellaneousUtils.generateWeightedPercentage();

        if(wd == null)
            waterDamage = 0;
        else {
            waterDamage = Integer.parseInt(ItemUtils.calculateStatsWithRange(map.get("waterDamage"), damagePercentage, "-"));
        }

        damagePercentage = MiscellaneousUtils.generateWeightedPercentage();

        if(ld == null)
            lightDamage = 0;
        else {
            lightDamage = Integer.parseInt(ItemUtils.calculateStatsWithRange(map.get("lightDamage"), damagePercentage, "-"));
        }

        damagePercentage = MiscellaneousUtils.generateWeightedPercentage();

        if(dd == null)
            darkDamage = 0;
        else {
            darkDamage = Integer.parseInt(ItemUtils.calculateStatsWithRange(map.get("darkDamage"), damagePercentage, "-"));
        }



        //Delete total damage later
        int totalDamage = normalDamage+earthDamage+fireDamage+waterDamage+lightDamage+darkDamage;

        damages.add(String.valueOf(normalDamage));
        damages.add(String.valueOf(earthDamage));
        damages.add(String.valueOf(fireDamage));
        damages.add(String.valueOf(waterDamage));
        damages.add(String.valueOf(lightDamage));
        damages.add(String.valueOf(darkDamage));
        damages.add(String.valueOf(totalDamage));

        return damages;
    }

    public static Map<String, String> parseItemFile(String folder, String name) throws FileNotFoundException {
        FileInputStream inputStream = new FileInputStream(Devoria.dataFolder+"/"+folder+"/"+name+".yml");
        Yaml yaml = new Yaml();
        return yaml.load(inputStream);
    }

    public static Collection<ItemStack> generate(String type, String level) throws FileNotFoundException {

        String dataFolder = Bukkit.getPluginManager().getPlugin("Devoria").getDataFolder().toString();

        Collection<ItemStack> items = new ArrayList<>();


        List<String> subPathList = null;
        String rarity;
        int randomNumber;

        Random randomNumbers = new Random();

        randomNumber = randomNumbers.ints(0, 101).findAny().getAsInt();

        if(randomNumber >= 50) {


            randomNumber = randomNumbers.ints(0, 101).findAny().getAsInt();

            if (randomNumber <= 5) {
                rarity = "mythic";
            } else if (randomNumber <= 15) {
                rarity = "legendary";
            } else if (randomNumber <= 30) {
                rarity = "epic";
            } else if (randomNumber <= 60) {
                rarity = "rare";
            } else {
                rarity = "common";
            }


            Path path = Paths.get(dataFolder + "/loot/15-20/" + rarity);

            System.out.println(path);

            try (Stream<Path> subPaths = Files.walk(path, 1)) {

                subPathList = subPaths.filter(Files::isRegularFile).map(Object::toString).collect(Collectors.toList());


            } catch (IOException e) {
                e.printStackTrace();
            }


            assert subPathList != null;
            Collections.shuffle(subPathList);


            try {
                FileInputStream inputStream = new FileInputStream(subPathList.get(0));
                Yaml yaml = new Yaml();
                ItemStack drop;
                Map<String, Object> attributes = yaml.load(inputStream);

                if (attributes.get("rarity").equals("common")) {
                    drop = ItemUtils.updateItem(",fileName:"+attributes.get("file_name"));
                } else {
                    drop = makeUnidentifiedItem(attributes.get("file_name"), attributes.get("rarity"), attributes.get("type"), attributes.get("level"));
                }

                items.add(drop);
            } catch (NullPointerException e) {
                e.printStackTrace();
            }
        }
        randomNumber = randomNumbers.ints(1, 7).findAny().getAsInt();
        for(int i = 0; i < randomNumber; i++) {
            ItemStack coin = new ItemStack(Material.EMERALD);
            items.add(coin);
        }

        return items;
    }

    public static ItemStack makeUnidentifiedItem(Object fileName, Object rarity, Object type, Object level){

        String itemInfo = ",unidentified:1,fileName:"+fileName+",level:"+level;

        ChatColor rarityColor = ChatColor.WHITE;
        Material material = Material.WHITE_WOOL;



        if(Objects.equals(rarity, "rare")) {
            rarityColor = ChatColor.BLUE;
            material = Material.LIGHT_BLUE_WOOL;
        }
        else if(Objects.equals(rarity, "epic")) {
            rarityColor = ChatColor.GREEN;
            material = Material.LIME_WOOL;
        }
        else if(Objects.equals(rarity, "legendary")) {
            rarityColor = ChatColor.GOLD;
            material = Material.YELLOW_WOOL;
        }
        else if(Objects.equals(rarity, "mythic")) {
            rarityColor = ChatColor.DARK_PURPLE;
            material = Material.PURPLE_WOOL;
        }

        ItemStack bow = new ItemStack(material);
        ItemMeta bowMeta = bow.getItemMeta();


        bowMeta.setDisplayName(rarityColor+"Unidentified "+StringUtils.capitalize(String.valueOf(rarity))+ " " + StringUtils.capitalize(String.valueOf(type)));
        bowMeta.setLocalizedName(itemInfo);

        bow.setItemMeta(bowMeta);

        return bow;
    }
}
