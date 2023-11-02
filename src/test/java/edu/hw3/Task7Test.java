package edu.hw3;

import edu.hw3.task7.NullComparator;
import java.util.TreeMap;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class Task7Test {
    @Test
    @DisplayName("nullComparator")
    void nullComparator() {
        TreeMap<String, String> tree = new TreeMap<>(new NullComparator<>());
        tree.put(null, "test");
        tree.put("test", null);
        tree.put("a", "a");
        tree.put("b", "b");

        assertThat(tree.containsKey(null)).isTrue();
        assertThat(tree.size()).isEqualTo(4);
    }
}
