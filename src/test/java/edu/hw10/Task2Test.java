package edu.hw10;

import edu.hw10.task2.CacheProxy;
import edu.hw10.task2.FibCalculator;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

import static org.assertj.core.api.Assertions.assertThat;

public class Task2Test {
    @Test
    @DisplayName("test caching")
    void testCaching() throws IOException {
        FibCalculator fibCalculator = new FibCalculator() {
            @Override
            public long fib(int number) {
                if (number <= 1) {
                    return number;
                } else {
                    return fib(number - 1) + fib(number - 2);
                }
            }
        };

        FibCalculator proxy = CacheProxy.create(fibCalculator, FibCalculator.class, "cache.txt");
        Path pathToCache = Path.of("cache.txt");

        long startTime = System.nanoTime();
        Long result1 = proxy.fib(15);
        long endTime = System.nanoTime();
        long resultTime = endTime - startTime;

        startTime = System.nanoTime();
        Long result2 = proxy.fib(15);
        endTime = System.nanoTime();

        assertThat(result1).isEqualTo(result2);
        assertThat(resultTime > endTime - startTime).isTrue();
        assertThat(Files.exists(pathToCache)).isTrue();

        Files.deleteIfExists(pathToCache);
    }
}
