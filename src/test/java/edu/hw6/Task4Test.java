package edu.hw6;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class Task4Test {
    @Test
    @DisplayName("compositionTest")
    void compositionTest() throws Exception {
        Path tempFile = Files.createTempFile("compositionTest", ".txt");
        Task4.composition(tempFile);

        assertThat(Files.exists(tempFile)).isTrue();
        assertTrue(Files.size(tempFile) > 0);

        String result = "Programming is learned by writing programs. ― Brian Kernighan" + System.lineSeparator();
        assertThat(Files.readString(tempFile)).isEqualTo(result);
    }

    @Test
    @DisplayName("exceptionTest")
    void exceptionTest() {
        Path nonExistentFile = Paths.get("/abc/abc/abc.txt");

        assertThrows(RuntimeException.class, () -> Task4.composition(nonExistentFile));
    }
}
