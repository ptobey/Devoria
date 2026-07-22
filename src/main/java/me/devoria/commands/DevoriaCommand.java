package me.devoria.commands;

import me.devoria.Devoria;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.NotNull;

public final class DevoriaCommand implements CommandExecutor {

    private final Devoria plugin;

    public DevoriaCommand(Devoria plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(
            @NotNull CommandSender sender,
            @NotNull Command command,
            @NotNull String label,
            String[] args) {
        if (args.length > 1 || (args.length == 1 && !args[0].equalsIgnoreCase("status"))) {
            return false;
        }

        sender.sendMessage("Devoria v" + plugin.getDescription().getVersion());
        sender.sendMessage("Server: " + Bukkit.getName() + " " + Bukkit.getBukkitVersion());
        if (plugin.isModelEngineAvailable()) {
            sender.sendMessage("ModelEngine: available (full model-backed features enabled)");
        } else {
            sender.sendMessage("ModelEngine: unavailable (required for full feature set; degraded mode active)");
        }
        sender.sendMessage("Legacy database: " + enabled("database.enabled"));
        sender.sendMessage("World-rule overrides: " + enabled("world-rules.enabled"));
        return true;
    }

    private String enabled(String configurationPath) {
        return plugin.getConfig().getBoolean(configurationPath, false) ? "enabled" : "disabled";
    }
}
