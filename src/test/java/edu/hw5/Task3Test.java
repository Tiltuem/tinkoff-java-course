package edu.hw5;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class Task3Test {
    @Test
    @DisplayName("parseDate")
    void parseDate() {
        HashMap<String, Optional<LocalDate>> test = new HashMap<>() {{
            put("2020-10-10", Optional.of(LocalDate.of(2020, 10, 10)));
            put("2020-12-2", Optional.of(LocalDate.of(2020, 12, 2)));
            put("1/3/1976", Optional.of(LocalDate.of(1976, 1, 3)));
            put("1/3/20", Optional.of(LocalDate.of(2020, 1, 3)));
            put("tomorrow", Optional.of(LocalDate.now().plusDays(1)));
            put("today", Optional.of(LocalDate.now()));
            put("yesterday", Optional.of(LocalDate.now().minusDays(1)));
            put("1 day ago", Optional.of(LocalDate.now().minusDays(1)));
            put("2234 days ago", Optional.of(LocalDate.now().minusDays(2234)));
            put("2020-1-1", Optional.empty());
        }};

        for (Map.Entry<String, Optional<LocalDate>> entry : test.entrySet()) {
            assertThat(Task3.parseDate(entry.getKey())).isEqualTo(entry.getValue());
        }
    }
}
