package me.devoria.adapters;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;
import java.lang.reflect.Type;
import java.util.Arrays;
import java.util.UUID;
import me.devoria.player.AffinityType;
import me.devoria.player.FactionType;
import me.devoria.player.PlayerStats;
import me.devoria.spells.Spell;

public class PlayerStatsAdapter implements JsonSerializer<PlayerStats>, JsonDeserializer<PlayerStats> {
    @Override
    public JsonElement serialize(PlayerStats data, Type type, JsonSerializationContext context) {
        JsonObject object = new JsonObject();

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
        if (json.isJsonObject()) {
            JsonObject object = json.getAsJsonObject();

            PlayerStats playerDataJSON = new PlayerStats(UUID.fromString(object.get("uuid").getAsString()));
            playerDataJSON.setMaxMana(object.get("maxMana").getAsInt());
            playerDataJSON.setMana(object.get("mana").getAsInt());
            playerDataJSON.setFaction(FactionType.fromString(object.get("faction").getAsString()));
            playerDataJSON.setAffinity(AffinityType.fromString(object.get("affinity").getAsString()));
            playerDataJSON.setSpells(new Spell[] {
                    Spell.fromString(object.get("spellRLR").getAsString()),
                    Spell.fromString(object.get("spellRRL").getAsString()),
                    Spell.fromString(object.get("spellRLL").getAsString()),
                    Spell.fromString(object.get("spellRRR").getAsString())
            });
            return playerDataJSON;
        }
        return null;
    }
}
