package me.devoria.commands;

import me.devoria.guis.FactionGUI;
import me.devoria.guis.SpellsGUI;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class OpenSpellGUI implements CommandExecutor {
    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        for (String string : args) {
            if (Bukkit.getPlayer(string) != null) {
                SpellsGUI.openGUI(Bukkit.getPlayer(string));
                return true;
            }
        }

        if (!(sender instanceof Player)) return false;
        SpellsGUI.openGUI((Player) sender);
        return true;
    }
}
