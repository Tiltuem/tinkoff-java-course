package edu.project1.dictionary;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.Random;
import org.jetbrains.annotations.NotNull;

public class DictionaryImpl implements Dictionary {
    private static final Random RANDOM = new Random();
    private final List<String> words;

    public DictionaryImpl() {
        try {
            words = Files.readAllLines(Paths.get("src/main/resources/words.txt"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public @NotNull String randomWord() {
        if (words.isEmpty()) {
            throw new RuntimeException("Нет слов в файле");
        }

        return words.get(RANDOM.nextInt(words.size()));
    }
}
