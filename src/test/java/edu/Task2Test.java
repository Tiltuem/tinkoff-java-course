package edu;

import edu.hw1.Task1;
import edu.hw1.Task2;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import java.util.HashMap;
import java.util.Map;
import static org.assertj.core.api.Assertions.assertThat;

public class Task2Test {
    @Test
    @DisplayName("numberOfDigits")
    void numberOfDigits() {
        HashMap<Integer, Integer> test = new HashMap<>() {{
            put(1, 1);
            put(-1, 1);
            put(987, 3);
            put(-1111, 4);
        }};

        for (Map.Entry<Integer, Integer> entry : test.entrySet()) {
            assertThat(Task2.numberOfDigits(entry.getKey())).isEqualTo(entry.getValue());
        }
    }
}
