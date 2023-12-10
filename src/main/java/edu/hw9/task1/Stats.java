package edu.hw9.task1;

import java.util.Arrays;

public class Stats {
    private double sum;
    private long count;
    private double average;
    private double max;
    private double min;

    public Stats(double[] data) {
        sum = Arrays.stream(data).sum();
        count = data.length;
        average = sum / count;
        max = Arrays.stream(data).max().orElse(Double.NaN);
        min = Arrays.stream(data).min().orElse(Double.NaN);
    }

    public void combine(double otherSum, long otherCount, double otherMax, double otherMin) {
        this.sum += otherSum;
        this.count += otherCount;
        this.average = sum / count;
        this.max = Math.max(max, otherMax);
        this.min = Math.min(min, otherMin);
    }

    public double getSum() {
        return sum;
    }

    public long getCount() {
        return count;
    }

    public double getAverage() {
        return average;
    }

    public double getMax() {
        return max;
    }

    public double getMin() {
        return min;
    }
}
