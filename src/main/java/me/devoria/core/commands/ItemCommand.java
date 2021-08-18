package me.devoria.core.commands;


import me.devoria.core.customBows.MakeBow;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;


public class ItemCommand implements CommandExecutor {

    //Gives you a custom item
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (sender instanceof Player) {
            sender.sendMessage("temp message!");
            ((Player) sender).getInventory().addItem(MakeBow.makeBow("Cow Bow", false, "common", -3, "80-120", "20"));
            ((Player) sender).getInventory().addItem(MakeBow.makeBow("Cow Bow", false, "rare", -2, "80-120", "20"));
            ((Player) sender).getInventory().addItem(MakeBow.makeBow("Cow Bow", false, "epic", -1, "80-120", "20"));
            ((Player) sender).getInventory().addItem(MakeBow.makeBow("Cow Bow", false, "legendary", 0, "80-120", "20"));
            ((Player) sender).getInventory().addItem(MakeBow.makeBow("Cow Bow", false, "mythic", 1, "80-120", "20"));
            ((Player) sender).getInventory().addItem(MakeBow.makeBow("Cow Bow", false, "legendary", 2, "80-120", "20"));
            ((Player) sender).getInventory().addItem(MakeBow.makeBow("Cow Bow", false, "rare", 3, "80-120", "20"));
            return true;
        }
        return false;
    }
}