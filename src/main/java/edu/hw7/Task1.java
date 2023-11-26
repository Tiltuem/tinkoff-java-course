package edu.hw7;

public class Task1 {
    private static int counter = 0;

    private Task1() {}

    public static void incrementCounter(int numThreads) {
        if (numThreads < 0) {
            throw new IllegalArgumentException("Negative numThreads");
        }

        Thread[] threads = new Thread[numThreads];

        for (int i = 0; i < numThreads; i++) {
            threads[i] = new Thread(() -> counter++);
            threads[i].start();
        }

        for (Thread thread : threads) {
            try {
                thread.join();
            } catch (InterruptedException e) {
                throw new RuntimeException();
            }
        }
    }

    public static int getCounter() {
        return counter;
    }
}
