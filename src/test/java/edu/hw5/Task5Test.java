package edu.hw5;

import java.util.HashMap;
import java.util.Map;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class Task5Test {
    @Test
    @DisplayName("isNumberValid")
    void isNumberValid() {
        HashMap<String, Boolean> test = new HashMap<>() {{
            put("А123ВЕ777", true);
            put("О777ОО177", true);
            put("123АВЕ777", false);
            put("~А123ВГ77", false);
        }};

        for (Map.Entry<String, Boolean> entry : test.entrySet()) {
            assertThat(Task5.isNumberValid(entry.getKey())).isEqualTo(entry.getValue());
        }
    }
}
