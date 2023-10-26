package edu.hw1;

import edu.hw1.Task1;
import edu.hw1.Task2;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import static org.assertj.core.api.Assertions.assertThat;

public class Task1Test {
    @Test
    @DisplayName("lengthInSeconds")
    void lengthInSeconds() {
        HashMap<String, Integer> test = new HashMap<>() {{
            put("0:0", 0);
            put("0:30", 30);
            put("10:00", 600);
            put("10:10", 610);
            put("10:-1", -1);
            put("-1:-1", -1);
            put("-0:0", -1);
            put("-1:10", -1);
            put("-110", -1);
            put("afdafa", -1);
        }};

        for (Map.Entry<String, Integer> entry : test.entrySet()) {
            assertThat(Task1.lengthInSeconds(entry.getKey())).isEqualTo(entry.getValue());
        }
    }
}
