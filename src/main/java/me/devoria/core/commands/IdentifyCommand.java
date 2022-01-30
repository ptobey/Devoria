package me.devoria.core.commands;

import me.devoria.core.itemSystem.UpdateItem;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.io.FileNotFoundException;
import java.util.HashMap;

public class IdentifyCommand implements CommandExecutor {

    //Identifies an item
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (sender instanceof Player) {

            HashMap<String, String> map = new HashMap<>();

            String itemData = ((Player) sender).getInventory().getItemInMainHand().getItemMeta().getLocalizedName();
            String[] seperatedStats = itemData.split(",");

            for (int i = 1; i < seperatedStats.length; i++) {
                String[] arr = seperatedStats[i].split(":");
                map.put(arr[0], arr[1]);
            }

            try {
                ((Player) sender).getInventory().setItemInMainHand(UpdateItem.update(",fileName:"+map.get("fileName")));
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }

            return true;

        }
        return false;
    }
}