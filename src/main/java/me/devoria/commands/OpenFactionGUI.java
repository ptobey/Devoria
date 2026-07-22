package me.devoria.commands;

import me.devoria.guis.FactionGUI;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class OpenFactionGUI implements CommandExecutor {
    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if (args.length > 0) {
            if (!sender.hasPermission("devoria.command.factions.others")) {
                sender.sendMessage("You do not have permission to change another player's faction.");
                return true;
            }

            Player target = Bukkit.getPlayerExact(args[0]);
            if (target == null) {
                sender.sendMessage("Player not found.");
                return true;
            }
            FactionGUI.openGUI(target);
            return true;
        }

        if (!(sender instanceof Player)) {
            sender.sendMessage("Specify an online player when using this command from the console.");
            return true;
        }
        FactionGUI.openGUI((Player) sender);
        return true;
    }
}
