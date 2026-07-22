package me.devoria.player;

import com.google.gson.JsonParseException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.NoSuchFileException;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import me.devoria.Devoria;
import me.devoria.spells.DefaultSpells;
import me.devoria.spells.Spell;
import me.devoria.spells.SpellTriggers;
import me.devoria.utils.JsonUtils;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

public class PlayerStats {
    public static Map<UUID, PlayerStats> playerStats = new HashMap<>();
    private static final Path STORAGE_FOLDER = Devoria.getInstance().getDataFolder().toPath().resolve("PlayerData");
    private final Path storage;
    private final UUID uuid;
    private transient boolean quarantineRequired;
    public SpellTriggers spellTriggers;
    public boolean spellMode;
    private FactionType faction = FactionType.NONE;
    private AffinityType affinity = AffinityType.NONE;
    private Spell[] spells = {
            DefaultSpells.DEFAULT,
            DefaultSpells.DEFAULT,
            DefaultSpells.DEFAULT,
            DefaultSpells.DEFAULT
    };
    private int maxMana = 100;
    private int currentMana = 100;

    @Override
    public String toString() {
        return "PlayerData{" +
                "uuid=" + uuid.toString() +
                ", faction='" + faction.toString() +
                ", affinity='" + affinity.toString() +
                ", spellRLR='" + spells[0].toString() +
                ", spellRRL='" + spells[1].toString() +
                ", spellRLL='" + spells[2].toString() +
                ", spellRRR='" + spells[3].toString() +
                ", maxMana=" + maxMana +
                ", mana=" + currentMana +
                '}';
    }

    public UUID getUuid() {
        return uuid;
    }

    public FactionType getFaction() {
        return faction;
    }

    public AffinityType getAffinity() {
        return affinity;
    }

    public Spell[] getSpells() {
        return spells;
    }

    public int getMaxMana() {
        return maxMana;
    }

    public int getMana() {
        return currentMana;
    }


    public void setFaction(FactionType faction) {
        this.faction = faction;
    }

    public void setAffinity(AffinityType affinity) {
        this.affinity = affinity;
    }

    public void setSpells(Spell[] spells) {
        this.spells = spells;
    }

    public void setSpells(int index, Spell spell) {
        spells[index] = spell;
    }

    public void setMaxMana(int maxMana) {
        this.maxMana = maxMana;
    }

    public void setMana(int currentMana) {
        this.currentMana = currentMana;
    }


    public PlayerStats(UUID uuid) {
        this.uuid = uuid;
        this.storage = STORAGE_FOLDER.resolve(uuid + ".json");
        this.spellTriggers = new SpellTriggers(Bukkit.getPlayer(uuid));
    }

    public static PlayerStats getStats(UUID uuid) {
        PlayerStats data = playerStats.getOrDefault(uuid, null);
        if (data == null) {
            Path path = STORAGE_FOLDER.resolve(uuid.toString() + ".json");
            if (!Files.exists(path)) {
                data = new PlayerStats(uuid);
                playerStats.put(uuid, data);
                return data;
            }
            try {
                String json = BoundedProfileReader.read(path);
                data = JsonUtils.GSON.fromJson(json, PlayerStats.class);
                if (data == null) {
                    throw new IOException("Player data was empty");
                }
                PlayerProfileDocument.requireIdentity(uuid, data.getUuid());
            } catch (IOException | JsonParseException e) {
                Devoria.getInstance().getLogger().warning(
                        "Player data for " + uuid + " failed validation ("
                                + e.getClass().getSimpleName()
                                + "); preserving it before creating defaults.");
                data = new PlayerStats(uuid);
                data.quarantineRequired = true;
                data.quarantineInvalidProfile();
            }
            playerStats.put(uuid, data);
        }
        return data;
    }

    public static void saveAll() {
        SaveBatch.saveAndRemoveSuccessful(playerStats, PlayerStats::save);
    }

    public boolean save() {
        if (quarantineRequired && !quarantineInvalidProfile()) {
            return false;
        }
        try {
            AtomicProfileWriter.write(storage, JsonUtils.GSON.toJson(this));
            return true;
        } catch (IOException | RuntimeException e) {
            Devoria.getInstance().getLogger().severe(
                    "Could not save player data for " + uuid + " at " + storage
                            + ": " + e.getMessage()
                            + ". State retained in memory for retry.");
            return false;
        }
    }

    public boolean saveAndDelete() {
        if (!save()) {
            return false;
        }
        playerStats.remove(uuid, this);
        return true;
    }

    public void bindPlayer(Player player) {
        if (!uuid.equals(player.getUniqueId())) {
            throw new IllegalArgumentException("Cannot bind a different player UUID");
        }
        spellTriggers = new SpellTriggers(player);
    }

    /** Clears scheduler-backed state that must never survive player lifecycle changes. */
    public boolean cancelPendingSpellInput() {
        if (spellTriggers == null || !spellTriggers.hasPendingInput()) {
            return false;
        }
        spellTriggers.cancelPendingInput();
        return true;
    }

    public static void cancelAllPendingSpellInput() {
        playerStats.values().forEach(PlayerStats::cancelPendingSpellInput);
    }

    private boolean quarantineInvalidProfile() {
        try {
            Path backup = InvalidProfileQuarantine.moveAside(storage);
            quarantineRequired = false;
            Devoria.getInstance().getLogger().warning(
                    "Invalid player data for " + uuid + " was moved to "
                            + backup.getFileName() + ".");
            return true;
        } catch (NoSuchFileException e) {
            quarantineRequired = false;
            return true;
        } catch (IOException | RuntimeException e) {
            quarantineRequired = true;
            Devoria.getInstance().getLogger().severe(
                    "Could not preserve invalid player data for " + uuid + " at "
                            + storage + " (" + e.getClass().getSimpleName()
                            + "). Saves are blocked and state is retained in memory for retry.");
            return false;
        }
    }
}
