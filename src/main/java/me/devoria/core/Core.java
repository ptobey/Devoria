package me.devoria.core;

import me.devoria.core.DataBase.DBconnect;
import me.devoria.core.commands.AttyMod;
import me.devoria.core.commands.ClassCommand;
import me.devoria.core.commands.ItemCommand;
import me.devoria.core.commands.RegisterCommand;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;
import java.sql.SQLException;

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
        this.getCommand("item").setExecutor(new ItemCommand());
        this.getCommand("reveal").setExecutor(new AttyMod());
        this.getCommand("register").setExecutor(new RegisterCommand());
    }

}