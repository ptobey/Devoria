package me.devoria.core.commands;

import me.devoria.core.Listeners;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;


import java.util.Locale;

public class ClassCommand implements CommandExecutor {

    //Sets your class
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (sender instanceof Player) {
            switch (args[0].toLowerCase(Locale.ROOT)) {
                case "huntsman":
                    sender.sendMessage("You picked the Huntsman class!");
                    Listeners.lookUpPlayer(((Player) sender).getUniqueId()).setType("huntsman");
                    return true;
                case "sorcerer":
                    sender.sendMessage("You picked the Sorcerer class!");
                    Listeners.lookUpPlayer(((Player) sender).getUniqueId()).setType("sorcerer");
                    return true;
                case "bard":
                    sender.sendMessage("You picked the Bard class!");
                    Listeners.lookUpPlayer(((Player) sender).getUniqueId()).setType("bard");
                    return true;
                case "knight":
                    sender.sendMessage("You picked the Knight class!");
                    Listeners.lookUpPlayer(((Player) sender).getUniqueId()).setType("knight");
                    return true;
            }
        }
        return false;
    }
}



