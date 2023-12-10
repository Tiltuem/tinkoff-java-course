package edu.hw9.task1;

public record StatsResult(String metricName, Stats stats) {
    @Override
    public String toString() {
        return String.format("Metric: %s, Sum: %.2f, Avg: %.2f, Max: %.2f, Min: %.2f",
            metricName, stats.getSum(), stats.getAverage(), stats.getMax(), stats.getMin()
        );
    }
}
