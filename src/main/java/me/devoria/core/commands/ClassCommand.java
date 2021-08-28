package me.devoria.core.commands;

import me.devoria.core.Core;
import me.devoria.core.DataBase.ClassTable;
import me.devoria.core.DataBase.Item_Stack;
import me.devoria.core.DataBase.LocationSv;
import me.devoria.core.Iventory.SerializeInventory;
import me.devoria.core.Listeners;
import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;


import java.sql.SQLException;
import java.util.Locale;
import java.util.UUID;

public class ClassCommand implements CommandExecutor {

    //Sets your class
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (sender instanceof Player) {
            Player player = (Player) sender;
            Core.getClassSelectGUI().openInventory(player);
            PlayerInventory playerInventory = player.getInventory();
            String itemStack = SerializeInventory.playerInventoryToBase64(playerInventory);
            UUID uuid = ((Player) sender).getUniqueId();
            String current_class = Listeners.lookUpPlayer(((Player) sender).getUniqueId()).getType();
            String c_class = ClassTable.FindCurrentClass(uuid);
            ItemStack[] items ={};

            // saving class location
            String world = ((Player) sender).getLocation().getWorld().getName();
            Integer block_x = ((Player) sender).getLocation().getBlockX();
            Integer block_y = ((Player) sender).getLocation().getBlockY() + 1;
            Integer block_z = ((Player) sender).getLocation().getBlockZ();

            String class_location = world + "|" + block_x.toString() + "|" + block_y.toString() + "|" + block_z.toString();


            switch (args[0].toLowerCase(Locale.ROOT)) {
                case "huntsman":

                    try {
                        Item_Stack.updateItemStack(uuid,current_class,itemStack);
                        LocationSv.SetClassLoc(uuid,class_location,current_class);
                    } catch (SQLException throwables) {
                        Bukkit.getLogger().info(throwables.toString());
                        Bukkit.getLogger().info("Could not update");
                    }
                    sender.sendMessage("You picked the Huntsman class!");
                    Listeners.lookUpPlayer(((Player) sender).getUniqueId()).setType("huntsman");
                    sender.sendMessage("Your past class is->" + c_class);
                    try {
                        ClassTable.SetCurrentClass(uuid,"huntsman");
                    } catch (SQLException throwables) {
                        Bukkit.getLogger().info(throwables.toString());
                        Bukkit.getLogger().info("Could not set class hunt on db");

                    }
                    ((Player) sender).getInventory().clear();
                    items = Item_Stack.getItemStack(((Player) sender).getUniqueId(),"huntsman");

                    ((Player) sender).getInventory().setContents(items);



                    return true;
                case "sorcerer":

                    try {
                        Item_Stack.updateItemStack(uuid,current_class,itemStack);
                        LocationSv.SetClassLoc(uuid,class_location,current_class);
                    } catch (SQLException throwables) {
                        Bukkit.getLogger().info(throwables.toString());
                        Bukkit.getLogger().info("Could not update");
                    }

                    sender.sendMessage("You picked the sorcerer class!");
                    Listeners.lookUpPlayer(((Player) sender).getUniqueId()).setType("sorcerer");
                    sender.sendMessage("Your past class is->" + c_class);
                    try {
                        ClassTable.SetCurrentClass(uuid,"sorcerer");
                    } catch (SQLException throwables) {
                        Bukkit.getLogger().info(throwables.toString());
                        Bukkit.getLogger().info("Could not set class sorcerer on db");
                    }
                    ((Player) sender).getInventory().clear();
                    items = Item_Stack.getItemStack(((Player) sender).getUniqueId(),"sorcerer");
                    ((Player) sender).getInventory().setContents(items);
                    return true;
                case "bard":
                    try {
                        Item_Stack.updateItemStack(uuid,current_class,itemStack);
                        LocationSv.SetClassLoc(uuid,class_location,current_class);

                    } catch (SQLException throwables) {
                        Bukkit.getLogger().info(throwables.toString());
                        Bukkit.getLogger().info("Could not update");
                    }
                    sender.sendMessage("You picked the Bard class!");
                    Listeners.lookUpPlayer(((Player) sender).getUniqueId()).setType("bard");
                    sender.sendMessage("Your past class is->" + c_class);
                    try {
                        ClassTable.SetCurrentClass(uuid,"bard");
                    } catch (SQLException throwables) {
                        Bukkit.getLogger().info(throwables.toString());
                        Bukkit.getLogger().info("Could not set class bard on db");
                    }
                    ((Player) sender).getInventory().clear();
                    items = Item_Stack.getItemStack(((Player) sender).getUniqueId(),"Bard");
                    ((Player) sender).getInventory().setContents(items);



                    return true;
                case "knight":
                    try {
                        Item_Stack.updateItemStack(uuid,current_class,itemStack);
                        LocationSv.SetClassLoc(uuid,class_location,current_class);
                    } catch (SQLException throwables) {
                        Bukkit.getLogger().info(throwables.toString());
                        Bukkit.getLogger().info("Could not update");
                    }
                    sender.sendMessage("You picked the Knight class!");
                    Listeners.lookUpPlayer(((Player) sender).getUniqueId()).setType("knight");
                    sender.sendMessage("Your past class is->" + c_class);
                    try {
                        ClassTable.SetCurrentClass(uuid,"knight");
                    } catch (SQLException throwables) {
                        Bukkit.getLogger().info(throwables.toString());
                        Bukkit.getLogger().info("Could not set class knight on db");
                    }
                    ((Player) sender).getInventory().clear();
                    items = Item_Stack.getItemStack(((Player) sender).getUniqueId(),"knight");
                    ((Player) sender).getInventory().setContents(items);
                    }

                    return true;
            }
            return true;
        }
}




