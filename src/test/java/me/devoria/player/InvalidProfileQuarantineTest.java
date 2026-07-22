package me.devoria.player;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.IOException;
import java.nio.file.FileAlreadyExistsException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.concurrent.atomic.AtomicInteger;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

class InvalidProfileQuarantineTest {

    @TempDir
    Path directory;

    @Test
    void movesInvalidBytesToARecoveryFile() throws IOException {
        Path source = directory.resolve("player.json");
        Files.writeString(source, "{broken json");

        Path backup = InvalidProfileQuarantine.moveAside(
                source, "2026-07-22T06-00-00Z", Files::move);

        assertEquals(directory.resolve(
                "player.json.invalid-2026-07-22T06-00-00Z"), backup);
        assertEquals("{broken json", Files.readString(backup));
        assertFalse(Files.exists(source));
    }

    @Test
    void neverReplacesAnExistingRecoveryFile() throws IOException {
        Path source = directory.resolve("player.json");
        Path firstBackup = directory.resolve("player.json.invalid-marker");
        Files.writeString(source, "second invalid profile");
        Files.writeString(firstBackup, "first invalid profile");

        Path secondBackup = InvalidProfileQuarantine.moveAside(
                source, "marker", Files::move);

        assertEquals(directory.resolve("player.json.invalid-marker-1"), secondBackup);
        assertEquals("first invalid profile", Files.readString(firstBackup));
        assertEquals("second invalid profile", Files.readString(secondBackup));
    }

    @Test
    void failedMoveLeavesTheOnlyProfileUntouched() throws IOException {
        Path source = directory.resolve("player.json");
        Files.writeString(source, "profile that must survive");

        IOException error = assertThrows(IOException.class,
                () -> InvalidProfileQuarantine.moveAside(source, "marker",
                        (ignoredSource, ignoredDestination) -> {
                            throw new IOException("simulated permission failure");
                        }));

        assertEquals("simulated permission failure", error.getMessage());
        assertEquals("profile that must survive", Files.readString(source));
    }

    @Test
    void retriesAfterAConcurrentNameCollision() throws IOException {
        Path source = directory.resolve("player.json");
        Files.writeString(source, "invalid profile");
        AtomicInteger attempts = new AtomicInteger();

        Path backup = InvalidProfileQuarantine.moveAside(source, "marker",
                (moveSource, destination) -> {
                    if (attempts.getAndIncrement() == 0) {
                        throw new FileAlreadyExistsException(destination.toString());
                    }
                    Files.move(moveSource, destination);
                });

        assertEquals(2, attempts.get());
        assertTrue(backup.endsWith("player.json.invalid-marker-1"));
        assertEquals("invalid profile", Files.readString(backup));
    }

    @Test
    void rejectsMarkersThatCouldEscapeTheProfileDirectory() throws IOException {
        Path source = directory.resolve("player.json");
        Files.writeString(source, "invalid profile");

        assertThrows(IllegalArgumentException.class,
                () -> InvalidProfileQuarantine.moveAside(
                        source, "../outside", Files::move));
        assertTrue(Files.exists(source));
    }
}
