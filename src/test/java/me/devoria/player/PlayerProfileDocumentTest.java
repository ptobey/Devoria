package me.devoria.player;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import java.util.List;
import java.util.UUID;
import org.junit.jupiter.api.Test;

class PlayerProfileDocumentTest {

    @Test
    void parsesCurrentSchema() {
        UUID uuid = UUID.randomUUID();
        JsonObject json = validProfile(uuid);
        json.addProperty("schemaVersion", 1);

        PlayerProfileDocument document = PlayerProfileDocument.fromJson(json);

        assertEquals(1, document.schemaVersion());
        assertEquals(uuid, document.uuid());
        assertEquals(100, document.maxMana());
        assertEquals(75, document.mana());
        assertEquals("imanity", document.faction());
        assertEquals("human", document.affinity());
        assertEquals(List.of("HeroicStrike", "EnergyBurst", "Dash",
                "AdventurersAura"), document.spells());
    }

    @Test
    void acceptsLegacyProfilesWithoutSchemaVersion() {
        PlayerProfileDocument document = PlayerProfileDocument.fromJson(
                validProfile(UUID.randomUUID()));

        assertEquals(0, document.schemaVersion());
    }

    @Test
    void rejectsFutureSchemaVersions() {
        JsonObject json = validProfile(UUID.randomUUID());
        json.addProperty("schemaVersion", 2);

        JsonParseException error = assertThrows(JsonParseException.class,
                () -> PlayerProfileDocument.fromJson(json));

        assertEquals("Unsupported player profile schema version: 2",
                error.getMessage());
    }

    @Test
    void rejectsManaOutsideValidatedBounds() {
        JsonObject json = validProfile(UUID.randomUUID());
        json.addProperty("mana", 101);

        JsonParseException error = assertThrows(JsonParseException.class,
                () -> PlayerProfileDocument.fromJson(json));

        assertEquals("mana must be between zero and maxMana", error.getMessage());
    }

    @Test
    void rejectsFractionalIntegers() {
        JsonObject json = validProfile(UUID.randomUUID());
        json.addProperty("mana", 75.5);

        JsonParseException error = assertThrows(JsonParseException.class,
                () -> PlayerProfileDocument.fromJson(json));

        assertEquals("mana must be an integer", error.getMessage());
    }

    @Test
    void rejectsUnknownFactionValues() {
        JsonObject json = validProfile(UUID.randomUUID());
        json.addProperty("faction", "outsider");

        JsonParseException error = assertThrows(JsonParseException.class,
                () -> PlayerProfileDocument.fromJson(json));

        assertEquals("Unsupported faction: outsider", error.getMessage());
    }

    @Test
    void rejectsMissingSpellSlots() {
        JsonObject json = validProfile(UUID.randomUUID());
        json.remove("spellRRR");

        JsonParseException error = assertThrows(JsonParseException.class,
                () -> PlayerProfileDocument.fromJson(json));

        assertEquals("spellRRR must be present", error.getMessage());
    }

    @Test
    void rejectsUnknownSpells() {
        JsonObject json = validProfile(UUID.randomUUID());
        json.addProperty("spellRRL", "FutureUnmigratedSpell");

        JsonParseException error = assertThrows(JsonParseException.class,
                () -> PlayerProfileDocument.fromJson(json));

        assertEquals("Unsupported spell: FutureUnmigratedSpell", error.getMessage());
    }

    @Test
    void rejectsIdentityThatDoesNotMatchStorageKey() {
        UUID storedUuid = UUID.randomUUID();

        JsonParseException error = assertThrows(JsonParseException.class,
                () -> PlayerProfileDocument.requireIdentity(
                        UUID.randomUUID(), storedUuid));

        assertEquals("Profile UUID does not match its storage filename",
                error.getMessage());
    }

    private static JsonObject validProfile(UUID uuid) {
        JsonObject json = new JsonObject();
        json.addProperty("uuid", uuid.toString());
        json.addProperty("maxMana", 100);
        json.addProperty("mana", 75);
        json.addProperty("faction", "imanity");
        json.addProperty("affinity", "human");
        json.addProperty("spellRLR", "HeroicStrike");
        json.addProperty("spellRRL", "EnergyBurst");
        json.addProperty("spellRLL", "Dash");
        json.addProperty("spellRRR", "AdventurersAura");
        return json;
    }
}
