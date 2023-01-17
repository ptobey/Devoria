package me.devoria;

import me.devoria.commands.Adventure;
import me.devoria.commands.Creative;
import me.devoria.commands.GetItemInfo;
import me.devoria.commands.IdentifyCommand;
import me.devoria.commands.ItemCommand;
import me.devoria.commands.LootCommand;
import me.devoria.commands.OpenFactionGUI;
import me.devoria.commands.RegisterCommand;
import me.devoria.commands.Spectator;
import me.devoria.commands.SpellMode;
import me.devoria.commands.SummonMob;
import me.devoria.commands.Survival;
import me.devoria.cooldowns.CooldownManager;
import me.devoria.listeners.EntityListener;
import me.devoria.listeners.GUIListener;
import me.devoria.listeners.PlayerListener;
import me.devoria.player.PlayerStats;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.java.JavaPlugin;
import java.io.File;
import java.io.IOException;

public class Devoria extends JavaPlugin {

    private static Devoria instance;
    private CooldownManager cdInstance;
    public static File dataFolder;


    public void onEnable() {
        cdInstance = new CooldownManager();
        dataFolder = getDataFolder();
        instance = this;

        //saveDefaultConfig();
        createCustomConfig();
        registerListeners();
        registerCommands();
    }
    public void onDisable() {
        PlayerStats.saveAll();
    }

    public void registerListeners() {
        getServer().getPluginManager().registerEvents(new EntityListener(), this);
        getServer().getPluginManager().registerEvents(new PlayerListener(), this);
        getServer().getPluginManager().registerEvents(new GUIListener(), this);
    }

    public void registerCommands() {
        this.getCommand("item").setExecutor(new ItemCommand());
        this.getCommand("register").setExecutor(new RegisterCommand());
        this.getCommand("getinfo").setExecutor(new GetItemInfo());
        this.getCommand("loot").setExecutor(new LootCommand());
        this.getCommand("identify").setExecutor(new IdentifyCommand());
        this.getCommand("summonmob").setExecutor(new SummonMob());
        this.getCommand("spellmode").setExecutor(new SpellMode());
        getCommand("survival").setExecutor(new Survival());
        getCommand("creative").setExecutor(new Creative());
        getCommand("adventure").setExecutor(new Adventure());
        getCommand("spectator").setExecutor(new Spectator());
        getCommand("factions").setExecutor(new OpenFactionGUI());
    }


    private void createCustomConfig() {
        File makeItemsFolder = new File(dataFolder, "items/");
        if (!makeItemsFolder.exists()) {
            makeItemsFolder.getParentFile().mkdirs();
            saveResource("items/", false);
        }
        FileConfiguration customConfig = new YamlConfiguration();

        try {
            customConfig.load(makeItemsFolder);
        } catch (IOException | InvalidConfigurationException e) {
            /*
             Do nothing for now, since it doesn't seem to be used.
            e.printStackTrace();
            System.out.println("Ignore This");
            */
        }

        File mobFolder = new File(dataFolder, "mobs/");
        if (!mobFolder.exists()) {
            mobFolder.getParentFile().mkdirs();
            saveResource("mobs/", false);
        }

        try {
            customConfig.load(mobFolder);
        } catch (IOException | InvalidConfigurationException e) {
            /*
             Do nothing for now, since it doesn't seem to be used.
             e.printStackTrace();
             System.out.println("Ignore This");
            */
        }

    }
    public static Devoria getInstance() {
        return instance;
    }

    public CooldownManager getCdInstance() {
        return cdInstance;
    }
}