package edu.hw6.task3;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.FileSystem;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.PathMatcher;

public class Filter {
    public static final int BYTE_MASK = 0xFF;
    public static final AbstractFilter IS_REGULAR_FILE = Files::isRegularFile;
    public static final AbstractFilter IS_READABLE = Files::isReadable;

    private Filter() {}

    public static AbstractFilter largerThan(long size) {
        return entry -> {
            try {
                return Files.size(entry) > size;
            } catch (IOException e) {
                return false;
            }
        };
    }

    public static AbstractFilter magicNumber(int... magicBytes) {
        return entry -> {
            try (InputStream is = Files.newInputStream(entry)) {
                byte[] bytes = new byte[magicBytes.length];
                int bytesRead = is.read(bytes);

                if (bytesRead != magicBytes.length) {
                    return false;
                }

                for (int i = 0; i < magicBytes.length; i++) {
                    if ((bytes[i] & BYTE_MASK) != magicBytes[i]) {
                        return false;
                    }
                }

                return true;
            } catch (IOException e) {
                return false;
            }
        };
    }

    public static AbstractFilter globMatches(String glob) {
        return entry -> {
            FileSystem fileSystem = FileSystems.getDefault();
            PathMatcher pathMatcher = fileSystem.getPathMatcher("glob:" + glob);

            return pathMatcher.matches(entry.getFileName());
        };
    }

    public static AbstractFilter regexContains(String regex) {
        return entry -> entry.getFileName().toString().matches(".*" + regex + ".*");
    }
}
