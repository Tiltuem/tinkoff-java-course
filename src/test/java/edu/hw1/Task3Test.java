package edu.hw1;

import edu.hw1.Task2;
import edu.hw1.Task3;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import static org.assertj.core.api.Assertions.assertThat;

public class Task3Test {
    @Test
    @DisplayName("isNestable")
    void isNestable() {
        LinkedHashMap<int[], int[]> test = new LinkedHashMap<>() {{
            put(new int[] {1, 2, 3}, new int[] {0, 1, 2, 3, 4, 5});
            put(new int[] {1, 2, 3}, new int[] {0, 1, 2, 3});
            put(new int[] {1, 2, 3}, new int[] {2, 3, 4, 5});
            put(new int[] {}, new int[] {-10, 10});
            put(new int[] {1, 2, 10}, new int[] {});
            put(new int[] {4, 8, -1}, new int[] {0, 10, -2, 3, 4, 5});
            put(null, new int[] {0, 10, -2, 3, 4, 5});
        }};

        boolean[] result = new boolean[] {true, false, false, true, false, true, false};
        int i = 0;

        for (Map.Entry<int[], int[]> entry : test.entrySet()) {
            assertThat(Task3.isNestable(entry.getKey(), entry.getValue())).isEqualTo(result[i]);
            i++;
        }
    }
}
