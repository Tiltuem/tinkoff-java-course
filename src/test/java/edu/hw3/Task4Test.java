package edu.hw3;

import java.util.HashMap;
import java.util.Map;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

public class Task4Test {
    @Test
    @DisplayName("toRoman")
    void toRoman() {
        HashMap<Integer, String> test = new HashMap<>() {{
            put(1, "I");
            put(3999, "MMMCMXCIX");
            put(10, "X");
            put(44, "XLIV");
            put(999, "CMXCIX");
        }};

        for (Map.Entry<Integer, String> entry : test.entrySet()) {
            assertThat(Task4.toRoman(entry.getKey())).isEqualTo(entry.getValue());

        }
    }

    @Test
    @DisplayName("toRomanException")
    void toRomanException() {
        assertThatThrownBy(() -> {
            Task4.toRoman(-212);
        }).isInstanceOf(IllegalArgumentException.class)
            .hasMessageContaining("The number must belong to the interval [1;4000)");

        assertThatThrownBy(() -> {
            Task4.toRoman(4000);
        }).isInstanceOf(IllegalArgumentException.class)
            .hasMessageContaining("The number must belong to the interval [1;4000)");
    }
}
