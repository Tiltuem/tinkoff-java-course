package edu.hw6;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class DiskMap implements Map<String, String> {
    private static final Logger LOGGER = LogManager.getLogger();
    private final File file;

    public DiskMap(String filePath) {
        this.file = new File(filePath);
    }

    @Override
    public int size() {
        return readFromFile().size();
    }

    @Override
    public boolean isEmpty() {
        return readFromFile().isEmpty();
    }

    @Override
    public boolean containsKey(Object key) {
        return readFromFile().containsKey(key);
    }

    @Override
    public boolean containsValue(Object value) {
        return readFromFile().containsValue(value);
    }

    @Override
    public String get(Object key) {
        return readFromFile().get(key);
    }

    @Override
    public String put(String key, String value) {
        Map<String, String> map = readFromFile();
        String oldValue = map.put(key, value);
        writeToFile(map);

        return oldValue;
    }

    @Override
    public String remove(Object key) {
        Map<String, String> map = readFromFile();
        String removedValue = map.remove(key);
        writeToFile(map);

        return removedValue;
    }

    @Override
    public void putAll(Map<? extends String, ? extends String> map) {
        Map<String, String> oldMap = readFromFile();
        oldMap.putAll(map);
        writeToFile(oldMap);
    }

    @Override
    public void clear() {
        writeToFile(new HashMap<>());
    }

    @Override
    public Set<String> keySet() {
        return readFromFile().keySet();
    }

    @Override
    public Collection<String> values() {
        return readFromFile().values();
    }

    @Override
    public Set<Entry<String, String>> entrySet() {
        return readFromFile().entrySet();
    }

    private Map<String, String> readFromFile() {
        Map<String, String> map = new HashMap<>();

        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line = reader.readLine();

            while (Objects.nonNull(line)) {
                String[] pair = line.split(":");

                if (pair.length == 2) {
                    map.put(pair[0], pair[1]);
                }
                line = reader.readLine();
            }
        } catch (IOException e) {
            LOGGER.error("Failed to read file");
        }
        return map;
    }

    private void writeToFile(Map<String, String> map) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(file))) {
            for (Entry<String, String> entry : map.entrySet()) {
                writer.write(entry.getKey() + ":" + entry.getValue());
                writer.newLine();
            }
        } catch (IOException e) {
            LOGGER.error("Failed to write file");
        }
    }
}
