package me.devoria.player;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import org.junit.jupiter.api.Test;

class SaveBatchTest {

    @Test
    void attemptsEveryValueAndRetainsOnlyFailures() {
        Map<String, String> profiles = new LinkedHashMap<>();
        profiles.put("first", "saved-first");
        profiles.put("second", "failed");
        profiles.put("third", "saved-third");
        List<String> attempts = new ArrayList<>();

        SaveBatch.saveAndRemoveSuccessful(profiles, profile -> {
            attempts.add(profile);
            return !profile.equals("failed");
        });

        assertEquals(List.of("saved-first", "failed", "saved-third"), attempts);
        assertEquals(Map.of("second", "failed"), profiles);
    }
}
