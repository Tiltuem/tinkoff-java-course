package edu.hw3;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class Task3 {
    private Task3() {
    }

    public static Map<Object, Integer> frequencyDictionary(Object[] objects) {
        if (Objects.isNull(objects) || objects.length == 0) {
            return new HashMap<>();
        }

        HashMap<Object, Integer> dictionary = new HashMap<>();

        for (Object obj : objects) {
            dictionary.put(obj, dictionary.getOrDefault(obj, 0) + 1);
        }

        return dictionary;
    }
}
