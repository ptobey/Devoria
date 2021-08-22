package me.devoria.core.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class GetItemInfo implements CommandExecutor {

    //Sets your class
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (sender instanceof Player) {

            sender.sendMessage(((Player) sender).getInventory().getItemInMainHand().getItemMeta().getLocalizedName());
            return true;
        }
        return false;
    }
        }