package edu.hw5;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class Task2Test {
    @Test
    @DisplayName("findFridayThe13th")
    void findFridayThe13th() {
        HashMap<Integer, List<LocalDate>> test = new HashMap<>() {{
            put(1925, List.of(LocalDate.of(1925, 2, 13), LocalDate.of(1925, 3, 13), LocalDate.of(1925, 11, 13)));
            put(2024, List.of(LocalDate.of(2024, 9, 13), LocalDate.of(2024, 12, 13)));
        }};

        for (Map.Entry<Integer, List<LocalDate>> entry : test.entrySet()) {
            assertEquals(Task2.findFridayThe13th(entry.getKey()), entry.getValue());
        }
    }

    @Test
    @DisplayName("nextFridayThe13th")
    void nextFridayThe13th() {
        assertThat(Task2.nextFridayThe13th(LocalDate.of(1925, 2, 25)))
            .isEqualTo(LocalDate.of(1925, 3, 13));
    }

    @Test
    @DisplayName("exceptionFind")
    void exceptionFind() {
        assertThatThrownBy(() -> {
            Task2.findFridayThe13th(-1);
        }).isInstanceOf(IllegalArgumentException.class)
            .hasMessageContaining("Invalid year");

    }
}
