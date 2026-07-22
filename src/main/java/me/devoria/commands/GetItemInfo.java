package me.devoria.commands;

import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class GetItemInfo implements CommandExecutor {

    //Sets your class
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage("This command may only be used in-game.");
            return true;
        }

        Player player = (Player) sender;
        ItemStack heldItem = player.getInventory().getItemInMainHand();
        ItemMeta itemMeta = heldItem.getItemMeta();
        if (itemMeta == null || !itemMeta.hasLocalizedName()) {
            sender.sendMessage("The held item does not contain Devoria metadata.");
            return true;
        }

        sender.sendMessage(itemMeta.getLocalizedName());
        if (!player.getMetadata("attributes").isEmpty()) {
            player.sendMessage(player.getMetadata("attributes").get(0).asString());
        }
        return true;
    }
}
