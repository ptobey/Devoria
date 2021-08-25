package me.devoria.core.commands;

import me.devoria.core.itemSystem.UpdateWeapon;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.io.FileNotFoundException;



public class ItemCommand implements CommandExecutor {

    //Gives you a custom item
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (sender instanceof Player) {
            try {
                ((Player) sender).getInventory().addItem((UpdateWeapon.update(",fileName:"+args[0])));
                return true;
            } catch (FileNotFoundException e) {
                e.printStackTrace();
                return false;
            }
        }
        return false;
    }
}
