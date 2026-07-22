package me.devoria.commands;

import me.devoria.Devoria;
import me.devoria.utils.ItemUtils;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.io.File;


public class ItemCommand implements CommandExecutor {

    //Gives you a custom item
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage("This command may only be used in-game.");
            return true;
        }
        if (args.length == 0) {
            sender.sendMessage("Usage: /item <name>");
            return true;
        }

        if (sender instanceof Player) {
            try {
                ((Player) sender).getInventory().addItem((ItemUtils.updateItem(",fileName:"+args[0])));
                return true;
            } catch (Exception e) {
                Devoria.getInstance().getLogger().warning("Could not grant item '" + args[0] + "': " + e.getMessage());

                File itemsFolder = new File(Devoria.dataFolder, "/items");

                String[] names = itemsFolder.list();
                StringBuilder list = new StringBuilder();

                if (names != null) {
                    for (String name : names) {
                        list.append(name).append(" ");
                    }
                }

                sender.sendMessage(list.length() == 0 ? "No item definitions are available." : list.toString());

                return true;
            }
        }
        return true;
    }
}
