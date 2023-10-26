package edu.hw1;

import edu.hw1.Task4;
import edu.hw1.Task5;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import java.util.HashMap;
import java.util.Map;
import static org.assertj.core.api.Assertions.assertThat;

public class Task5Test {
    @Test
    @DisplayName("isPalindrome")
    void isPalindrome() {
        HashMap<Integer, Boolean> test = new HashMap<>() {{
            put(11211230, true);
            put(13001120, true);
            put(11, true);
            put(1, false);
            put(-12121, false);
            put(1213121, true);
        }};

        for (Map.Entry<Integer, Boolean> entry : test.entrySet()) {
            assertThat(Task5.isPalindrome(entry.getKey())).isEqualTo(entry.getValue());
        }
    }
}
