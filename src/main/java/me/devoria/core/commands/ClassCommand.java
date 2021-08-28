package me.devoria.core.commands;

import me.devoria.core.Core;
import me.devoria.core.DataBase.ClassTable;
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

public class ClassCommand implements CommandExecutor {

    //Sets your class
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (sender instanceof Player) {
            Player player = (Player) sender;
            Core.getClassSelectGUI().openInventory(player);
            return true;
        }
        return false;
    }
}




