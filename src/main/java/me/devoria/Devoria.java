package me.devoria;

import java.io.File;
import java.util.List;
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
import me.devoria.config.RuntimeConfiguration;
import me.devoria.config.RuntimeConfigurationValidator;
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
        RuntimeConfiguration runtimeConfiguration = loadRuntimeConfiguration();
        configureOptionalIntegrations(runtimeConfiguration);
        createDataDirectories();
        registerListeners();
        registerCommands();
        applyWorldRules(runtimeConfiguration);
    }

    private void applyWorldRules(RuntimeConfiguration config) {
        if (!config.worldRulesEnabled()) {
            return;
        }
        for (World world : Bukkit.getWorlds()) {
            world.setGameRule(GameRule.DO_MOB_SPAWNING, config.mobSpawning());
            world.setGameRule(GameRule.DO_FIRE_TICK, config.fireTick());
            world.setGameRule(GameRule.RANDOM_TICK_SPEED, config.randomTickSpeed());
            world.setGameRule(GameRule.MOB_GRIEFING, config.mobGriefing());
        }
    }

    private RuntimeConfiguration loadRuntimeConfiguration() {
        List<String> errors = RuntimeConfigurationValidator.validate(
                getConfig(), System.getenv());
        if (errors.isEmpty()) {
            return RuntimeConfiguration.from(getConfig());
        }

        for (String error : errors) {
            getLogger().severe("Configuration error: " + error);
        }
        throw new IllegalStateException(
                "Invalid config.yml; correct the errors above and restart Devoria");
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

    private void configureOptionalIntegrations(RuntimeConfiguration config) {
        if (!config.modelEngineEnabled()) {
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
