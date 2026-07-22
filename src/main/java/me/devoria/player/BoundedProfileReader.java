package me.devoria.player;

import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;
import java.nio.charset.CodingErrorAction;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;

/** Reads a small UTF-8 profile without allowing an unbounded allocation. */
final class BoundedProfileReader {
    static final int MAX_PROFILE_BYTES = 64 * 1_024;

    private BoundedProfileReader() {
    }

    static String read(Path source) throws IOException {
        try (InputStream input = Files.newInputStream(source)) {
            byte[] bytes = input.readNBytes(MAX_PROFILE_BYTES + 1);
            if (bytes.length > MAX_PROFILE_BYTES) {
                throw new IOException(
                        "Player profile exceeds " + MAX_PROFILE_BYTES + " bytes");
            }
            return StandardCharsets.UTF_8.newDecoder()
                    .onMalformedInput(CodingErrorAction.REPORT)
                    .onUnmappableCharacter(CodingErrorAction.REPORT)
                    .decode(ByteBuffer.wrap(bytes))
                    .toString();
        }
    }
}
