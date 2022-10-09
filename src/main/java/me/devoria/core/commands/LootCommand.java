package me.devoria.core.commands;

import me.devoria.core.itemSystem.GenerateLoot;
import me.devoria.core.itemSystem.MakeUnidentifiedItem;
import me.devoria.core.itemSystem.UpdateItem;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.io.FileNotFoundException;
import java.util.Map;

public class LootCommand implements CommandExecutor {

    //Gives you random loot
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (sender instanceof Player) {

        }
        return false;
    }
}
