package me.devoria.adapters;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;
import java.lang.reflect.Type;
import me.devoria.player.AffinityType;
import me.devoria.player.FactionType;
import me.devoria.player.PlayerProfileDocument;
import me.devoria.player.PlayerStats;
import me.devoria.spells.Spell;

public class PlayerStatsAdapter implements JsonSerializer<PlayerStats>, JsonDeserializer<PlayerStats> {
    @Override
    public JsonElement serialize(PlayerStats data, Type type, JsonSerializationContext context) {
        JsonObject object = new JsonObject();

        object.addProperty("schemaVersion", PlayerProfileDocument.CURRENT_SCHEMA_VERSION);
        object.addProperty("uuid", data.getUuid().toString());
        object.addProperty("maxMana", data.getMaxMana());
        object.addProperty("mana", data.getMana());
        object.addProperty("faction", FactionType.toString(data.getFaction()));
        object.addProperty("affinity", AffinityType.toString(data.getAffinity()));
        object.addProperty("spellRLR", data.getSpells()[0].toString());
        object.addProperty("spellRRL", data.getSpells()[1].toString());
        object.addProperty("spellRLL", data.getSpells()[2].toString());
        object.addProperty("spellRRR", data.getSpells()[3].toString());
        return object;
    }

    @Override
    public PlayerStats deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        PlayerProfileDocument document = PlayerProfileDocument.fromJson(json);
        PlayerStats playerData = new PlayerStats(document.uuid());
        playerData.setMaxMana(document.maxMana());
        playerData.setMana(document.mana());
        playerData.setFaction(FactionType.fromString(document.faction()));
        playerData.setAffinity(AffinityType.fromString(document.affinity()));
        playerData.setSpells(new Spell[] {
                Spell.fromString(document.spells().get(0)),
                Spell.fromString(document.spells().get(1)),
                Spell.fromString(document.spells().get(2)),
                Spell.fromString(document.spells().get(3))
        });
        return playerData;
    }
}
