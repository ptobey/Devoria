package me.devoria.core.customBows;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.Objects;

public class MakeBow {

//makes a custom bow using stats pulled from the yml
    public static ItemStack makeBow(Object name, Object tradeable, Object rarity, Object attackSpeed, Object damage, Object earthDamage){

        ChatColor rarityColor = ChatColor.WHITE;
        ChatColor attackSpeedColor = ChatColor.WHITE;

        String rarityType = "";
        String attackSpeedType = "";
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

        if(earthDamage != null) {
            lore.add(ChatColor.DARK_GREEN+"✿ Earth Damage: "+ChatColor.GREEN+earthDamage);
            itemInfo += ",earthDamage:"+earthDamage;
        }
        lore.add("");
        lore.add(rarityColor+"✯ "+rarityType+" Quality");

        bowMeta.setLore(lore);

        bowMeta.setLocalizedName(itemInfo);
        bow.setItemMeta(bowMeta);

        return bow;
    }

}
