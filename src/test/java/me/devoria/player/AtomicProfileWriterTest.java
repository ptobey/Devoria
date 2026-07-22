package me.devoria.player;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.concurrent.atomic.AtomicReference;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

class AtomicProfileWriterTest {

    @TempDir
    Path directory;

    @Test
    void createsACompleteProfile() throws IOException {
        Path destination = directory.resolve("profiles/player.json");

        AtomicProfileWriter.write(destination, "new profile");

        assertEquals("new profile", Files.readString(destination));
        assertNoTemporaryFiles(destination.getParent());
    }

    @Test
    void replacesAnExistingProfile() throws IOException {
        Path destination = directory.resolve("player.json");
        Files.writeString(destination, "old profile");

        AtomicProfileWriter.write(destination, "new profile");

        assertEquals("new profile", Files.readString(destination));
        assertNoTemporaryFiles(directory);
    }

    @Test
    void failedMovePreservesLastGoodProfileAndDeletesTemporaryFile()
            throws IOException {
        Path destination = directory.resolve("player.json");
        Files.writeString(destination, "last good profile");
        AtomicReference<Path> temporaryFile = new AtomicReference<>();

        IOException error = assertThrows(IOException.class,
                () -> AtomicProfileWriter.write(destination, "unsaved profile",
                        (source, ignoredDestination) -> {
                            temporaryFile.set(source);
                            throw new IOException("simulated disk failure");
                        }));

        assertEquals("simulated disk failure", error.getMessage());
        assertEquals("last good profile", Files.readString(destination));
        assertFalse(Files.exists(temporaryFile.get()));
        assertNoTemporaryFiles(directory);
    }

    private static void assertNoTemporaryFiles(Path directory) throws IOException {
        try (var files = Files.list(directory)) {
            assertTrue(files.noneMatch(path -> path.getFileName().toString().endsWith(".tmp")));
        }
    }
}
