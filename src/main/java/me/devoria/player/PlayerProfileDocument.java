package me.devoria.player;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.google.gson.JsonPrimitive;
import java.util.List;
import java.util.Set;
import java.util.UUID;

/**
 * Versioned and validated representation of one persisted player profile.
 */
public record PlayerProfileDocument(
        int schemaVersion,
        UUID uuid,
        int maxMana,
        int mana,
        String faction,
        String affinity,
        List<String> spells) {

    public static final int CURRENT_SCHEMA_VERSION = 1;
    private static final int SPELL_SLOT_COUNT = 4;
    private static final Set<String> FACTIONS = Set.of(
            "imanity", "lightseeker", "hellscaper", "cavedweller", "none");
    private static final Set<String> AFFINITIES = Set.of(
            "demigod", "human", "knight", "mage", "angel", "aquan",
            "astrean", "elf", "none");
    private static final Set<String> SPELLS = Set.of(
            "SpinSlash", "GodsClaws", "Lunge", "GodScream",
            "HeroicStrike", "EnergyBurst", "Dash", "AdventurersAura",
            "HeavySlam", "KnightsJustice", "RageLeap", "DivineProtection",
            "ManaPull", "ManaBurst", "Teleport", "Channeling",
            "AngelicChaos", "DivineWrath", "Flight", "Lament",
            "AquaJet", "WaterPrison", "Ripwhirl", "OceanPower",
            "LightSurge", "Omniblast", "Warp", "LightPlague",
            "LightSpear", "ArrowRain", "LeapOfFate", "EyeOfLight",
            "Default");

    public PlayerProfileDocument {
        if (schemaVersion < 0 || schemaVersion > CURRENT_SCHEMA_VERSION) {
            throw new JsonParseException(
                    "Unsupported player profile schema version: " + schemaVersion);
        }
        if (uuid == null) {
            throw new JsonParseException("uuid must be present");
        }
        if (maxMana <= 0) {
            throw new JsonParseException("maxMana must be greater than zero");
        }
        if (mana < 0 || mana > maxMana) {
            throw new JsonParseException("mana must be between zero and maxMana");
        }
        if (faction == null || faction.isBlank()) {
            throw new JsonParseException("faction must not be blank");
        }
        if (!FACTIONS.contains(faction)) {
            throw new JsonParseException("Unsupported faction: " + faction);
        }
        if (affinity == null || affinity.isBlank()) {
            throw new JsonParseException("affinity must not be blank");
        }
        if (!AFFINITIES.contains(affinity)) {
            throw new JsonParseException("Unsupported affinity: " + affinity);
        }
        if (spells == null || spells.size() != SPELL_SLOT_COUNT
                || spells.stream().anyMatch(value -> value == null || value.isBlank())) {
            throw new JsonParseException("exactly four spell slots must be configured");
        }
        for (String spell : spells) {
            if (!SPELLS.contains(spell)) {
                throw new JsonParseException("Unsupported spell: " + spell);
            }
        }
        spells = List.copyOf(spells);
    }

    public static PlayerProfileDocument fromJson(JsonElement json) {
        if (json == null || !json.isJsonObject()) {
            throw new JsonParseException("Player profile must be a JSON object");
        }

        JsonObject object = json.getAsJsonObject();
        int schemaVersion = object.has("schemaVersion")
                ? requireInteger(object, "schemaVersion")
                : 0;

        UUID uuid;
        try {
            uuid = UUID.fromString(requireString(object, "uuid"));
        } catch (IllegalArgumentException exception) {
            throw new JsonParseException("uuid must be a valid UUID", exception);
        }

        return new PlayerProfileDocument(
                schemaVersion,
                uuid,
                requireInteger(object, "maxMana"),
                requireInteger(object, "mana"),
                requireString(object, "faction"),
                requireString(object, "affinity"),
                List.of(
                        requireString(object, "spellRLR"),
                        requireString(object, "spellRRL"),
                        requireString(object, "spellRLL"),
                        requireString(object, "spellRRR")));
    }

    public static void requireIdentity(UUID expectedUuid, UUID storedUuid) {
        if (!expectedUuid.equals(storedUuid)) {
            throw new JsonParseException(
                    "Profile UUID does not match its storage filename");
        }
    }

    private static int requireInteger(JsonObject object, String field) {
        JsonPrimitive primitive = requirePrimitive(object, field);
        if (!primitive.isNumber()) {
            throw new JsonParseException(field + " must be an integer");
        }
        try {
            return Integer.parseInt(primitive.getAsString());
        } catch (NumberFormatException exception) {
            throw new JsonParseException(field + " must be an integer", exception);
        }
    }

    private static String requireString(JsonObject object, String field) {
        JsonPrimitive primitive = requirePrimitive(object, field);
        if (!primitive.isString() || primitive.getAsString().isBlank()) {
            throw new JsonParseException(field + " must be a non-blank string");
        }
        return primitive.getAsString();
    }

    private static JsonPrimitive requirePrimitive(JsonObject object, String field) {
        JsonElement value = object.get(field);
        if (value == null || !value.isJsonPrimitive()) {
            throw new JsonParseException(field + " must be present");
        }
        return value.getAsJsonPrimitive();
    }
}
