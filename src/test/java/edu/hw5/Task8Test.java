package edu.hw5;

import java.util.HashMap;
import java.util.Map;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class Task8Test {
    @Test
    @DisplayName("isOddLength")
    void isOddLength() {
        HashMap<String, Boolean> test = new HashMap<>() {{
            put("10011", true);
            put("000", true);
            put("0011", false);
            put("avc", false);
        }};

        for (Map.Entry<String, Boolean> entry : test.entrySet()) {
            assertThat(Task8.isOddLength(entry.getKey())).isEqualTo(entry.getValue());
        }
    }

    @Test
    @DisplayName("parityBasedOnFirstChar")
    void parityBasedOnFirstChar() {
        HashMap<String, Boolean> test = new HashMap<>() {{
            put("01111", true);
            put("1100", true);
            put("1", false);
            put("123", false);
        }};

        for (Map.Entry<String, Boolean> entry : test.entrySet()) {
            assertThat(Task8.parityBasedOnFirstChar(entry.getKey())).isEqualTo(entry.getValue());
        }
    }

    @Test
    @DisplayName("countZeroMultipleThree")
    void countZeroMultipleThree() {
        HashMap<String, Boolean> test = new HashMap<>() {{
            put("100110", true);
            put("000", true);
            put("0011", false);
            put("3100a", false);
        }};

        for (Map.Entry<String, Boolean> entry : test.entrySet()) {
            assertThat(Task8.countZeroMultipleThree(entry.getKey())).isEqualTo(entry.getValue());
        }
    }

    @Test
    @DisplayName("everyOddCharIsOne")
    void everyOddCharIsOne() {
        HashMap<String, Boolean> test = new HashMap<>() {{
            put("10101", true);
            put("1", true);
            put("0011", false);
            put("123", false);
        }};

        for (Map.Entry<String, Boolean> entry : test.entrySet()) {
            assertThat(Task8.everyOddCharIsOne(entry.getKey())).isEqualTo(entry.getValue());
        }
    }

    @Test
    @DisplayName("containsMinTwoZeroAndMaxOne")
    void containsMinTwoZeroAndMaxOne() {
        HashMap<String, Boolean> test = new HashMap<>() {{
            put("100", true);
            put("000", true);
            put("0011", false);
            put("1200", false);
        }};

        for (Map.Entry<String, Boolean> entry : test.entrySet()) {
            assertThat(Task8.containsMinTwoZeroAndMaxOne(entry.getKey())).isEqualTo(entry.getValue());
        }
    }

    @Test
    @DisplayName("noConsecutiveOne")
    void noConsecutiveOne() {
        HashMap<String, Boolean> test = new HashMap<>() {{
            put("10001", true);
            put("1", true);
            put("11", false);
            put("102", false);
        }};

        for (Map.Entry<String, Boolean> entry : test.entrySet()) {
            assertThat(Task8.noConsecutiveOne(entry.getKey())).isEqualTo(entry.getValue());
        }
    }
}
