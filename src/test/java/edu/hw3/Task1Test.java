package edu.hw3;

import java.util.HashMap;
import java.util.Map;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class Task1Test {
    @Test
    @DisplayName("atbashCipher")
    void atbashCipherTest() {
        HashMap<String, String> test = new HashMap<>() {{
            put("", "");
            put(null, "");
            put("abc", "zyx");
            put("zyx", "abc");
            put("Hello world!", "Svool dliow!");
            put("Привет!", "Привет!");
        }};

        for (Map.Entry<String, String> entry : test.entrySet()) {
            assertThat(Task1.atbashCipher(entry.getKey())).isEqualTo(entry.getValue());
        }
    }
}
