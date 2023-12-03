package edu.hw8.task2;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class FibonacciCalc {
    private static final Logger LOGGER = LogManager.getLogger();
    private static final int THREADS = 8;
    private static final int TIME = 2000;

    private FibonacciCalc() {}

    public static void calculate(int n) {
        if (n < 0) {
            throw new IllegalArgumentException("Negative n");
        }

        try (FixedThreadPool threadPool = FixedThreadPool.create(THREADS)) {
            threadPool.start();

            for (int i = 0; i < n; i++) {
                final int currentN = i;
                threadPool.execute(() -> {
                    long result = parallelFibonacci(currentN);
                    LOGGER.info(
                        "Fibonacci(" + currentN + ") = " + result + " (Thread " + Thread.currentThread().getId() + ")");
                });
            }

            Thread.sleep(TIME);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static long parallelFibonacci(int n) {
        if (n <= 1) {
            return n;
        }

        FibonacciTask fibonacciTask = new FibonacciTask(n);

        return fibonacciTask.compute();
    }
}
