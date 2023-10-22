package edu.project1.dictionary;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.Random;
import org.jetbrains.annotations.NotNull;

public class DictionaryImpl implements Dictionary {
    @Override
    public @NotNull String randomWord() {
        Random random = new Random();
        List<String> words;

        try {
            words = Files.readAllLines(Paths.get("src/main/resources/words.txt"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        if (words.isEmpty()) {
            throw new RuntimeException("Нет слов в файле");
        }

        return words.get(random.nextInt(words.size()));
    }
}
