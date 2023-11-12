package edu.hw5;

import java.util.HashMap;
import java.util.Map;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class Task4Test {
    @Test
    @DisplayName("isValidPassword")
    void isValidPassword() {
        HashMap<String, Boolean> test = new HashMap<>() {{
            put("abc#", true);
            put("~!@#$%^&*|", true);
            put("fwfwfew", false);
            put("~abc", true);
        }};

        for (Map.Entry<String, Boolean> entry : test.entrySet()) {
            assertThat(Task4.isValidPassword(entry.getKey())).isEqualTo(entry.getValue());
        }
    }
}
