package me.devoria.core.commands;

import me.devoria.core.DataBase.Item_Stack;
import me.devoria.core.Iventory.SerializeInventory;
import me.devoria.core.Listeners;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;


import java.sql.SQLException;
import java.util.Locale;
import java.util.UUID;

import static me.devoria.core.Iventory.SerializeInventory.itemStackArrayToBase64;

public class ClassCommand implements CommandExecutor {

    //Sets your class
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (sender instanceof Player) {
            Player player = (Player) sender;
            PlayerInventory playerInventory = player.getInventory();
            String itemStack = SerializeInventory.playerInventoryToBase64(playerInventory);
            UUID uuid = ((Player) sender).getUniqueId();
            String current_class = Listeners.lookUpPlayer(((Player) sender).getUniqueId()).getType();

            switch (args[0].toLowerCase(Locale.ROOT)) {
                case "huntsman":

                    try {
                        Item_Stack.updateItemStack(uuid,current_class,itemStack);
                    } catch (SQLException throwables) {
                        Bukkit.getLogger().info(throwables.toString());
                        Bukkit.getLogger().info("Could not update");
                    }
                    sender.sendMessage("You picked the Huntsman class!");
                    Listeners.lookUpPlayer(((Player) sender).getUniqueId()).setType("huntsman");
                    Item_Stack.getItemStack(((Player) sender).getUniqueId(),"huntsman");

                    return true;
                case "sorcerer":

                    try {
                        Item_Stack.updateItemStack(uuid,current_class,itemStack);
                    } catch (SQLException throwables) {
                        Bukkit.getLogger().info(throwables.toString());
                        Bukkit.getLogger().info("Could not update");
                    }
                    sender.sendMessage("You picked the Sorcerer class!");
                    Listeners.lookUpPlayer(((Player) sender).getUniqueId()).setType("sorcerer");
                    Item_Stack.getItemStack(((Player) sender).getUniqueId(),"sorcerer");
                    return true;
                case "bard":
                    try {
                        Item_Stack.updateItemStack(uuid,current_class,itemStack);
                    } catch (SQLException throwables) {
                        Bukkit.getLogger().info(throwables.toString());
                        Bukkit.getLogger().info("Could not update");
                    }
                    sender.sendMessage("You picked the Bard class!");
                    Listeners.lookUpPlayer(((Player) sender).getUniqueId()).setType("bard");
                    Item_Stack.getItemStack(((Player) sender).getUniqueId(),"bard");
                    return true;
                case "knight":
                    try {
                        Item_Stack.updateItemStack(uuid,current_class,itemStack);
                    } catch (SQLException throwables) {
                        Bukkit.getLogger().info(throwables.toString());
                        Bukkit.getLogger().info("Could not update");
                    }
                    sender.sendMessage("You picked the Knight class!");
                    Listeners.lookUpPlayer(((Player) sender).getUniqueId()).setType("knight");
                    Item_Stack.getItemStack(((Player) sender).getUniqueId(),"knight");
                    return true;
            }
            return true;
        }
        return false;
    }
}



