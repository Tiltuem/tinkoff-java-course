package edu.hw8;

import edu.hw8.task2.FibonacciCalc;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class Task2Test {
    private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
    private final PrintStream originalOut = System.out;

    @BeforeEach
    public void setUpStreams() {
        System.setOut(new PrintStream(outContent));
    }

    @AfterEach
    public void restoreStreams() {
        System.setOut(originalOut);
    }

    @Test
    @DisplayName("fibonacciCalc")
    public void fibonacciCalc() {
        int n = 5;

        FibonacciCalc.calculate(n);
        String[] outputLines = outContent.toString().split("\\n");
        assertThat(n).isEqualTo(outputLines.length);

        for (int i = 0; i < n; i++) {
            assertTrue(outContent.toString().contains("Fibonacci(" + i + ")"));
        }
    }

}
