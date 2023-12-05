package edu.hw8.task2;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class FibonacciCalc {
    private static final Logger LOGGER = LogManager.getLogger();
    private static final int THREADS = 4;
    private static List<Long> fibonacciNumbers = new ArrayList<>();
    private static HashSet<Long> threadsId = new HashSet<>();

    private FibonacciCalc() {
    }

    public static List<Long> getFibonacciNumbers() {
        return fibonacciNumbers;
    }

    public static HashSet<Long> getThreadsId() {
        return threadsId;
    }

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
                    fibonacciNumbers.add(result);
                    threadsId.add(Thread.currentThread().getId());
                    LOGGER.info(
                        "Fibonacci(" + currentN + ") = " + result + " (Thread " + Thread.currentThread().getId() + ")");
                });
            }
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

    public void clear() {
        fibonacciNumbers = new ArrayList<>();
        threadsId = new HashSet<>();
    }
}
