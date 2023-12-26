package edu.hw8.task2;

import java.util.concurrent.RecursiveTask;

public class FibonacciTask extends RecursiveTask<Long> {
    private final int n;

    FibonacciTask(int n) {
        this.n = n;
    }

    @Override
    protected Long compute() {
        if (n <= 1) {
            return (long) n;
        }

        FibonacciTask firstTask = new FibonacciTask(n - 1);
        firstTask.fork();

        FibonacciTask secondTask = new FibonacciTask(n - 2);
        long secondResult = secondTask.compute();
        long firstResult = firstTask.join();

        return firstResult + secondResult;
    }
}
