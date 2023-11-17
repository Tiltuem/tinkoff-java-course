package edu.hw6;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class Task2Test {
    @Test
    @DisplayName("cloneFileTest")
    void cloneFile() {
        try {
            Path tempDirectory = Files.createTempDirectory("fileClonerTest");
            Path originalFilePath = tempDirectory.resolve("test.txt");
            Files.write(originalFilePath, "Test content".getBytes());

            String clonedFileName = Task2.cloneFile(originalFilePath);
            Path clonedFilePath = tempDirectory.resolve(clonedFileName);

            assertThat(Files.exists(clonedFilePath)).isTrue();
            assertThat(Files.readAllBytes(originalFilePath)).isEqualTo(Files.readAllBytes(clonedFilePath));

            Files.deleteIfExists(originalFilePath);
            Files.deleteIfExists(clonedFilePath);
            Files.deleteIfExists(tempDirectory);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    @DisplayName("cloneFileWithExistingCopiesTest")
    void cloneFileWithExistingCopiesTest() {
        try {
            Path tempDirectory = Files.createTempDirectory("fileClonerTest");
            Path originalFilePath = tempDirectory.resolve("test.txt");
            Files.write(originalFilePath, "Test content".getBytes());

            String firstClonedFileName = Task2.cloneFile(originalFilePath);
            Path firstClonedFilePath = tempDirectory.resolve(firstClonedFileName);
            assertThat(firstClonedFileName).isEqualTo("test - копия.txt");

            String secondClonedFileName = Task2.cloneFile(originalFilePath);
            Path secondClonedFilePath = tempDirectory.resolve(secondClonedFileName);
            assertThat(secondClonedFileName).isEqualTo("test - копия (2).txt");

            Files.deleteIfExists(originalFilePath);
            Files.deleteIfExists(firstClonedFilePath);
            Files.deleteIfExists(secondClonedFilePath);
            Files.deleteIfExists(tempDirectory);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
