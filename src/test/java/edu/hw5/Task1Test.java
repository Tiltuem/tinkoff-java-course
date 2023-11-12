package edu.hw5;

import java.util.HashMap;
import java.util.Map;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

public class Task1Test {
    @Test
    @DisplayName("calculateAverageTimeSpent")
    void calculateAverageTimeSpent() {
        HashMap<String[], String> test = new HashMap<>() {{
            put(new String[] {"2022-03-12, 10:00 - 2022-03-12, 23:00",
                "2022-04-01, 21:30 - 2022-04-02, 01:30", "2022-01-01, 11:20 - 2022-01-01, 11:50"}, "5ч 50м");
            put(new String[] {"2022-03-12, 20:20 - 2022-03-12, 23:50", "2022-04-01, 21:30 - 2022-04-02, 01:20"}, "3ч 40м");
            put(new String[] {"2022-03-12, 20:20 - 2022-03-12, 23:50"}, "3ч 30м");
        }};

        for (Map.Entry<String[], String> entry : test.entrySet()) {
            assertThat(Task1.calculateAverageTimeSpent(entry.getKey())).isEqualTo(entry.getValue());
        }
    }

    @Test
    @DisplayName("exceptionCalc")
    void exceptionCalc() {
        assertThatThrownBy(() -> {
            Task1.calculateAverageTimeSpent(new String[] {"2022-03-12, 20:20 - 2022-03-12, 23:50", "2022-04-adada21:30 - 2022-04-02, 01:20"});
        }).isInstanceOf(IllegalArgumentException.class)
            .hasMessageContaining("Invalid format");

        assertThatThrownBy(() -> {
            Task1.calculateAverageTimeSpent(null);
        }).isInstanceOf(NullPointerException.class)
            .hasMessageContaining("Invalid input");
    }
}
