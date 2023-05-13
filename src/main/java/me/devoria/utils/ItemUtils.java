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
import org.apache.commons.lang.StringUtils;
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
        int defense = 0;
        int strength = 0;
        int spirit = 0;
        int dexterity = 0;
        int endurance = 0;

        int walkSpeed = 0;
        int maxHealth = 5;
        int hpr = 0;
        int hprPercent = 0;
        int currentHealthHpr = 0;
        int maxHealthHpr = 0;
        int healthPercent = 0;
        String damage = "0-0";
        String earthDamage = "0-0";
        String fireDamage = "0-0";
        String arcaneDamage = "0-0";
        String lightDamage = "0-0";
        String darkDamage = "0-0";
        String windDamage = "0-0";
        String thunderDamage = "0-0";
        int earthDamagePercent = 0;
        int fireDamagePercent = 0;
        int arcaneDamagePercent = 0;
        int lightDamagePercent = 0;
        int darkDamagePercent = 0;
        int windDamagePercent = 0;
        int thunderDamagePercent = 0;
        int rawMelee = 0;
        int meleePercent = 0;
        int rawSpell = 0;
        int spellPercent = 0;

        int xpBonus = 0;
        int lootChance = 0;
        int lootQuality = 0;
        int coinChance = 0;
        int coinQuality = 0;


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
            if(weaponStatsMap.get("damage") != null) {
                damage = weaponStatsMap.get("damage");
            }
            if(weaponStatsMap.get("earthDamage") != null) {
                earthDamage = weaponStatsMap.get("earthDamage");
            }
            if(weaponStatsMap.get("fireDamage") != null) {
                fireDamage = weaponStatsMap.get("fireDamage");
            }
            if(weaponStatsMap.get("arcaneDamage") != null) {
                arcaneDamage = weaponStatsMap.get("arcaneDamage");
            }
            if(weaponStatsMap.get("lightDamage") != null) {
                lightDamage = weaponStatsMap.get("lightDamage");
            }
            if(weaponStatsMap.get("darkDamage") != null) {
                darkDamage = weaponStatsMap.get("darkDamage");
            }
            if(weaponStatsMap.get("windDamage") != null) {
                windDamage = weaponStatsMap.get("windDamage");
            }
            if(weaponStatsMap.get("thunderDamage") != null) {
                thunderDamage = weaponStatsMap.get("thunderDamage");
            }

            //damage percents
            if(weaponStatsMap.get("earthDamagePercent") != null) {
                earthDamagePercent += Integer.parseInt(weaponStatsMap.get("earthDamagePercent"));
            }
            if(weaponStatsMap.get("fireDamagePercent") != null) {
                fireDamagePercent += Integer.parseInt(weaponStatsMap.get("fireDamagePercent"));
            }
            if(weaponStatsMap.get("arcaneDamagePercent") != null) {
                arcaneDamagePercent += Integer.parseInt(weaponStatsMap.get("arcaneDamagePercent"));
            }
            if(weaponStatsMap.get("lightDamagePercent") != null) {
                lightDamagePercent += Integer.parseInt(weaponStatsMap.get("lightDamagePercent"));
            }
            if(weaponStatsMap.get("darkDamagePercent") != null) {
                darkDamagePercent += Integer.parseInt(weaponStatsMap.get("darkDamagePercent"));
            }
            if(weaponStatsMap.get("windDamagePercent") != null) {
                windDamagePercent += Integer.parseInt(weaponStatsMap.get("windDamagePercent"));
            }
            if(weaponStatsMap.get("thunderDamagePercent") != null) {
                thunderDamagePercent += Integer.parseInt(weaponStatsMap.get("thunderDamagePercent"));
            }
            if(weaponStatsMap.get("rawMelee") != null) {
                rawMelee += Integer.parseInt(weaponStatsMap.get("rawMelee"));
            }
            if(weaponStatsMap.get("rawSpell") != null) {
                rawSpell += Integer.parseInt(weaponStatsMap.get("rawSpell"));
            }
            if(weaponStatsMap.get("meleePercent") != null) {
                meleePercent += Integer.parseInt(weaponStatsMap.get("meleePercent"));
            }
            if(weaponStatsMap.get("spellPercent") != null) {
                spellPercent += Integer.parseInt(weaponStatsMap.get("spellPercent"));
            }
            // next 5 stats were added xp, lc, cc, etc
            if(weaponStatsMap.get("xpBonus") != null) {
                xpBonus += Integer.parseInt(weaponStatsMap.get("xpBonus"));
            }
            if(weaponStatsMap.get("lootChance") != null) {
                lootChance += Integer.parseInt(weaponStatsMap.get("lootChance"));
            }
            if(weaponStatsMap.get("lootQuality") != null) {
                lootQuality += Integer.parseInt(weaponStatsMap.get("lootQuality"));
            }
            if(weaponStatsMap.get("coinChance") != null) {
                coinChance += Integer.parseInt(weaponStatsMap.get("coinChance"));
            }
            if(weaponStatsMap.get("coinQuality") != null) {
                coinQuality += Integer.parseInt(weaponStatsMap.get("coinQuality"));
            }
            // +elemental stats
            if(weaponStatsMap.get("strength") != null) {
                strength += Integer.parseInt(weaponStatsMap.get("strength"));
            }
            if(weaponStatsMap.get("defense") != null) {
                defense += Integer.parseInt(weaponStatsMap.get("defense"));
            }
            if(weaponStatsMap.get("spirit") != null) {
                spirit += Integer.parseInt(weaponStatsMap.get("spirit"));
            }
            if(weaponStatsMap.get("dexterity") != null) {
                dexterity += Integer.parseInt(weaponStatsMap.get("dexterity"));
            }
            if(weaponStatsMap.get("endurance") != null) {
                endurance += Integer.parseInt(weaponStatsMap.get("endurance"));
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

            //damage percents
            if(helmetStatsMap.get("earthDamagePercent") != null) {
                earthDamagePercent += Integer.parseInt(helmetStatsMap.get("earthDamagePercent"));
            }
            if(helmetStatsMap.get("fireDamagePercent") != null) {
                fireDamagePercent += Integer.parseInt(helmetStatsMap.get("fireDamagePercent"));
            }
            if(helmetStatsMap.get("arcaneDamagePercent") != null) {
                arcaneDamagePercent += Integer.parseInt(helmetStatsMap.get("arcaneDamagePercent"));
            }
            if(helmetStatsMap.get("lightDamagePercent") != null) {
                lightDamagePercent += Integer.parseInt(helmetStatsMap.get("lightDamagePercent"));
            }
            if(helmetStatsMap.get("darkDamagePercent") != null) {
                darkDamagePercent += Integer.parseInt(helmetStatsMap.get("darkDamagePercent"));
            }
            if(helmetStatsMap.get("windDamagePercent") != null) {
                windDamagePercent += Integer.parseInt(helmetStatsMap.get("windDamagePercent"));
            }
            if(helmetStatsMap.get("thunderhDamagePercent") != null) {
                thunderDamagePercent += Integer.parseInt(helmetStatsMap.get("thunderDamagePercent"));
            }
            if(helmetStatsMap.get("rawMelee") != null) {
                rawMelee += Integer.parseInt(helmetStatsMap.get("rawMelee"));
            }
            if(helmetStatsMap.get("rawSpell") != null) {
                rawSpell += Integer.parseInt(helmetStatsMap.get("rawSpell"));
            }
            if(helmetStatsMap.get("meleePercent") != null) {
                meleePercent += Integer.parseInt(helmetStatsMap.get("meleePercent"));
            }
            if(helmetStatsMap.get("spellPercent") != null) {
                spellPercent += Integer.parseInt(helmetStatsMap.get("spellPercent"));
            }
            // next 5 stats were added xp, lc, cc, etc
            if(helmetStatsMap.get("xpBonus") != null) {
                xpBonus += Integer.parseInt(helmetStatsMap.get("xpBonus"));
            }
            if(helmetStatsMap.get("lootChance") != null) {
                lootChance += Integer.parseInt(helmetStatsMap.get("lootChance"));
            }
            if(helmetStatsMap.get("lootQuality") != null) {
                lootQuality += Integer.parseInt(helmetStatsMap.get("lootQuality"));
            }
            if(helmetStatsMap.get("coinChance") != null) {
                coinChance += Integer.parseInt(helmetStatsMap.get("coinChance"));
            }
            if(helmetStatsMap.get("coinQuality") != null) {
                coinQuality += Integer.parseInt(helmetStatsMap.get("coinQuality"));
            }
            // +elemental stats
            if(helmetStatsMap.get("strength") != null) {
                strength += Integer.parseInt(helmetStatsMap.get("strength"));
            }
            if(helmetStatsMap.get("defense") != null) {
                defense += Integer.parseInt(helmetStatsMap.get("defense"));
            }
            if(helmetStatsMap.get("spirit") != null) {
                spirit += Integer.parseInt(helmetStatsMap.get("spirit"));
            }
            if(helmetStatsMap.get("dexterity") != null) {
                dexterity += Integer.parseInt(helmetStatsMap.get("dexterity"));
            }
            if(helmetStatsMap.get("endurance") != null) {
                endurance += Integer.parseInt(helmetStatsMap.get("endurance"));
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

            //damage percents
            if(chestplateStatsMap.get("earthDamagePercent") != null) {
                earthDamagePercent += Integer.parseInt(chestplateStatsMap.get("earthDamagePercent"));
            }
            if(chestplateStatsMap.get("fireDamagePercent") != null) {
                fireDamagePercent += Integer.parseInt(chestplateStatsMap.get("fireDamagePercent"));
            }
            if(chestplateStatsMap.get("arcaneDamagePercent") != null) {
                arcaneDamagePercent += Integer.parseInt(chestplateStatsMap.get("arcaneDamagePercent"));
            }
            if(chestplateStatsMap.get("lightDamagePercent") != null) {
                lightDamagePercent += Integer.parseInt(chestplateStatsMap.get("lightDamagePercent"));
            }
            if(chestplateStatsMap.get("darkDamagePercent") != null) {
                darkDamagePercent += Integer.parseInt(chestplateStatsMap.get("darkDamagePercent"));
            }
            if(chestplateStatsMap.get("windDamagePercent") != null) {
                windDamagePercent += Integer.parseInt(chestplateStatsMap.get("windDamagePercent"));
            }
            if(chestplateStatsMap.get("thunderhDamagePercent") != null) {
                thunderDamagePercent += Integer.parseInt(chestplateStatsMap.get("thunderDamagePercent"));
            }
            if(chestplateStatsMap.get("rawMelee") != null) {
                rawMelee += Integer.parseInt(chestplateStatsMap.get("rawMelee"));
            }
            if(chestplateStatsMap.get("rawSpell") != null) {
                rawSpell += Integer.parseInt(chestplateStatsMap.get("rawSpell"));
            }
            if(chestplateStatsMap.get("meleePercent") != null) {
                meleePercent += Integer.parseInt(chestplateStatsMap.get("meleePercent"));
            }
            if(chestplateStatsMap.get("spellPercent") != null) {
                spellPercent += Integer.parseInt(chestplateStatsMap.get("spellPercent"));
            }
            // next 5 stats were added xp, lc, cc, etc
            if(chestplateStatsMap.get("xpBonus") != null) {
                xpBonus += Integer.parseInt(chestplateStatsMap.get("xpBonus"));
            }
            if(chestplateStatsMap.get("lootChance") != null) {
                lootChance += Integer.parseInt(chestplateStatsMap.get("lootChance"));
            }
            if(chestplateStatsMap.get("lootQuality") != null) {
                lootQuality += Integer.parseInt(chestplateStatsMap.get("lootQuality"));
            }
            if(chestplateStatsMap.get("coinChance") != null) {
                coinChance += Integer.parseInt(chestplateStatsMap.get("coinChance"));
            }
            if(chestplateStatsMap.get("coinQuality") != null) {
                coinQuality += Integer.parseInt(chestplateStatsMap.get("coinQuality"));
            }
            // +elemental stats
            if(chestplateStatsMap.get("strength") != null) {
                strength += Integer.parseInt(chestplateStatsMap.get("strength"));
            }
            if(chestplateStatsMap.get("defense") != null) {
                defense += Integer.parseInt(chestplateStatsMap.get("defense"));
            }
            if(chestplateStatsMap.get("spirit") != null) {
                spirit += Integer.parseInt(chestplateStatsMap.get("spirit"));
            }
            if(chestplateStatsMap.get("dexterity") != null) {
                dexterity += Integer.parseInt(chestplateStatsMap.get("dexterity"));
            }
            if(chestplateStatsMap.get("endurance") != null) {
                endurance += Integer.parseInt(chestplateStatsMap.get("endurance"));
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

            //damage percents
            if(leggingsStatsMap.get("earthDamagePercent") != null) {
                earthDamagePercent += Integer.parseInt(leggingsStatsMap.get("earthDamagePercent"));
            }
            if(leggingsStatsMap.get("fireDamagePercent") != null) {
                fireDamagePercent += Integer.parseInt(leggingsStatsMap.get("fireDamagePercent"));
            }
            if(leggingsStatsMap.get("arcaneDamagePercent") != null) {
                arcaneDamagePercent += Integer.parseInt(leggingsStatsMap.get("arcaneDamagePercent"));
            }
            if(leggingsStatsMap.get("lightDamagePercent") != null) {
                lightDamagePercent += Integer.parseInt(leggingsStatsMap.get("lightDamagePercent"));
            }
            if(leggingsStatsMap.get("darkDamagePercent") != null) {
                darkDamagePercent += Integer.parseInt(leggingsStatsMap.get("darkDamagePercent"));
            }
            if(leggingsStatsMap.get("windDamagePercent") != null) {
                windDamagePercent += Integer.parseInt(leggingsStatsMap.get("windDamagePercent"));
            }
            if(leggingsStatsMap.get("thunderhDamagePercent") != null) {
                thunderDamagePercent += Integer.parseInt(leggingsStatsMap.get("thunderDamagePercent"));
            }
            if(leggingsStatsMap.get("rawMelee") != null) {
                rawMelee += Integer.parseInt(leggingsStatsMap.get("rawMelee"));
            }
            if(leggingsStatsMap.get("rawSpell") != null) {
                rawSpell += Integer.parseInt(leggingsStatsMap.get("rawSpell"));
            }
            if(leggingsStatsMap.get("meleePercent") != null) {
                meleePercent += Integer.parseInt(leggingsStatsMap.get("meleePercent"));
            }
            if(leggingsStatsMap.get("spellPercent") != null) {
                spellPercent += Integer.parseInt(leggingsStatsMap.get("spellPercent"));
            }
            // next 5 stats were added xp, lc, cc, etc
            if(leggingsStatsMap.get("xpBonus") != null) {
                xpBonus += Integer.parseInt(leggingsStatsMap.get("xpBonus"));
            }
            if(leggingsStatsMap.get("lootChance") != null) {
                lootChance += Integer.parseInt(leggingsStatsMap.get("lootChance"));
            }
            if(leggingsStatsMap.get("lootQuality") != null) {
                lootQuality += Integer.parseInt(leggingsStatsMap.get("lootQuality"));
            }
            if(leggingsStatsMap.get("coinChance") != null) {
                coinChance += Integer.parseInt(leggingsStatsMap.get("coinChance"));
            }
            if(leggingsStatsMap.get("coinQuality") != null) {
                coinQuality += Integer.parseInt(leggingsStatsMap.get("coinQuality"));
            }
            // +elemental stats
            if(leggingsStatsMap.get("strength") != null) {
                strength += Integer.parseInt(leggingsStatsMap.get("strength"));
            }
            if(leggingsStatsMap.get("defense") != null) {
                defense += Integer.parseInt(leggingsStatsMap.get("defense"));
            }
            if(leggingsStatsMap.get("spirit") != null) {
                spirit += Integer.parseInt(leggingsStatsMap.get("spirit"));
            }
            if(leggingsStatsMap.get("dexterity") != null) {
                dexterity += Integer.parseInt(leggingsStatsMap.get("dexterity"));
            }
            if(leggingsStatsMap.get("endurance") != null) {
                endurance += Integer.parseInt(leggingsStatsMap.get("endurance"));
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

            //damage percents
            if(bootsStatsMap.get("earthDamagePercent") != null) {
                earthDamagePercent += Integer.parseInt(bootsStatsMap.get("earthDamagePercent"));
            }
            if(bootsStatsMap.get("fireDamagePercent") != null) {
                fireDamagePercent += Integer.parseInt(bootsStatsMap.get("fireDamagePercent"));
            }
            if(bootsStatsMap.get("arcaneDamagePercent") != null) {
                arcaneDamagePercent += Integer.parseInt(bootsStatsMap.get("arcaneDamagePercent"));
            }
            if(bootsStatsMap.get("lightDamagePercent") != null) {
                lightDamagePercent += Integer.parseInt(bootsStatsMap.get("lightDamagePercent"));
            }
            if(bootsStatsMap.get("darkDamagePercent") != null) {
                darkDamagePercent += Integer.parseInt(bootsStatsMap.get("darkDamagePercent"));
            }
            if(bootsStatsMap.get("windDamagePercent") != null) {
                windDamagePercent += Integer.parseInt(bootsStatsMap.get("windDamagePercent"));
            }
            if(bootsStatsMap.get("thunderhDamagePercent") != null) {
                thunderDamagePercent += Integer.parseInt(bootsStatsMap.get("thunderDamagePercent"));
            }
            if(bootsStatsMap.get("rawMelee") != null) {
                rawMelee += Integer.parseInt(bootsStatsMap.get("rawMelee"));
            }
            if(bootsStatsMap.get("rawSpell") != null) {
                rawSpell += Integer.parseInt(bootsStatsMap.get("rawSpell"));
            }
            if(bootsStatsMap.get("meleePercent") != null) {
                meleePercent += Integer.parseInt(bootsStatsMap.get("meleePercent"));
            }
            if(bootsStatsMap.get("spellPercent") != null) {
                spellPercent += Integer.parseInt(bootsStatsMap.get("spellPercent"));
            }
            // next 5 stats were added xp, lc, cc, etc
            if(bootsStatsMap.get("xpBonus") != null) {
                xpBonus += Integer.parseInt(bootsStatsMap.get("xpBonus"));
            }
            if(bootsStatsMap.get("lootChance") != null) {
                lootChance += Integer.parseInt(bootsStatsMap.get("lootChance"));
            }
            if(bootsStatsMap.get("lootQuality") != null) {
                lootQuality += Integer.parseInt(bootsStatsMap.get("lootQuality"));
            }
            if(bootsStatsMap.get("coinChance") != null) {
                coinChance += Integer.parseInt(bootsStatsMap.get("coinChance"));
            }
            if(bootsStatsMap.get("coinQuality") != null) {
                coinQuality += Integer.parseInt(bootsStatsMap.get("coinQuality"));
            }
            // +elemental stats
            if(bootsStatsMap.get("strength") != null) {
                strength += Integer.parseInt(bootsStatsMap.get("strength"));
            }
            if(bootsStatsMap.get("defense") != null) {
                defense += Integer.parseInt(bootsStatsMap.get("defense"));
            }
            if(bootsStatsMap.get("spirit") != null) {
                spirit += Integer.parseInt(bootsStatsMap.get("spirit"));
            }
            if(bootsStatsMap.get("dexterity") != null) {
                dexterity += Integer.parseInt(bootsStatsMap.get("dexterity"));
            }
            if(bootsStatsMap.get("endurance") != null) {
                endurance += Integer.parseInt(bootsStatsMap.get("endurance"));
            }
        }


        if (maxHealth < 5) {
            maxHealth = 5;
        }

        maxHealth += maxHealth*(healthPercent/100.0);

        attributes += ",health:"+maxHealth+",walkSpeed:"+walkSpeed+",hpr:"+hpr+",maxHealthHpr:"+maxHealthHpr+",currentHealthHpr:"+currentHealthHpr+",hprPercent:"+hprPercent+",healthPercent:"+healthPercent+
                ",damage:"+damage+ ",earthDamage:"+earthDamage+",fireDamage:"+fireDamage+",arcaneDamage:"+arcaneDamage+",lightDamage:"+lightDamage+",darkDamage:"+darkDamage+",windDamage:"+windDamage+",thunderDamage:"+thunderDamage+
                ",earthDamagePercent:"+earthDamagePercent+",fireDamagePercent:"+fireDamagePercent+",arcaneDamagePercent:"+arcaneDamagePercent+",lightDamagePercent:"+lightDamagePercent+",darkDamagePercent:"+darkDamagePercent+",windDamagePercent:"+windDamagePercent+",thunderDamagePercent:"+thunderDamagePercent+
                ",rawMelee:"+rawMelee+",rawSpell:"+rawSpell+",meleePercent:"+meleePercent+",spellPercent:"+spellPercent+",xpBonus:"+xpBonus+",lootChance:"+lootChance+",lootQuality:"+lootQuality+",coinChance:"+coinChance+",coinQuality:"+coinQuality
                +",defense:"+defense+",strength:"+strength+",spirit:"+spirit+",dexterity:"+dexterity+",endurance:"+endurance;

        p.setMetadata("attributes", new FixedMetadataValue(Devoria.getInstance(), attributes));
        String s = ItemUtils.calculateStatsWithRange("12-30", String.valueOf(walkSpeed), "-");
        p.getAttribute(Attribute.GENERIC_MOVEMENT_SPEED).setBaseValue((float)Integer.parseInt(s)/100);
    }

    public static void updateOnlyWeaponAttributes(Player p, int slot) {
        ItemStack helmet = p.getInventory().getHelmet();
        ItemStack chestplate = p.getInventory().getChestplate();
        ItemStack leggings = p.getInventory().getLeggings();
        ItemStack boots = p.getInventory().getBoots();
        String weaponStats = null;
        String helmetStats = null;
        String chestplateStats = null;
        String leggingsStats = null;
        String bootsStats = null;


        if(helmet != null && !helmet.getItemMeta().getLocalizedName().isEmpty()) {
            helmetStats = helmet.getItemMeta().getLocalizedName();
        }
        if(chestplate != null && !chestplate.getItemMeta().getLocalizedName().isEmpty()) {
            chestplateStats = chestplate.getItemMeta().getLocalizedName();
        }
        if(leggings != null && !leggings.getItemMeta().getLocalizedName().isEmpty()) {
            leggingsStats = leggings.getItemMeta().getLocalizedName();
        }
        if(boots != null && !boots.getItemMeta().getLocalizedName().isEmpty()) {
            bootsStats = boots.getItemMeta().getLocalizedName();
        }

        try {
            if(slot == -1) {
                if(p.getInventory().getItemInMainHand().getType() != Material.AIR && !p.getInventory().getItemInMainHand().getItemMeta().getLocalizedName().isEmpty()) {

                    String stats = p.getInventory().getItemInMainHand().getItemMeta().getLocalizedName();
                    HashMap<String, String> weaponStatsMap = FastUtils.map(stats);


                    if(weaponStatsMap.get("unidentified") == null) {
                        p.getInventory().setItemInMainHand(ItemUtils.updateItem(stats));


                        String type = weaponStatsMap.get("type");

                        if (type.equals("bow") || type.equals("sword")) {
                            weaponStats = p.getInventory().getItemInMainHand().getItemMeta().getLocalizedName();

                        }
                    }
                }
            }
            else {
                if(p.getInventory().getItem(slot) != null && !p.getInventory().getItem(slot).getItemMeta().getLocalizedName().isEmpty()) {

                    String stats = p.getInventory().getItem(slot).getItemMeta().getLocalizedName();
                    HashMap<String, String> weaponStatsMap = FastUtils.map(stats);


                    if(weaponStatsMap.get("unidentified") == null) {
                        p.getInventory().setItem(slot, ItemUtils.updateItem(stats));
                    }


                    String type = weaponStatsMap.get("type");


                    if (type.equals("bow") || type.equals("sword")) {

                        weaponStats = p.getInventory().getItem(slot).getItemMeta().getLocalizedName();
                    }



                }
            }
        }
        catch(Exception ignore) {
        }
        ItemUtils.updateAttributes(p, weaponStats,helmetStats,chestplateStats,leggingsStats,bootsStats);
        PlayerUtils.changeHealth(p,0, null, false);
        PlayerUtils.updateHealthBar(p);
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
        Object arcaneDamage = attributes.get("arcane_damage");
        Object lightDamage = attributes.get("light_damage");
        Object darkDamage = attributes.get("dark_damage");
        Object windDamage = attributes.get("wind_damage");
        Object thunderDamage = attributes.get("electric_damage");
        Object rawMelee = attributes.get("raw_melee");
        Object rawSpell = attributes.get("raw_spell");
        Object earthDamagePercent = attributes.get("earth_percent");
        Object fireDamagePercent = attributes.get("fire_percent");
        Object arcaneDamagePercent = attributes.get("arcane_percent");
        Object lightDamagePercent = attributes.get("light_percent");
        Object darkDamagePercent = attributes.get("dark_percent");
        Object windDamagePercent = attributes.get("wind_percent");
        Object thunderDamagePercent = attributes.get("electric_percent");
        Object meleePercent = attributes.get("melee_percent");
        Object spellPercent = attributes.get("spell_percent");
        Object walkSpeed = attributes.get("walk_speed");
        Object healthBonus = attributes.get("health_bonus");
        Object hpr = attributes.get("hpr");
        Object hprPercent = attributes.get("hpr_percent");
        Object maxHealthHpr = attributes.get("max_health_hpr");
        Object currentHealthHpr = attributes.get("current_health_hpr");
        Object healthPercent = attributes.get("health_percent");

        Object xpBonus = attributes.get("xp_bonus");
        Object lootChance = attributes.get("loot_chance");
        Object lootQuality = attributes.get("loot_quality");
        Object coinChance = attributes.get("coin_chance");
        Object coinQuality = attributes.get("coin_quality");

        Object defense = attributes.get("defense");
        Object strength = attributes.get("strength");
        Object spirit = attributes.get("spirit");
        Object dexterity = attributes.get("dexterity");
        Object endurance = attributes.get("endurance");



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
            if (arcaneDamage != null) {
                lore.add(ChatColor.AQUA + "❆ Arcane " + ChatColor.GRAY + "Damage: " + arcaneDamage);
                itemInfo += ",arcaneDamage:" + arcaneDamage;
            }
            if (lightDamage != null) {
                lore.add(ChatColor.WHITE + "✦ Light " + ChatColor.GRAY + "Damage: " + lightDamage);
                itemInfo += ",lightDamage:" + lightDamage;
            }
            if (darkDamage != null) {
                lore.add(ChatColor.DARK_PURPLE + "✺ Darkness " + ChatColor.GRAY + "Damage: " + darkDamage);
                itemInfo += ",darkDamage:" + darkDamage;
            }
            if (windDamage != null) {
                lore.add(ChatColor.GRAY + "✺ Wind " + ChatColor.GRAY + "Damage: " + windDamage);
                itemInfo += ",windDamage:" + windDamage;
            }
            if (thunderDamage != null) {
                lore.add(ChatColor.YELLOW+ "⚡ Electric " + ChatColor.GRAY + "Damage: " + thunderDamage);
                itemInfo += ",thunderDamage:" + thunderDamage;
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

        if(defense != null || strength != null || spirit != null || dexterity != null || endurance != null) {
            lore.add("");
        }

        if(defense != null) {
            plusOrMinusFinder(defense.toString());
            lore.add(statColor+plusOrMinus+defense+ChatColor.GRAY+" Defense");

            itemInfo += ",defense:"+defense;
        }

        if(strength != null) {
            plusOrMinusFinder(strength.toString());
            lore.add(statColor+plusOrMinus+strength+ChatColor.GRAY+" Strength");

            itemInfo += ",strength:"+strength;
        }

        if(spirit != null) {
            plusOrMinusFinder(spirit.toString());
            lore.add(statColor+plusOrMinus+spirit+ChatColor.GRAY+" Spirit");

            itemInfo += ",spirit:"+spirit;
        }
        if(dexterity != null) {
            plusOrMinusFinder(dexterity.toString());
            lore.add(statColor+plusOrMinus+dexterity+ChatColor.GRAY+" Dexterity");

            itemInfo += ",dexterity:"+dexterity;
        }
        if(endurance != null) {
            plusOrMinusFinder(endurance.toString());
            lore.add(statColor+plusOrMinus+endurance+ChatColor.GRAY+" Endurance");

            itemInfo += ",endurance:"+endurance;
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

        if(earthDamagePercent != null || fireDamagePercent != null || arcaneDamagePercent != null || lightDamagePercent != null || darkDamagePercent != null || windDamagePercent != null || thunderDamagePercent != null || rawMelee != null || meleePercent != null || rawSpell != null || spellPercent != null) {
            lore.add("");
        }


        //damages here

        if(earthDamagePercent != null) {

            String earthDamagePercentPercentage;

            if(map.get("earthDamagePercentPercentage") != null) {
                earthDamagePercentPercentage = map.get("earthDamagePercentPercentage");
            }
            else {
                earthDamagePercentPercentage = MiscellaneousUtils.generatePercentage();
            }

            String calculatedEarthDamagePercent = calculateStatsWithRange(earthDamagePercent, earthDamagePercentPercentage, ",");

            plusOrMinusFinder(calculatedEarthDamagePercent);

            starsColorFinder(Integer.parseInt(earthDamagePercentPercentage));

            lore.add(statColor+plusOrMinus+calculatedEarthDamagePercent+"% "+ChatColor.GRAY+"Earth Damage"+starsColor+" ✯");
            totalPercent += Integer.parseInt(earthDamagePercentPercentage);
            numberOfStats += 1;

            itemInfo += ",earthDamagePercent:"+calculatedEarthDamagePercent+",earthDamagePercentPercentage:"+earthDamagePercentPercentage;
        }

        if(fireDamagePercent != null) {

            String fireDamagePercentPercentage;

            if(map.get("fireDamagePercentPercentage") != null) {
                fireDamagePercentPercentage = map.get("fireDamagePercentPercentage");
            }
            else {
                fireDamagePercentPercentage = MiscellaneousUtils.generatePercentage();
            }

            String calculatedFireDamagePercent = calculateStatsWithRange(fireDamagePercent, fireDamagePercentPercentage, ",");

            plusOrMinusFinder(calculatedFireDamagePercent);

            starsColorFinder(Integer.parseInt(fireDamagePercentPercentage));

            lore.add(statColor+plusOrMinus+calculatedFireDamagePercent+"% "+ChatColor.GRAY+"Fire Damage"+starsColor+" ✯");
            totalPercent += Integer.parseInt(fireDamagePercentPercentage);
            numberOfStats += 1;

            itemInfo += ",fireDamagePercent:"+calculatedFireDamagePercent+",fireDamagePercentPercentage:"+fireDamagePercentPercentage;
        }

        if(arcaneDamagePercent != null) {

            String arcaneDamagePercentPercentage;

            if(map.get("arcaneDamagePercentPercentage") != null) {
                arcaneDamagePercentPercentage = map.get("arcaneDamagePercentPercentage");
            }
            else {
                arcaneDamagePercentPercentage = MiscellaneousUtils.generatePercentage();
            }

            String calculatedArcaneDamagePercent = calculateStatsWithRange(arcaneDamagePercent, arcaneDamagePercentPercentage, ",");

            plusOrMinusFinder(calculatedArcaneDamagePercent);

            starsColorFinder(Integer.parseInt(arcaneDamagePercentPercentage));

            lore.add(statColor+plusOrMinus+calculatedArcaneDamagePercent+"% "+ChatColor.GRAY+"Arcane Damage"+starsColor+" ✯");
            totalPercent += Integer.parseInt(arcaneDamagePercentPercentage);
            numberOfStats += 1;

            itemInfo += ",arcaneDamagePercent:"+calculatedArcaneDamagePercent+",arcaneDamagePercentPercentage:"+arcaneDamagePercentPercentage;
        }

        if(lightDamagePercent != null) {

            String lightDamagePercentPercentage;

            if(map.get("lightDamagePercentPercentage") != null) {
                lightDamagePercentPercentage = map.get("lightDamagePercentPercentage");
            }
            else {
                lightDamagePercentPercentage = MiscellaneousUtils.generatePercentage();
            }

            String calculatedLightDamagePercent = calculateStatsWithRange(lightDamagePercent, lightDamagePercentPercentage, ",");

            plusOrMinusFinder(calculatedLightDamagePercent);

            starsColorFinder(Integer.parseInt(lightDamagePercentPercentage));

            lore.add(statColor+plusOrMinus+calculatedLightDamagePercent+"% "+ChatColor.GRAY+"Light Damage"+starsColor+" ✯");
            totalPercent += Integer.parseInt(lightDamagePercentPercentage);
            numberOfStats += 1;

            itemInfo += ",lightDamagePercent:"+calculatedLightDamagePercent+",lightDamagePercentPercentage:"+lightDamagePercentPercentage;
        }

        if(darkDamagePercent != null) {

            String darkDamagePercentPercentage;

            if(map.get("darkDamagePercentPercentage") != null) {
                darkDamagePercentPercentage = map.get("darkDamagePercentPercentage");
            }
            else {
                darkDamagePercentPercentage = MiscellaneousUtils.generatePercentage();
            }

            String calculatedDarkDamagePercent = calculateStatsWithRange(darkDamagePercent, darkDamagePercentPercentage, ",");

            plusOrMinusFinder(calculatedDarkDamagePercent);

            starsColorFinder(Integer.parseInt(darkDamagePercentPercentage));

            lore.add(statColor+plusOrMinus+calculatedDarkDamagePercent+"% "+ChatColor.GRAY+"Dark Damage"+starsColor+" ✯");
            totalPercent += Integer.parseInt(darkDamagePercentPercentage);
            numberOfStats += 1;

            itemInfo += ",darkDamagePercent:"+calculatedDarkDamagePercent+",darkDamagePercentPercentage:"+darkDamagePercentPercentage;
        }

        if(windDamagePercent != null) {

            String windDamagePercentPercentage;

            if(map.get("windDamagePercentPercentage") != null) {
                windDamagePercentPercentage = map.get("windDamagePercentPercentage");
            }
            else {
                windDamagePercentPercentage = MiscellaneousUtils.generatePercentage();
            }

            String calculatedWindDamagePercent = calculateStatsWithRange(windDamagePercent, windDamagePercentPercentage, ",");

            plusOrMinusFinder(calculatedWindDamagePercent);

            starsColorFinder(Integer.parseInt(windDamagePercentPercentage));

            lore.add(statColor+plusOrMinus+calculatedWindDamagePercent+"% "+ChatColor.GRAY+"Wind Damage"+starsColor+" ✯");
            totalPercent += Integer.parseInt(windDamagePercentPercentage);
            numberOfStats += 1;

            itemInfo += ",windDamagePercent:"+calculatedWindDamagePercent+",windDamagePercentPercentage:"+windDamagePercentPercentage;
        }

        if(thunderDamagePercent != null) {

            String thunderDamagePercentPercentage;

            if(map.get("thunderDamagePercentPercentage") != null) {
                thunderDamagePercentPercentage = map.get("thunderDamagePercentPercentage");
            }
            else {
                thunderDamagePercentPercentage = MiscellaneousUtils.generatePercentage();
            }

            String calculatedThunderDamagePercent = calculateStatsWithRange(thunderDamagePercent, thunderDamagePercentPercentage, ",");

            plusOrMinusFinder(calculatedThunderDamagePercent);

            starsColorFinder(Integer.parseInt(thunderDamagePercentPercentage));

            lore.add(statColor+plusOrMinus+calculatedThunderDamagePercent+"% "+ChatColor.GRAY+"Electric Damage"+starsColor+" ✯");
            totalPercent += Integer.parseInt(thunderDamagePercentPercentage);
            numberOfStats += 1;

            itemInfo += ",thunderDamagePercent:"+calculatedThunderDamagePercent+",thunderDamagePercentPercentage:"+thunderDamagePercentPercentage;
        }

        if(rawMelee != null) {

            String rawMeleePercentage;

            if(map.get("rawMeleePercentage") != null) {
                rawMeleePercentage = map.get("rawMeleePercentage");
            }
            else {
                rawMeleePercentage = MiscellaneousUtils.generatePercentage();
            }

            String calculatedRawMelee = calculateStatsWithRange(rawMelee, rawMeleePercentage, ",");

            plusOrMinusFinder(calculatedRawMelee);

            starsColorFinder(Integer.parseInt(rawMeleePercentage));

            lore.add(statColor+plusOrMinus+calculatedRawMelee+" "+ChatColor.GRAY+"Melee Damage"+starsColor+" ✯");
            totalPercent += Integer.parseInt(rawMeleePercentage);
            numberOfStats += 1;

            itemInfo += ",rawMelee:"+calculatedRawMelee+",rawMeleePercentage:"+rawMeleePercentage;
        }

        if(meleePercent != null) {

            String meleePercentPercentage;

            if(map.get("meleePercentPercentage") != null) {
                meleePercentPercentage = map.get("meleePercentPercentage");
            }
            else {
                meleePercentPercentage = MiscellaneousUtils.generatePercentage();
            }

            String calculatedMeleePercent = calculateStatsWithRange(meleePercent, meleePercentPercentage, ",");

            plusOrMinusFinder(calculatedMeleePercent);

            starsColorFinder(Integer.parseInt(meleePercentPercentage));

            lore.add(statColor+plusOrMinus+calculatedMeleePercent+"% "+ChatColor.GRAY+"Melee Damage"+starsColor+" ✯");
            totalPercent += Integer.parseInt(meleePercentPercentage);
            numberOfStats += 1;

            itemInfo += ",meleePercent:"+calculatedMeleePercent+",meleePercentPercentage:"+meleePercentPercentage;
        }

        if(rawSpell != null) {

            String rawSpellPercentage;

            if(map.get("rawSpellPercentage") != null) {
                rawSpellPercentage = map.get("rawSpellPercentage");
            }
            else {
                rawSpellPercentage = MiscellaneousUtils.generatePercentage();
            }

            String calculatedRawSpell = calculateStatsWithRange(rawSpell, rawSpellPercentage, ",");

            plusOrMinusFinder(calculatedRawSpell);

            starsColorFinder(Integer.parseInt(rawSpellPercentage));

            lore.add(statColor+plusOrMinus+calculatedRawSpell+" "+ChatColor.GRAY+"Spell Damage"+starsColor+" ✯");
            totalPercent += Integer.parseInt(rawSpellPercentage);
            numberOfStats += 1;

            itemInfo += ",rawSpell:"+calculatedRawSpell+",rawSpellPercentage:"+rawSpellPercentage;
        }

        if(spellPercent != null) {

            String spellPercentPercentage;

            if(map.get("spellPercentPercentage") != null) {
                spellPercentPercentage = map.get("spellPercentPercentage");
            }
            else {
                spellPercentPercentage = MiscellaneousUtils.generatePercentage();
            }

            String calculatedSpellPercent = calculateStatsWithRange(spellPercent, spellPercentPercentage, ",");

            plusOrMinusFinder(calculatedSpellPercent);

            starsColorFinder(Integer.parseInt(spellPercentPercentage));

            lore.add(statColor+plusOrMinus+calculatedSpellPercent+"% "+ChatColor.GRAY+"Spell Damage"+starsColor+" ✯");
            totalPercent += Integer.parseInt(spellPercentPercentage);
            numberOfStats += 1;

            itemInfo += ",spellPercent:"+calculatedSpellPercent+",spellPercentPercentage:"+spellPercentPercentage;
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
                walkSpeedPercentage = MiscellaneousUtils.generatePercentage();
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
                healthBonusPercentage = MiscellaneousUtils.generatePercentage();
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
                hprPercentage = MiscellaneousUtils.generatePercentage();
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
                hprPercentPercentage = MiscellaneousUtils.generatePercentage();
            }

            String calculatedHprPercent = calculateStatsWithRange(hprPercent, hprPercentPercentage, ",");

            plusOrMinusFinder(calculatedHprPercent);

            starsColorFinder(Integer.parseInt(hprPercentPercentage));

            lore.add(statColor+plusOrMinus+calculatedHprPercent+"% "+ChatColor.GRAY+"Health Regen"+starsColor+" ✯");
            totalPercent += Integer.parseInt(hprPercentPercentage);
            numberOfStats += 1;

            itemInfo += ",hprPercent:"+calculatedHprPercent+",hprPercentPercentage:"+hprPercentPercentage;
        }

        if(xpBonus != null || lootChance != null || lootQuality != null || coinChance != null || coinQuality != null) {
            lore.add("");
        }
        //Xp bonus
        if(xpBonus != null) {

            String xpBonusPercentage;

            if(map.get("xpBonusPercentage") != null) {
                xpBonusPercentage = map.get("xpBonusPercentage");
            }
            else {
                xpBonusPercentage = MiscellaneousUtils.generatePercentage();
            }

            String calculatedXpBonusPercent = calculateStatsWithRange(xpBonus, xpBonusPercentage, ",");

            plusOrMinusFinder(calculatedXpBonusPercent);

            starsColorFinder(Integer.parseInt(xpBonusPercentage));

            lore.add(statColor+plusOrMinus+calculatedXpBonusPercent+"% "+ChatColor.GRAY+"XP Bonus"+starsColor+" ✯");
            totalPercent += Integer.parseInt(xpBonusPercentage);
            numberOfStats += 1;

            itemInfo += ",xpBonus:"+calculatedXpBonusPercent+",xpBonusPercentage:"+xpBonusPercentage;
        }

        //Loot chance
        if(lootChance != null) {

            String lootChancePercentage;

            if(map.get("lootChancePercentage") != null) {
                lootChancePercentage = map.get("lootChancePercentage");
            }
            else {
                lootChancePercentage = MiscellaneousUtils.generatePercentage();
            }

            String calculatedLootChancePercent = calculateStatsWithRange(lootChance, lootChancePercentage, ",");

            plusOrMinusFinder(calculatedLootChancePercent);

            starsColorFinder(Integer.parseInt(lootChancePercentage));

            lore.add(statColor+plusOrMinus+calculatedLootChancePercent+"% "+ChatColor.GRAY+"Loot Chance"+starsColor+" ✯");
            totalPercent += Integer.parseInt(lootChancePercentage);
            numberOfStats += 1;

            itemInfo += ",lootChance:"+calculatedLootChancePercent+",lootChancePercentage:"+lootChancePercentage;
        }

        //Loot quality
        if(lootQuality != null) {

            String lootQualityPercentage;

            if(map.get("lootQualityPercentage") != null) {
                lootQualityPercentage = map.get("lootQualityPercentage");
            }
            else {
                lootQualityPercentage = MiscellaneousUtils.generatePercentage();
            }

            String calculatedLootQualityPercent = calculateStatsWithRange(lootQuality, lootQualityPercentage, ",");

            plusOrMinusFinder(calculatedLootQualityPercent);

            starsColorFinder(Integer.parseInt(lootQualityPercentage));

            lore.add(statColor+plusOrMinus+calculatedLootQualityPercent+"% "+ChatColor.GRAY+"Loot Quality"+starsColor+" ✯");
            totalPercent += Integer.parseInt(lootQualityPercentage);
            numberOfStats += 1;

            itemInfo += ",lootQuality:"+calculatedLootQualityPercent+",lootQualityPercentage:"+lootQualityPercentage;
        }

        //Coin chance
        if(coinChance != null) {

            String coinChancePercentage;

            if(map.get("coinChancePercentage") != null) {
                coinChancePercentage = map.get("coinChancePercentage");
            }
            else {
                coinChancePercentage = MiscellaneousUtils.generatePercentage();
            }

            String calculatedCoinChancePercent = calculateStatsWithRange(coinChance, coinChancePercentage, ",");

            plusOrMinusFinder(calculatedCoinChancePercent);

            starsColorFinder(Integer.parseInt(coinChancePercentage));

            lore.add(statColor+plusOrMinus+calculatedCoinChancePercent+"% "+ChatColor.GRAY+"Coin Chance"+starsColor+" ✯");
            totalPercent += Integer.parseInt(coinChancePercentage);
            numberOfStats += 1;

            itemInfo += ",coinChance:"+calculatedCoinChancePercent+",coinChancePercentage:"+coinChancePercentage;
        }

        //Coin quality
        if(coinQuality != null) {

            String coinQualityPercentage;

            if(map.get("coinQualityPercentage") != null) {
                coinQualityPercentage = map.get("coinQualityPercentage");
            }
            else {
                coinQualityPercentage = MiscellaneousUtils.generatePercentage();
            }

            String calculatedCoinQualityPercent = calculateStatsWithRange(coinQuality, coinQualityPercentage, ",");

            plusOrMinusFinder(calculatedCoinQualityPercent);

            starsColorFinder(Integer.parseInt(coinQualityPercentage));

            lore.add(statColor+plusOrMinus+calculatedCoinQualityPercent+"% "+ChatColor.GRAY+"Coin Quality"+starsColor+" ✯");
            totalPercent += Integer.parseInt(coinQualityPercentage);
            numberOfStats += 1;

            itemInfo += ",coinQuality:"+calculatedCoinQualityPercent+",coinQualityPercentage:"+coinQualityPercentage;
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
        lore.add(rarityColor+ StringUtils.capitalize(String.valueOf(rarity))+" "+ StringUtils.capitalize(String.valueOf(type)));

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


    public static ArrayList<String> getDamage(String damagerStats, String victimStats, boolean isSpell) {

        HashMap<String,String> damagerMap = new HashMap<>();
        HashMap<String,String> victimMap = new HashMap<>();

        ArrayList<String> damages = new ArrayList<>();

        int normalDamage;
        int earthDamage;
        int fireDamage;
        int arcaneDamage;
        int lightDamage;
        int darkDamage;
        int windDamage;
        int thunderDamage;
        int rawDamage;
        float earthDamagePercent;
        float fireDamagePercent;
        float arcaneDamagePercent;
        float lightDamagePercent;
        float darkDamagePercent;
        float windDamagePercent;
        float thunderDamagePercent;
        float damagePercent;
        float strengthPercent;
        float defensePercent;


        String[] seperatedDamagerStats = damagerStats.split(",");

        for(int i=1;i<seperatedDamagerStats.length;i++){
            String[] damagerArr = seperatedDamagerStats[i].split(":");
            damagerMap.put(damagerArr[0], damagerArr[1]);
        }

        String[] seperatedVictimStats = victimStats.split(",");

        for(int i=1;i<seperatedVictimStats.length;i++){
            String[] victimArr = seperatedVictimStats[i].split(":");
            victimMap.put(victimArr[0], victimArr[1]);
        }

        String nd = damagerMap.get("damage");
        String ed = damagerMap.get("earthDamage");
        String fd = damagerMap.get("fireDamage");
        String ad = damagerMap.get("arcaneDamage");
        String ld = damagerMap.get("lightDamage");
        String dd = damagerMap.get("darkDamage");
        String wd = damagerMap.get("windDamage");
        String td = damagerMap.get("thunderDamage");
        String rmd = damagerMap.get("rawMelee");
        String rsd = damagerMap.get("rawSpell");
        String edp = damagerMap.get("earthDamagePercent");
        String fdp = damagerMap.get("fireDamagePercent");
        String adp = damagerMap.get("arcaneDamagePercent");
        String ldp = damagerMap.get("lightDamagePercent");
        String ddp = damagerMap.get("darkDamagePercent");
        String wdp = damagerMap.get("windDamagePercent");
        String tdp = damagerMap.get("thunderDamagePercent");
        String sdp = damagerMap.get("spellPercent");
        String mdp = damagerMap.get("meleePercent");
        String str = damagerMap.get("strength");
        String def = victimMap.get("defense");


        String generatedPercentage = MiscellaneousUtils.generatePercentage();


        if(nd == null)
            normalDamage = 0;
        else {
            normalDamage = Integer.parseInt(ItemUtils.calculateStatsWithRange(damagerMap.get("damage"), generatedPercentage, "-"));
        }

        generatedPercentage = MiscellaneousUtils.generatePercentage();


        if(ed == null)
            earthDamage = 0;
        else {
            earthDamage = Integer.parseInt(ItemUtils.calculateStatsWithRange(damagerMap.get("earthDamage"), generatedPercentage, "-"));
        }

        generatedPercentage = MiscellaneousUtils.generatePercentage();

        if(fd == null)
            fireDamage = 0;
        else {
            fireDamage = Integer.parseInt(ItemUtils.calculateStatsWithRange(damagerMap.get("fireDamage"), generatedPercentage, "-"));
        }

        generatedPercentage = MiscellaneousUtils.generatePercentage();

        if(ad == null)
            arcaneDamage = 0;
        else {
            arcaneDamage = Integer.parseInt(ItemUtils.calculateStatsWithRange(damagerMap.get("arcaneDamage"), generatedPercentage, "-"));
        }

        generatedPercentage = MiscellaneousUtils.generatePercentage();

        if(ld == null)
            lightDamage = 0;
        else {
            lightDamage = Integer.parseInt(ItemUtils.calculateStatsWithRange(damagerMap.get("lightDamage"), generatedPercentage, "-"));
        }

        generatedPercentage = MiscellaneousUtils.generatePercentage();

        if(dd == null)
            darkDamage = 0;
        else {
            darkDamage = Integer.parseInt(ItemUtils.calculateStatsWithRange(damagerMap.get("darkDamage"), generatedPercentage, "-"));
        }

        generatedPercentage = MiscellaneousUtils.generatePercentage();

        if(wd == null)
            windDamage = 0;
        else {
            windDamage = Integer.parseInt(ItemUtils.calculateStatsWithRange(damagerMap.get("windDamage"), generatedPercentage, "-"));
        }

        generatedPercentage = MiscellaneousUtils.generatePercentage();

        if(td == null)
            thunderDamage = 0;
        else {
            thunderDamage = Integer.parseInt(ItemUtils.calculateStatsWithRange(damagerMap.get("thunderDamage"), generatedPercentage, "-"));
        }

        if(edp == null)
            earthDamagePercent = 1;
        else {
            earthDamagePercent = (Float.parseFloat(damagerMap.get("earthDamagePercent")) + 100) / 100;
        }

        if(fdp == null)
            fireDamagePercent = 1;
        else {
            fireDamagePercent = (Float.parseFloat(damagerMap.get("fireDamagePercent")) + 100) / 100;
        }


        if(adp == null)
            arcaneDamagePercent = 1;
        else {
            arcaneDamagePercent = (Float.parseFloat(damagerMap.get("arcaneDamagePercent")) + 100) / 100;
        }


        if(ldp == null)
            lightDamagePercent = 1;
        else {
            lightDamagePercent = (Float.parseFloat(damagerMap.get("lightDamagePercent")) + 100) / 100;
        }

        if(ddp == null)
            darkDamagePercent = 1;
        else {
            darkDamagePercent =(Float.parseFloat(damagerMap.get("darkDamagePercent")) + 100) / 100;
        }

        if(wdp == null)
            windDamagePercent = 1;
        else {
            windDamagePercent = (Float.parseFloat(damagerMap.get("windDamagePercent")) + 100) / 100;
        }

        if(tdp == null)
            thunderDamagePercent = 1;
        else {
            thunderDamagePercent = (Float.parseFloat(damagerMap.get("thunderDamagePercent")) + 100) / 100;
        }

        if(str == null)
            strengthPercent = 1;
        else {
            strengthPercent = (((Float.parseFloat(damagerMap.get("strength")) / 2) + 100) / 100);
            if(strengthPercent < 1) {
                strengthPercent = 1;
            }
            else if(strengthPercent > 1.75) {
                strengthPercent = 1.75F;
            }
        }

        if(def == null) {
            defensePercent = 1;
        }
        else {
            defensePercent = (((Float.parseFloat(victimMap.get("defense")) / 2) + 100) / 100);
            if(defensePercent < 1) {
                defensePercent = 1;
            }
            else if(defensePercent > 1.75) {
                defensePercent = 1.75F;
            }
        }

        if(!isSpell) {

            if (rmd == null) {
                rawDamage = 0;
            }
            else {
                rawDamage = Integer.parseInt(damagerMap.get("rawMelee"));
            }

            if(mdp == null) {
                damagePercent = 1;
            }
            else {
                damagePercent = (Float.parseFloat(damagerMap.get("meleePercent")) + 100) / 100;
            }
        }

        else {

            if (rsd == null) {
                rawDamage = 0;
            }
            else {
                rawDamage = Integer.parseInt(damagerMap.get("rawSpell"));
            }

            if(sdp == null) {
                damagePercent = 1;
            }
            else {
                damagePercent = (Float.parseFloat(damagerMap.get("spellPercent")) + 100) / 100;
            }
        }


        normalDamage = (int) ((normalDamage + rawDamage) * damagePercent * ((strengthPercent-defensePercent)+1));

        earthDamage = (int) (earthDamage * earthDamagePercent * damagePercent * ((strengthPercent-defensePercent)+1));

        fireDamage = (int) (fireDamage * fireDamagePercent * damagePercent * ((strengthPercent-defensePercent)+1));

        arcaneDamage = (int) (arcaneDamage * arcaneDamagePercent * damagePercent * ((strengthPercent-defensePercent)+1));

        lightDamage = (int) (lightDamage * lightDamagePercent * damagePercent * ((strengthPercent-defensePercent)+1));

        darkDamage = (int) (darkDamage * darkDamagePercent * damagePercent * ((strengthPercent-defensePercent)+1));

        windDamage = (int) (windDamage * windDamagePercent * damagePercent * ((strengthPercent-defensePercent)+1));

        thunderDamage = (int) (thunderDamage * thunderDamagePercent * damagePercent * ((strengthPercent-defensePercent)+1));


        int totalDamage = (normalDamage+earthDamage+fireDamage+arcaneDamage+lightDamage+darkDamage+windDamage+thunderDamage);

        damages.add(String.valueOf(normalDamage));
        damages.add(String.valueOf(earthDamage));
        damages.add(String.valueOf(fireDamage));
        damages.add(String.valueOf(arcaneDamage));
        damages.add(String.valueOf(lightDamage));
        damages.add(String.valueOf(darkDamage));
        damages.add(String.valueOf(windDamage));
        damages.add(String.valueOf(thunderDamage));
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
