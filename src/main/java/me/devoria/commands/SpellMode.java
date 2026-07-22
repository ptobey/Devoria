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
        if (strings.length > 0) {
            if (!commandSender.hasPermission("devoria.command.spellmode.others")) {
                commandSender.sendMessage("You do not have permission to toggle another player's spell mode.");
                return true;
            }

            Player target = Bukkit.getPlayerExact(strings[0]);
            if (target == null) {
                commandSender.sendMessage("Player not found.");
                return true;
            }
            toggleSpellMode(target);
            return true;
        }

        if (commandSender instanceof Player) {
            toggleSpellMode((Player) commandSender);
            return true;
        }
        commandSender.sendMessage("Specify an online player when using this command from the console.");
        return true;
    }

    private void toggleSpellMode(Player player) {
        PlayerStats stats = PlayerStats.getStats(player.getUniqueId());
        stats.spellMode = !stats.spellMode;
        player.sendMessage("Spell mode " + (stats.spellMode ? "enabled" : "disabled") + ".");
    }
}
