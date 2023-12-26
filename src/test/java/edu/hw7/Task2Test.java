package edu.hw7;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import java.math.BigInteger;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

public class Task2Test {
    @Test
    @DisplayName("testCalculateFactorial")
    public void testCalculateFactorial() {
        BigInteger expectedFactorial = calculateFactorial();
        BigInteger actualFactorial = Task2.calculateFactorial(15);

        assertThat(expectedFactorial).isEqualTo(actualFactorial);
    }

    @Test
    @DisplayName("exception")
    public void exception() {
        assertThatThrownBy(() -> {
            Task2.calculateFactorial(-1);
        }).isInstanceOf(IllegalArgumentException.class).hasMessageContaining("Negative n");
    }

    private BigInteger calculateFactorial() {
        BigInteger result = BigInteger.ONE;
        for (int i = 1; i <= 15; i++) {
            result = result.multiply(BigInteger.valueOf(i));
        }

        return result;
    }
}
