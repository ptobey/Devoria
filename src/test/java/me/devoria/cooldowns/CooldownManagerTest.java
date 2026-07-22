package me.devoria.cooldowns;

import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertSame;

import java.util.UUID;
import org.junit.jupiter.api.Test;

class CooldownManagerTest {

    @Test
    void createdContainerIsTheOneStoredForThePlayer() {
        CooldownManager manager = new CooldownManager();
        UUID playerId = UUID.randomUUID();

        CooldownContainer created = manager.createContainer(playerId);

        assertSame(created, manager.getContainer(playerId));
        assertSame(created, manager.removeContainer(playerId));
        assertNull(manager.getContainer(playerId));
    }
}
