package edu.hw7;

import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.IntStream;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@SuppressWarnings("MagicNumber")
public class Task4 {
    private static final int[] NUM_SIMULATION = {10000000, 100000000, 1000000000};
    private static final String PRINT = "Threads: %d, Time: %fs, Accuracy: %f, Acceleration: %f ";
    private static final Logger LOGGER = LogManager.getLogger();

    private Task4() {
    }

    public static void printMultiThread() {
        double elapsedTimeSecondsOld = 1;
        double elapsedTimeSeconds;
        double accuracy;
        double acceleration;
        long startTime;
        double piApproximation;
        long endTime;

        for (int totalCount : NUM_SIMULATION) {
            LOGGER.info("Number of simulations: " + totalCount);

            for (int numThreads = 1; numThreads <= 8; numThreads++) {
                startTime = System.currentTimeMillis();
                piApproximation = calculatePiMultiThread(totalCount, numThreads);
                endTime = System.currentTimeMillis();
                elapsedTimeSeconds = (endTime - startTime) / 1000.0;

                accuracy = Math.abs(Math.PI - piApproximation);
                acceleration = elapsedTimeSecondsOld / elapsedTimeSeconds;
                elapsedTimeSecondsOld = elapsedTimeSeconds;

                LOGGER.info(String.format(PRINT, numThreads, elapsedTimeSeconds, accuracy, acceleration));
            }
        }
    }

    public static double calculatePiSingleThread(int totalCount) {
        long circleCount = 0;
        double x;
        double y;
        double distance;
        Random random = new Random();

        for (int i = 0; i < totalCount; i++) {
            x = -1 + 2 * random.nextDouble();
            y = -1 + 2 * random.nextDouble();
            distance = Math.sqrt(x * x + y * y);

            if (distance <= 1) {
                circleCount++;
            }
        }

        return 4.0 * circleCount / totalCount;
    }

    public static double calculatePiMultiThread(int totalCount, int numThreads) {
        int simulationsPerThread = totalCount / numThreads;

        AtomicLong circleCount = new AtomicLong(0);

        IntStream.range(0, numThreads)
            .parallel()
            .forEach(threadNum -> {
                long localCircleCount = simulateMonteCarlo(simulationsPerThread);
                circleCount.addAndGet(localCircleCount);
            });

        return 4.0 * circleCount.get() / totalCount;
    }

    private static long simulateMonteCarlo(int totalCount) {
        long localCircleCount = 0;
        double x;
        double y;
        double distance;

        for (int i = 0; i < totalCount; i++) {
            x = ThreadLocalRandom.current().nextDouble(-1, 1);
            y = ThreadLocalRandom.current().nextDouble(-1, 1);

            distance = Math.sqrt(x * x + y * y);

            if (distance <= 1) {
                localCircleCount++;
            }
        }

        return localCircleCount;
    }
}
