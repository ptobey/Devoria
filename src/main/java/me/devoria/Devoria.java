package me.devoria;

import java.io.File;
import java.util.Objects;
import me.devoria.commands.Adventure;
import me.devoria.commands.Creative;
import me.devoria.commands.GetItemInfo;
import me.devoria.commands.IdentifyCommand;
import me.devoria.commands.ItemCommand;
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
import me.devoria.utils.DatabaseUtils;
import org.bukkit.Bukkit;
import org.bukkit.GameRule;
import org.bukkit.World;
import org.bukkit.command.PluginCommand;
import org.bukkit.plugin.java.JavaPlugin;

public class Devoria extends JavaPlugin {

    private static Devoria instance;
    private CooldownManager cdInstance;
    private boolean modelEngineAvailable;
    public static File dataFolder;


    public void onEnable() {
        cdInstance = new CooldownManager();
        dataFolder = getDataFolder();
        instance = this;

        saveDefaultConfig();
        configureOptionalIntegrations();
        createDataDirectories();
        registerListeners();
        registerCommands();

        if (getConfig().getBoolean("world-rules.enabled", false)) {
            for (World world : Bukkit.getWorlds()) {
                world.setGameRule(GameRule.DO_MOB_SPAWNING,
                        getConfig().getBoolean("world-rules.do-mob-spawning", false));
                world.setGameRule(GameRule.DO_FIRE_TICK,
                        getConfig().getBoolean("world-rules.do-fire-tick", false));
                world.setGameRule(GameRule.RANDOM_TICK_SPEED,
                        getConfig().getInt("world-rules.random-tick-speed", 0));
                world.setGameRule(GameRule.MOB_GRIEFING,
                        getConfig().getBoolean("world-rules.mob-griefing", false));
            }
        }
    }

    public void onDisable() {
        PlayerStats.saveAll();
        DatabaseUtils.disconnect();
    }

    public void registerListeners() {
        getServer().getPluginManager().registerEvents(new EntityListener(), this);
        getServer().getPluginManager().registerEvents(new PlayerListener(), this);
        getServer().getPluginManager().registerEvents(new GUIListener(), this);
    }

    public void registerCommands() {
        Objects.requireNonNull(getCommand("item")).setExecutor(new ItemCommand());
        Objects.requireNonNull(getCommand("register")).setExecutor(new RegisterCommand());
        Objects.requireNonNull(getCommand("getinfo")).setExecutor(new GetItemInfo());
        Objects.requireNonNull(getCommand("identify")).setExecutor(new IdentifyCommand());
        registerSummonMobCommand();
        Objects.requireNonNull(getCommand("spellmode")).setExecutor(new SpellMode());
        Objects.requireNonNull(getCommand("survival")).setExecutor(new Survival());
        Objects.requireNonNull(getCommand("creative")).setExecutor(new Creative());
        Objects.requireNonNull(getCommand("adventure")).setExecutor(new Adventure());
        Objects.requireNonNull(getCommand("spectator")).setExecutor(new Spectator());
        Objects.requireNonNull(getCommand("factions")).setExecutor(new OpenFactionGUI());
    }

    private void configureOptionalIntegrations() {
        if (!getConfig().getBoolean("integrations.model-engine.enabled", true)) {
            getLogger().info("ModelEngine integration is disabled by configuration.");
            modelEngineAvailable = false;
            return;
        }

        modelEngineAvailable = getServer().getPluginManager().isPluginEnabled("ModelEngine");
        if (modelEngineAvailable) {
            getLogger().info("ModelEngine integration is enabled.");
        } else {
            getLogger().warning("ModelEngine is not installed; model-backed mobs are disabled.");
        }
    }

    private void registerSummonMobCommand() {
        PluginCommand command = Objects.requireNonNull(getCommand("summonmob"));
        if (modelEngineAvailable) {
            try {
                command.setExecutor(new SummonMob());
                return;
            } catch (LinkageError error) {
                modelEngineAvailable = false;
                getLogger().severe("ModelEngine was detected, but its API is incompatible: " + error.getMessage());
            }
        }

        command.setExecutor((sender, ignoredCommand, ignoredLabel, ignoredArgs) -> {
            sender.sendMessage("ModelEngine is unavailable; model-backed mobs cannot be summoned.");
            return true;
        });
    }

    private void createDataDirectories() {
        copyExampleResource("items", "example.yml");
        copyExampleResource("mobs", "example_mob.yml");
        new File(dataFolder, "loot").mkdirs();
    }

    private void copyExampleResource(String directory, String resourceName) {
        File targetDirectory = new File(dataFolder, directory);
        if (!targetDirectory.exists() && !targetDirectory.mkdirs()) {
            getLogger().warning("Could not create data directory: " + targetDirectory);
            return;
        }

        File target = new File(targetDirectory, resourceName);
        if (!target.exists()) {
            saveResource(directory + "/" + resourceName, false);
        }
    }

    public static Devoria getInstance() {
        return instance;
    }

    public CooldownManager getCdInstance() {
        return cdInstance;
    }

    public boolean isModelEngineAvailable() {
        return modelEngineAvailable;
    }
}
