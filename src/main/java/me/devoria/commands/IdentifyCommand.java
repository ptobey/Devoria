package me.devoria.commands;

import me.devoria.utils.ItemUtils;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.io.FileNotFoundException;
import java.util.HashMap;

public class IdentifyCommand implements CommandExecutor {

    //Identifies an item
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (sender instanceof Player) {
            Player player = (Player) sender;
            ItemStack heldItem = player.getInventory().getItemInMainHand();
            ItemMeta itemMeta = heldItem.getItemMeta();
            if (itemMeta == null || !itemMeta.hasLocalizedName()) {
                sender.sendMessage("The held item cannot be identified.");
                return true;
            }

            HashMap<String, String> map = new HashMap<>();

            String itemData = itemMeta.getLocalizedName();
            String[] seperatedStats = itemData.split(",");

            for (int i = 1; i < seperatedStats.length; i++) {
                String[] arr = seperatedStats[i].split(":", 2);
                if (arr.length == 2) {
                    map.put(arr[0], arr[1]);
                }
            }
            if (!map.containsKey("fileName")) {
                sender.sendMessage("The held item is missing its item definition.");
                return true;
            }

            try {
                player.getInventory().setItemInMainHand(ItemUtils.updateItem(",fileName:" + map.get("fileName")));
            } catch (FileNotFoundException e) {
                sender.sendMessage("The item definition no longer exists.");
            }

            return true;

        }
        sender.sendMessage("This command may only be used in-game.");
        return true;
    }
}
