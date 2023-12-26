package edu.hw7;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

public class Task1Test {
    @Test
    @DisplayName("testThreadSafeCounter")
    public void testThreadSafeCounter() throws InterruptedException {
        int numThreads = 5;
        CountDownLatch latch = new CountDownLatch(numThreads);
        ExecutorService executorService = Executors.newFixedThreadPool(numThreads);

        for (int i = 0; i < numThreads; i++) {
            executorService.execute(() -> {
                Task1.incrementCounter(1);
                latch.countDown();
            });
        }

        latch.await();
        executorService.shutdown();

        assertThat(Task1.getCounter()).isEqualTo(5);
    }

    @Test
    @DisplayName("exception")
    public void exception() {
        assertThatThrownBy(() -> {
            Task1.incrementCounter(-1);
        }).isInstanceOf(IllegalArgumentException.class).hasMessageContaining("Negative numThreads");
    }
}
