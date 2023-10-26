package edu.hw1;

import edu.hw1.Task7;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import java.util.LinkedHashMap;
import java.util.Map;
import static org.assertj.core.api.Assertions.assertThat;

public class Task7Test {
    @Test
    @DisplayName("rotateLeft")
    void rotateLeft() {
        LinkedHashMap<Integer, Integer> test = new LinkedHashMap<>() {{
            put(8, 1);
            put(17, 2);
            put(20, 3);
            put(0, 10);
            put(-2, 10);
            put(10, -2);
        }};

        int[] result = new int[] {1, 6, 5, 0, -1, -1};
        int i = 0;
        for (Map.Entry<Integer, Integer> entry : test.entrySet()) {
            assertThat(Task7.rotateLeft(entry.getKey(), entry.getValue())).isEqualTo(result[i]);
            i++;
        }
    }

    @Test
    @DisplayName("rotateRight")
    void rotateRight() {
        LinkedHashMap<Integer, Integer> test = new LinkedHashMap<>() {{
            put(8, 1);
            put(17, 3);
            put(20, 2);
            put(0, 0);
            put(2, -10);
            put(-2121, -2);
        }};

        int[] result = new int[] {4, 6, 5, 0, -1, -1};
        int i = 0;
        for (Map.Entry<Integer, Integer> entry : test.entrySet()) {
            assertThat(Task7.rotateRight(entry.getKey(), entry.getValue())).isEqualTo(result[i]);
            i++;
        }
    }
}
