package edu.hw5;

import java.util.HashMap;
import java.util.Map;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import static org.assertj.core.api.Assertions.assertThat;

public class Task7Test {
    static Arguments[] test() {
        HashMap<String, Boolean> test = new HashMap<>() {{
            put("10011", true);
            put("000", true);
            put("0011", false);
            put("avc", false);
        }};

        return new Arguments[] {
            Arguments.of(test)
        };
    }

    @ParameterizedTest
    @MethodSource("test")
    @DisplayName("containsThreeCharAndThirdIsZero")
    void containsMinThreeCharAndThirdIsZero(HashMap<String, Boolean> test) {
        for (Map.Entry<String, Boolean> entry : test.entrySet()) {
            assertThat(Task7.containsMinThreeCharAndThirdIsZero(entry.getKey())).isEqualTo(entry.getValue());
        }
    }

    @ParameterizedTest
    @MethodSource("test")
    @DisplayName("firstCharEqualLast")
    void firstCharEqualLast(HashMap<String, Boolean> test) {
        for (Map.Entry<String, Boolean> entry : test.entrySet()) {
            assertThat(Task7.firstCharEqualLast(entry.getKey())).isEqualTo(entry.getValue());
        }
    }

    @Test
    @DisplayName("lengthMinOneMaxThree")
    void lengthMinOneMaxThree() {
        HashMap<String, Boolean> test = new HashMap<>() {{
            put("1", true);
            put("000", true);
            put("0011", false);
            put("121", false);
        }};

        for (Map.Entry<String, Boolean> entry : test.entrySet()) {
            assertThat(Task7.lengthMinOneMaxThree(entry.getKey())).isEqualTo(entry.getValue());
        }
    }
}
