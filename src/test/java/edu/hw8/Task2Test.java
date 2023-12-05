package edu.hw8;

import edu.hw8.task2.FibonacciCalc;
import java.util.Collections;
import java.util.List;
import java.util.Set;
import org.junit.jupiter.api.DisplayName;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

public class Task2Test {
    @Test
    @DisplayName("fibonacciCalcTest")
    public void fibonacciCalcTest() {
        FibonacciCalc.calculate(10);
        List<Long> expectedNum = List.of(0L, 1L, 1L, 2L, 3L, 5L, 8L, 13L, 21L, 34L);

        List<Long> actualNum = FibonacciCalc.getFibonacciNumbers();
        Collections.sort(actualNum);
        Set<Long> actualThreads = FibonacciCalc.getThreadsId();

        assertThat(actualNum).isEqualTo(expectedNum);
        assertThat(actualThreads.size()).isEqualTo(4);
    }

    @Test
    @DisplayName("exceptionTest")
    void exceptionTest() {
        assertThatThrownBy(() -> {
            FibonacciCalc.calculate(-21312);
        }).isInstanceOf(IllegalArgumentException.class).hasMessageContaining("Negative n");
    }
}
