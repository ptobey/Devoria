package me.devoria.commands;

import io.lumine.xikage.mythicmobs.utils.Players;
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
                //PlayerStats.getStats(Bukkit.getPlayer(string), Bukkit.getPlayerUniqueId(string)).spellMode = !PlayerStats.getStats(Bukkit.getPlayer(string), Bukkit.getPlayerUniqueId(string)).spellMode;
                Bukkit.getPlayer(string).sendMessage("SpellMode Toggled!");
                return true;
            }
        }

        if (commandSender instanceof Player) {
            Player sender = (Player) commandSender;
            //PlayerStats.getStats(sender, sender.getUniqueId()).spellMode = !PlayerStats.getStats(sender, sender.getUniqueId()).spellMode;
            sender.sendMessage("SpellMode Toggled!");
            return true;
        }
        return false;
    }
}
