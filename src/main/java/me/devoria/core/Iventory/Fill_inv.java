package me.devoria.core.Iventory;

import me.devoria.core.DataBase.Item_Stack;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.inventory.ItemStack;

public class Fill_inv {


    public static void fill_huntsman(org.bukkit.entity.Player sender) {



        Inventory Huntsman_inventory = Bukkit.createInventory(null, 45, ChatColor.BLACK + "Huntsman inventory!");


        sender.openInventory(Huntsman_inventory);



    }

    public static void fill_sorcerer(org.bukkit.entity.Player sender) {




        Inventory sorcerer_inventory = Bukkit.createInventory(null, 45, ChatColor.BLACK + "Sorcerer inventory!");
        sender.openInventory(sorcerer_inventory);




    }

    public static void fill__bard(org.bukkit.entity.Player sender) {


        InventoryHolder owner = (InventoryHolder) sender;
        Inventory bard_inventory = Bukkit.createInventory(owner, InventoryType.PLAYER, ChatColor.BLACK + "Bard inventory!");
        //String bard_inv = Item_Stack.getItemStack(sender.getUniqueId(),"bard");
        //bard_inventory.setContents(bard_inv);
        //sender.openInventory(bard_inventory);



    }

    public static void fill_knight(org.bukkit.entity.Player sender) {



        Inventory knight_inventory = Bukkit.createInventory(null, 45, ChatColor.BLACK + "Knight inventory!");


        sender.openInventory(knight_inventory);



    }




}
