package me.devoria.player;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.AtomicMoveNotSupportedException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;

/** Writes a complete profile without exposing a partially written destination. */
final class AtomicProfileWriter {

    private AtomicProfileWriter() {
    }

    static void write(Path destination, String content) throws IOException {
        write(destination, content, AtomicProfileWriter::moveIntoPlace);
    }

    static void write(Path destination, String content, MoveOperation moveOperation)
            throws IOException {
        Path parent = destination.getParent();
        if (parent == null) {
            throw new IOException("Profile destination must have a parent directory");
        }

        Files.createDirectories(parent);
        Path temporary = Files.createTempFile(parent,
                destination.getFileName().toString(), ".tmp");
        try {
            Files.writeString(temporary, content, StandardCharsets.UTF_8);
            moveOperation.move(temporary, destination);
        } finally {
            Files.deleteIfExists(temporary);
        }
    }

    private static void moveIntoPlace(Path temporary, Path destination)
            throws IOException {
        try {
            Files.move(temporary, destination,
                    StandardCopyOption.ATOMIC_MOVE,
                    StandardCopyOption.REPLACE_EXISTING);
        } catch (AtomicMoveNotSupportedException exception) {
            Files.move(temporary, destination, StandardCopyOption.REPLACE_EXISTING);
        }
    }

    @FunctionalInterface
    interface MoveOperation {
        void move(Path source, Path destination) throws IOException;
    }
}
