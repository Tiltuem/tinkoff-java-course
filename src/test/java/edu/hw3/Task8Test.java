package edu.hw3;

import edu.hw3.task8.BackwardIterator;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class Task8Test {
    @Test
    @DisplayName("backwardIterator")
    void backwardIterator() {
        BackwardIterator<Integer> iterator = new BackwardIterator<>(List.of(1,2,3));

        for (int i = 3; i > 0; i--) {
            assertThat(iterator.hasNext()).isTrue();
            assertThat(iterator.next()).isEqualTo(i);
        }

        assertThat(iterator.hasNext()).isFalse();
    }
}
