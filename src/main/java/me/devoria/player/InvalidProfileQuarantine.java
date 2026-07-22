package me.devoria.player;

import java.io.IOException;
import java.nio.file.FileAlreadyExistsException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.Instant;
import java.time.format.DateTimeFormatter;

/** Moves an unreadable profile aside without replacing an earlier backup. */
final class InvalidProfileQuarantine {
    private static final int MAX_NAME_ATTEMPTS = 1_000;

    private InvalidProfileQuarantine() {
    }

    static Path moveAside(Path source) throws IOException {
        String marker = DateTimeFormatter.ISO_INSTANT.format(Instant.now())
                .replace(':', '-');
        return moveAside(source, marker, Files::move);
    }

    static Path moveAside(Path source, String marker, MoveOperation moveOperation)
            throws IOException {
        Path parent = source.getParent();
        if (parent == null) {
            throw new IOException("Profile source must have a parent directory");
        }
        if (!marker.matches("[A-Za-z0-9._-]+")) {
            throw new IllegalArgumentException("Quarantine marker contains unsafe characters");
        }

        String backupName = source.getFileName() + ".invalid-" + marker;
        for (int attempt = 0; attempt < MAX_NAME_ATTEMPTS; attempt++) {
            Path destination = parent.resolve(
                    attempt == 0 ? backupName : backupName + "-" + attempt);
            try {
                moveOperation.move(source, destination);
                return destination;
            } catch (FileAlreadyExistsException ignored) {
                // Preserve every earlier recovery artifact and try another name.
            }
        }
        throw new IOException("Could not allocate a unique quarantine filename");
    }

    @FunctionalInterface
    interface MoveOperation {
        void move(Path source, Path destination) throws IOException;
    }
}
