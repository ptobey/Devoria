package me.devoria.core;

import me.devoria.core.commands.*;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.java.JavaPlugin;
import java.io.File;
import java.io.IOException;

public class Core extends JavaPlugin {


    public void onEnable() {

        createCustomConfig();
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
        this.getCommand("getinfo").setExecutor(new GetItemInfo());
    }


    private void createCustomConfig() {
        File makeItemsFolder = new File(getDataFolder(), "items/example.yml");
        if (!makeItemsFolder.exists()) {
            makeItemsFolder.getParentFile().mkdirs();
            saveResource("items/example.yml", false);
        }
        FileConfiguration customConfig = new YamlConfiguration();
        try {
            customConfig.load(makeItemsFolder);
        } catch (IOException | InvalidConfigurationException e) {
            e.printStackTrace();
        }
    }
}