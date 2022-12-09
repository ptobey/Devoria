package me.devoria.player;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import me.devoria.Devoria;
import me.devoria.spells.Spell;
import me.devoria.spells.SpellTriggers;
import me.devoria.spells.imanity.humans.HumanSpells;
import me.devoria.spells.lightseekers.elves.ElfSpells;
import me.devoria.utils.JsonUtils;
import org.bukkit.entity.Player;

public class PlayerStats {
    public static HashMap<UUID, PlayerStats> playerStats = new HashMap<>();
    private static final Path STORAGE_FOLDER = Devoria.getInstance().getDataFolder().toPath().resolve("PlayerData");
    private final Path storage;
    private final UUID uuid;
    private FactionType faction;
    private AffinityType affinity;
    private Spell[] spells = {
            HumanSpells.HEROIC_STRIKE,
            HumanSpells.ADVENTURERS_AURA,
            HumanSpells.ENERGY_BURST,
            HumanSpells.DASH
    };
    private int maxMana = 100;
    private int currentMana = 100;
    public SpellTriggers spellTriggers;

    @Override
    public String toString() {
        return "PlayerData{" +
                "uuid=" + uuid +
                ", faction='" + faction +
                ", affinity='" + affinity +
                ", spellRLR='" + spells[0] +
                ", spellRRL='" + spells[1] +
                ", spellRLL='" + spells[2] +
                ", spellRRR='" + spells[3] +
                ", mana=" + +
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
    }
    
    public static PlayerStats getStats(UUID uuid) {
        PlayerStats data = playerStats.getOrDefault(uuid, null);
        if (data == null) {
            Path path = STORAGE_FOLDER.resolve(uuid.toString() + ".json");
            if (!Files.exists(path)) {
                data = new PlayerStats(uuid);
                return data;
            }
            try {
                // getting the data
                String json = Files.readString(path);
                data = JsonUtils.GSON.fromJson(json, PlayerStats.class);
            } catch (IOException e) {
                // print the error
                e.printStackTrace();
            }
            playerStats.put(uuid, data);
        }
        return data;
    }

    public void createJSON() throws IOException {
        if (!Files.exists(this.storage)) {
            if (!Files.exists(this.storage.getParent())) {
                Files.createDirectory(this.storage.getParent());
            }
            Files.createFile(this.storage);
        }
    }

    public static void saveAll() {
        playerStats.forEach((k, v) -> v.saveAndDelete());
    }

    public void save() {
        try {
            this.createJSON();
            Files.writeString(this.storage, JsonUtils.GSON.toJson(this));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void saveAndDelete() {
        save();
        playerStats.remove(this,uuid);
    }
}
