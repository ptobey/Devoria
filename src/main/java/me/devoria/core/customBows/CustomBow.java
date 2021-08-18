package me.devoria.core.customBows;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.Arrays;

public class CustomBow {


    public ItemStack buildBow(){

        ItemStack bow = new ItemStack(Material.PINK_WOOL);
        bow.addUnsafeEnchantment(Enchantment.ARROW_FIRE,1);

        ItemMeta bowMeta = bow.getItemMeta();

        bowMeta.setDisplayName(ChatColor.WHITE + "" + "Strengthened Wooden Bow");
        bowMeta.setLore(Arrays.asList(ChatColor.DARK_GRAY + "" + ChatColor.BOLD + "Created by the great power of PTO",
                                                                                ChatColor.DARK_PURPLE + "Mythic Item"));
        bow.setItemMeta(bowMeta);

        return bow;
    }

}
