package edu.hw5;

import java.util.HashMap;
import java.util.Map;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class Task6Test {
    @Test
    @DisplayName("isSubstring")
    void isSubstring() {
        HashMap<String[], Boolean> test = new HashMap<>() {{
            put(new String[]{"achfdbaabgabcaabg", "achfdbaabgabcaabg"}, true);
            put(new String[]{"abc", "abc"}, true);
            put(new String[]{"abc", "abzzzzc"}, true);
            put(new String[]{"abc", "abzzzz"}, false);
            put(new String[]{"abc", "cba"}, false);
        }};

        for (Map.Entry<String[], Boolean> entry : test.entrySet()) {
            assertThat(Task6.isSubstring(entry.getKey()[0], entry.getKey()[1])).isEqualTo(entry.getValue());
        }
    }
}
