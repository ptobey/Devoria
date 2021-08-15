package me.devoria.core;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.Locale;

public class ClassCommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (sender instanceof Player) {
            switch (args[0].toLowerCase(Locale.ROOT)) {
                case "huntsman":
                    sender.sendMessage("You picked the Huntsman class!");
                    return true;
                case "sorcerer":
                    sender.sendMessage("You picked the Sorcerer class!");
                    return true;
                case "bard":
                    sender.sendMessage("You picked the Bard class!");
                    return true;
                case "knight":
                    sender.sendMessage("You picked the Knight class!");
                    return true;
            }
        }
        return false;
    }
}



