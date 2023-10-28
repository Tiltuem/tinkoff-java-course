package edu.hw3;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

@SuppressWarnings("MagicNumber")
public class Task4 {
    private static final int MIN_VALUE = 1;
    private static final int MAX_VALUE = 3999;

    private static final HashMap<Integer, String> NUM_TO_ROMAN_MAP = new LinkedHashMap<>() {
        {
            put(1000, "M");
            put(900, "CM");
            put(500, "D");
            put(400, "CD");
            put(100, "C");
            put(90, "XC");
            put(50, "L");
            put(40, "XL");
            put(10, "X");
            put(9, "IX");
            put(5, "V");
            put(4, "IV");
            put(1, "I");
        }
    };

    private Task4() {
    }

    public static String toRoman(int n) {
        if (n < MIN_VALUE || n > MAX_VALUE) {
            throw new IllegalArgumentException("The number must belong to the interval [1;4000)");
        }

        StringBuilder result = new StringBuilder();
        int arabNum = n;

        for (Map.Entry<Integer, String> entry : NUM_TO_ROMAN_MAP.entrySet()) {
            while (arabNum >= entry.getKey()) {
                result.append(entry.getValue());
                arabNum -= entry.getKey();
            }
        }

        return result.toString();
    }
}
