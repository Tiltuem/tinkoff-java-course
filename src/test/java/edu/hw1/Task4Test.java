package edu.hw1;

import edu.hw1.Task2;
import edu.hw1.Task4;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import java.util.HashMap;
import java.util.Map;
import static org.assertj.core.api.Assertions.assertThat;

public class Task4Test {
    @Test
    @DisplayName("fixString")
    void fixString() {
        HashMap<String, String> test = new HashMap<>() {{
            put("214365", "123456");
            put("оПомигети псаривьтс ртко!и", "Помогите исправить строки!");
            put("badce", "abcde");
            put("", "");
            put("1", "1");
        }};

        for (Map.Entry<String, String> entry : test.entrySet()) {
            assertThat(Task4.fixString(entry.getKey())).isEqualTo(entry.getValue());
        }
    }
}
