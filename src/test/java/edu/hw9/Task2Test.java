package edu.hw9;

import edu.hw9.task2.DirectorySearchTask;
import edu.hw9.task2.FileSearchTask;
import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertArrayEquals;

public class Task2Test {
    @Test
    @DisplayName("testDirectorySearch")
    void testDirectorySearch() throws IOException {
        Path tempDirectory = Files.createTempDirectory("fileClonerTest");

        assertThat(DirectorySearchTask.countFiles(tempDirectory.toString())).isFalse();

        for (int i = 0; i < 1001; i++) {
            String filePath = tempDirectory + File.separator + "file_" + i + ".txt";
            File file = new File(filePath);
            file.createNewFile();
        }

        assertThat(DirectorySearchTask.countFiles(tempDirectory.toString())).isTrue();
    }

    @Test
    @DisplayName("testDirectorySearchWithSubDir")
    void testDirectorySearchWithSubDir() throws IOException {
        Path tempDirectory = Files.createTempDirectory("fileClonerTest");

        for (int i = 0; i < 3; i++) {
            File subfolder = new File(tempDirectory.toString(), "subfolder_" + i);
            subfolder.mkdirs();

            for (int j = 0; j < 400; j++) {
                String filePath = subfolder + File.separator + "file_" + j + ".txt";
                File file = new File(filePath);
                file.createNewFile();
            }
        }

        assertThat(DirectorySearchTask.countFiles(tempDirectory.toString())).isTrue();
    }

    @Test
    @DisplayName("testFileSearch")
    void testFileSearch() throws IOException {
        Path tempDirectory = Files.createTempDirectory("fileSearchTest");
        File subfolder = new File(tempDirectory.toString(), "subfolder");
        subfolder.mkdirs();

        String suitableFileFirst = tempDirectory + File.separator + "suitableFileFirst" + ".txt";
        String suitableFileSecond = tempDirectory + File.separator + "suitableFileSecond" + ".txt";
        String invalidFileFirst = tempDirectory + File.separator + "invalidFileFirst" + ".exe";
        String invalidFileSecond = subfolder.getAbsolutePath() + File.separator + "invalidFileSecond" + ".txt";

        try (RandomAccessFile firstFile = new RandomAccessFile(new File(suitableFileFirst), "rw");
             RandomAccessFile secondFile = new RandomAccessFile(new File(suitableFileSecond), "rw");
             RandomAccessFile thirdFile = new RandomAccessFile(new File(invalidFileFirst), "rw");
             RandomAccessFile fourFile = new RandomAccessFile(new File(invalidFileSecond), "rw");
        ) {
            firstFile.setLength(2000);
            secondFile.setLength(2000);
            thirdFile.setLength(2000);
            fourFile.setLength(100);
        } catch (IOException e) {
            e.printStackTrace();
        }

        List<File> actual = new ArrayList<>(List.of(new File(suitableFileSecond), new File(suitableFileFirst)));
        List<File> expected = FileSearchTask.searchFiles(tempDirectory.toString(), ".txt", 1000);
        Collections.sort(expected);
        Collections.sort(actual);

        assertThat(expected).isEqualTo(actual);
    }
}
