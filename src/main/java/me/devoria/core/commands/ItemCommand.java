package me.devoria.core.commands;

import me.devoria.core.Core;
import me.devoria.core.itemSystem.UpdateItem;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.io.File;


public class ItemCommand implements CommandExecutor {

    //Gives you a custom item
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (sender instanceof Player) {
            try {
                ((Player) sender).getInventory().addItem((UpdateItem.update(",fileName:"+args[0])));
                return true;
            } catch (Exception e) {
                e.printStackTrace();

                File itemsFolder = new File(Core.dataFolder, "/items");

                String[] names = itemsFolder.list();
                StringBuilder list = new StringBuilder();

                for (String name : names) {
                    list.append(name).append(" ");
                }

                sender.sendMessage(list.toString());

                return false;
            }
        }
        return false;
    }
}
