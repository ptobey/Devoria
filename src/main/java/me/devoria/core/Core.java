package me.devoria.core;

import me.devoria.core.commands.AttyMod;
import me.devoria.core.commands.ClassCommand;
import org.bukkit.plugin.java.JavaPlugin;

public class Core extends JavaPlugin {

    public void onEnable() {
        getLogger().info("onEnable is called!");
        registerListeners();
        registerCommands();
    }
    public void onDisable() {
    }

    public void registerListeners() {
        getServer().getPluginManager().registerEvents(new Listeners(), this);
    }
    public void registerCommands() {
        this.getCommand("class").setExecutor(new ClassCommand());
        this.getCommand("Reveal").setExecutor(new AttyMod());
    }


}