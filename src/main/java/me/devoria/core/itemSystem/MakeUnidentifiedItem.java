package me.devoria.core.itemSystem;

import me.devoria.core.WeightedPercentageGenerator;
import org.apache.commons.lang.StringUtils;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.util.StringUtil;

import java.util.ArrayList;
import java.util.Objects;

public class MakeUnidentifiedItem {

    //makes a custom bow using stats pulled from the yml
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