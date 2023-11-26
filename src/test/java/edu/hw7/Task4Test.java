package edu.hw7;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class Task4Test {
    @Test
    @DisplayName("calculatePiMultiThread")
    public void calculatePiMultiThread() {
        boolean result;
        double first = Math.abs(Math.PI - Task4.calculatePiMultiThread(1000, 8));
        double second = Math.abs(Math.PI - Task4.calculatePiMultiThread(100000000, 8));
        result = first > second;

        assertThat(result).isTrue();
    }
}
