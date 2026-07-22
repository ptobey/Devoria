package me.devoria.player;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

class BoundedProfileReaderTest {

    @TempDir
    Path directory;

    @Test
    void readsAProfileAtTheByteLimit() throws IOException {
        Path profile = directory.resolve("player.json");
        String content = "x".repeat(BoundedProfileReader.MAX_PROFILE_BYTES);
        Files.writeString(profile, content);

        assertEquals(content, BoundedProfileReader.read(profile));
    }

    @Test
    void rejectsAProfileBeyondTheByteLimit() throws IOException {
        Path profile = directory.resolve("player.json");
        Files.writeString(profile,
                "x".repeat(BoundedProfileReader.MAX_PROFILE_BYTES + 1));

        IOException error = assertThrows(IOException.class,
                () -> BoundedProfileReader.read(profile));

        assertEquals("Player profile exceeds 65536 bytes", error.getMessage());
    }

    @Test
    void appliesTheLimitToUtf8BytesRatherThanCharacters() throws IOException {
        Path profile = directory.resolve("player.json");
        Files.writeString(profile,
                "é".repeat((BoundedProfileReader.MAX_PROFILE_BYTES / 2) + 1));

        assertThrows(IOException.class, () -> BoundedProfileReader.read(profile));
    }

    @Test
    void rejectsMalformedUtf8() throws IOException {
        Path profile = directory.resolve("player.json");
        Files.write(profile, new byte[] {(byte) 0xc3, (byte) 0x28});

        assertThrows(IOException.class, () -> BoundedProfileReader.read(profile));
    }
}
