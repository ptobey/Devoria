package me.devoria.core;

import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

public class Core extends JavaPlugin {

    public void onEnable() {
        getLogger().info("onEnable is called!");
        Bukkit.broadcastMessage("Hey");
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
    }

}