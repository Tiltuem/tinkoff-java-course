package edu.hw6;

import java.nio.file.Files;
import java.nio.file.Path;

public class Task2 {
    private Task2() {
    }

    public static String cloneFile(Path filePath) {
        String fileName = filePath.getFileName().toString();
        String name = fileName.substring(0, fileName.lastIndexOf("."));
        String extension = fileName.substring(fileName.lastIndexOf("."));

        int count = 1;
        Path newFilePath = filePath.resolveSibling(name + extension);

        while (Files.exists(newFilePath)) {
            newFilePath =
                filePath.resolveSibling(name + " - копия" + (count > 1 ? " (" + count + ")" : "") + extension);
            count++;
        }

        try {
            Files.copy(filePath, newFilePath);
            return newFilePath.getFileName().toString();
        } catch (Exception e) {
            throw new RuntimeException();
        }
    }
}
