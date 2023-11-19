package edu.hw6;

import java.io.IOException;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import static edu.hw6.task3.Filter.IS_READABLE;
import static edu.hw6.task3.Filter.IS_REGULAR_FILE;
import static edu.hw6.task3.Filter.globMatches;
import static edu.hw6.task3.Filter.largerThan;
import static edu.hw6.task3.Filter.magicNumber;
import static edu.hw6.task3.Filter.regexContains;

public class Task3Test {
    @Test
    @DisplayName("filter")
    void abstractFilterTest() {
        DirectoryStream.Filter<Path> filter = IS_REGULAR_FILE
            .and(IS_READABLE)
            .and(largerThan(100_000))
            .and(magicNumber(0x89, 'P', 'N', 'G'))
            .and(globMatches("*.png"))
            .and(regexContains("-"));

        Path dir = Paths.get("src/main/resources/");

        try (DirectoryStream<Path> entries = Files.newDirectoryStream(dir, filter)) {
            entries.forEach(System.out::println);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
