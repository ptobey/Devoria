package me.devoria.commands;


import me.devoria.player.PlayerStats;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class SpellMode implements CommandExecutor {

    @Override
    public boolean onCommand(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String s, @NotNull String[] strings) {
        for (String string : strings) {
            if (Bukkit.getPlayer(string) != null) {
                PlayerStats.getStats(Bukkit.getPlayer(string).getUniqueId()).spellMode = !PlayerStats.getStats(Bukkit.getPlayer(string).getUniqueId()).spellMode;
                Bukkit.getPlayer(string).sendMessage("SpellMode Toggled!");
                return true;
            }
        }

        if (commandSender instanceof Player) {
            Player sender = (Player) commandSender;
            PlayerStats.getStats(sender.getUniqueId()).spellMode = !PlayerStats.getStats(sender.getUniqueId()).spellMode;
            sender.sendMessage("SpellMode Toggled!");
            return true;
        }
        return false;
    }
}
