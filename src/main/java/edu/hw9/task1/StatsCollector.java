package edu.hw9.task1;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class StatsCollector {
    private final Map<String, Stats> statisticsMap = new ConcurrentHashMap<>();
    private final ExecutorService executorService;

    public StatsCollector(int numThreads) {
        this.executorService = Executors.newFixedThreadPool(numThreads);
    }

    public void push(String metricName, double[] data) {
        executorService.submit(() -> {
            if (Objects.nonNull(data)) {
                statisticsMap.compute(metricName, (key, existingStats) -> {
                    if (existingStats == null) {
                        return new Stats(data);
                    } else {
                        double sum = Arrays.stream(data).sum();
                        double max = Arrays.stream(data).max().orElse(Double.NaN);
                        double min = Arrays.stream(data).min().orElse(Double.NaN);
                        existingStats.combine(sum, data.length, max, min);

                        return existingStats;
                    }
                });
            } else {
                throw new IllegalArgumentException("Invalid data");
            }
        });
    }

    public List<StatsResult> stats() throws InterruptedException {
        executorService.shutdown();
        executorService.awaitTermination(1, TimeUnit.MINUTES);

        return statisticsMap.entrySet().stream()
            .map(entry -> new StatsResult(entry.getKey(), entry.getValue()))
            .toList();
    }
}
