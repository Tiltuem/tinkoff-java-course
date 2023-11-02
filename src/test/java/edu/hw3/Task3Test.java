package edu.hw3;

import java.util.HashMap;
import java.util.Map;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class Task3Test {
    @Test
    @DisplayName("frequencyDictionary")
    void frequencyDictionary() {
        HashMap<Object[], Map<Object, Integer>> test = new HashMap<>() {{
            put(new String[] {}, new HashMap<>());
            put(null, new HashMap<>());
            put(new String[] {"a", "bb", "a", "bb"}, Map.of("bb", 2, "a", 2));
            put(new Integer[] {0, 1, 2, 2, -1, -1}, Map.of(0, 1, 1, 1, 2, 2, -1, 2));
            put(new String[] {"код", "код", "код", "bug"}, Map.of("код", 3, "bug", 1));
            put(new Double[] {0.0, 0.0, 0.0, 0.0, 1.2}, Map.of(0.0, 4, 1.2, 1));
        }};

        for (Map.Entry<Object[], Map<Object, Integer>> entry : test.entrySet()) {
            assertEquals(Task3.frequencyDictionary(entry.getKey()), entry.getValue());
        }
    }
}
