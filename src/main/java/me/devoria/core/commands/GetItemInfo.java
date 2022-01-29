package me.devoria.core.commands;

import net.md_5.bungee.api.chat.BaseComponent;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class GetItemInfo implements CommandExecutor {

    //Sets your class
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (sender instanceof Player) {

            Player p = ((Player) sender).getPlayer();
            sender.sendMessage(((Player) sender).getInventory().getItemInMainHand().getItemMeta().getLocalizedName());

            p.sendMessage(p.getMetadata("attributes").get(0).asString());
            return true;
        }
        return false;
    }
        }