package me.devoria.mobs;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.HashMap;
import java.util.Map;
import org.junit.jupiter.api.Test;

class MobDefinitionTest {

    @Test
    void parsesACompleteDefinition() {
        MobDefinition definition = MobDefinition.from(validValues());

        assertEquals("Training Husk", definition.name());
        assertEquals("training_husk", definition.model());
        assertEquals("zombie", definition.entityType());
        assertEquals(100, definition.maxHealth());
        assertEquals(10, definition.damage());
        assertEquals(25, definition.experience());
    }

    @Test
    void rejectsMissingRequiredValues() {
        Map<String, String> values = new HashMap<>(validValues());
        values.remove("model");

        IllegalArgumentException error = assertThrows(
                IllegalArgumentException.class,
                () -> MobDefinition.from(values));

        assertEquals("model must not be blank", error.getMessage());
    }

    @Test
    void rejectsUnsafeModelIdentifiers() {
        Map<String, String> values = new HashMap<>(validValues());
        values.put("model", "../outside");

        IllegalArgumentException error = assertThrows(
                IllegalArgumentException.class,
                () -> MobDefinition.from(values));

        assertEquals(
                "model must contain only letters, numbers, underscores, or hyphens",
                error.getMessage());
    }

    @Test
    void rejectsInvalidNumericValues() {
        Map<String, String> values = new HashMap<>(validValues());
        values.put("max_health", "0");

        IllegalArgumentException error = assertThrows(
                IllegalArgumentException.class,
                () -> MobDefinition.from(values));

        assertEquals("max_health must be greater than zero", error.getMessage());
    }

    @Test
    void rejectsNegativeRewards() {
        Map<String, String> values = new HashMap<>(validValues());
        values.put("xp", "-1");

        IllegalArgumentException error = assertThrows(
                IllegalArgumentException.class,
                () -> MobDefinition.from(values));

        assertEquals("xp must not be negative", error.getMessage());
    }

    private static Map<String, String> validValues() {
        return Map.of(
                "name", "Training Husk",
                "model", "training_husk",
                "type", "zombie",
                "max_health", "100",
                "damage", "10",
                "xp", "25");
    }
}
