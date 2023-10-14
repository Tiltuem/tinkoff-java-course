package edu;

import edu.hw1.Task4;
import edu.hw1.Task5;
import edu.hw1.Task6;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import java.util.HashMap;
import java.util.Map;
import static org.assertj.core.api.Assertions.assertThat;

public class Task6Test {
    @Test
    @DisplayName("numberOfStepsForKaprekar")
    void numberOfStepsForKaprekar() {
        HashMap<Integer, Integer> test = new HashMap<>() {{
            put(3524, 3);
            put(6621, 5);
            put(1234, 3);
            put(-1121, -1);
            put(987, -1);
            put(10000, -1);
            put(6174, 0);
            put(3333, -1);
        }};

        for (Map.Entry<Integer, Integer> entry : test.entrySet()) {
            assertThat(Task6.numberOfStepsForKaprekar(entry.getKey())).isEqualTo(entry.getValue());
            Task6.setCount(0);
        }
    }
}
