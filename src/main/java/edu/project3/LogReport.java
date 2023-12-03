package edu.project3;

import java.util.Comparator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.stream.Collectors;

public class LogReport {
    private final List<String> files;
    private final String startDate;
    private final String endDate;
    private final List<LogRecord> logRecords;

    public LogReport(List<LogRecord> logRecords, List<String> files, String startDate, String endDate) {
        this.logRecords = logRecords;
        this.files = files;
        this.startDate = startDate;
        this.endDate = endDate;
    }

    public LogReport(List<LogRecord> logRecords, String file, String startDate, String endDate) {
        this.logRecords = logRecords;
        this.files = List.of(file);
        this.startDate = startDate;
        this.endDate = endDate;
    }

    public String getFiles() {
        return String.join(", ", files);
    }

    public String getStartDate() {
        return startDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public int getTotalRequests() {
        return logRecords.size();
    }

    public double getAverageResponseSize() {
        return logRecords.stream()
            .mapToLong(LogRecord::responseSize)
            .average()
            .orElse(0);
    }

    public Map<String, Long> getTopRequestedResources(int limit) {
        Map<String, Long> mapResult = logRecords.stream()
            .collect(Collectors.groupingBy(LogRecord::resource, Collectors.counting()));

        TreeMap<String, Long> orderMapResult =
            new TreeMap<>(Comparator.<String, Long>comparing(mapResult::get).reversed());

        orderMapResult.putAll(mapResult);

        mapResult = new LinkedHashMap<>();
        int count = 0;
        for (Map.Entry<String, Long> entry : orderMapResult.entrySet()) {
            if (count < limit) {
                mapResult.put(entry.getKey(), entry.getValue());
                count++;
            } else {
                break;
            }
        }

        return mapResult;
    }

    public Map<Integer, Long> getResponseStatusCounts() {
        Map<Integer, Long> mapResult = logRecords.stream()
            .collect(Collectors.groupingBy(LogRecord::statusCode, Collectors.counting()));

        TreeMap<Integer, Long> orderMapResult =
            new TreeMap<>(Comparator.<Integer, Long>comparing(mapResult::get).reversed());
        orderMapResult.putAll(mapResult);

        return orderMapResult;
    }

    public Map<String, Long> getTopClientIpCounts(int limit) {
        Map<String, Long> mapResult = logRecords.stream()
            .collect(Collectors.groupingBy(LogRecord::clientIp, Collectors.counting()));

        TreeMap<String, Long> orderMapResult =
            new TreeMap<>(Comparator.<String, Long>comparing(mapResult::get).reversed());

        orderMapResult.putAll(mapResult);

        mapResult = new LinkedHashMap<>();
        int count = 0;
        for (Map.Entry<String, Long> entry : orderMapResult.entrySet()) {
            if (count < limit) {
                mapResult.put(entry.getKey(), entry.getValue());
                count++;
            } else {
                break;
            }
        }

        return mapResult;
    }

    public Map<String, Long> getMethodCounts() {
        Map<String, Long> mapResult = logRecords.stream()
            .collect(Collectors.groupingBy(LogRecord::method, Collectors.counting()));

        TreeMap<String, Long> orderMapResult =
            new TreeMap<>(Comparator.<String, Long>comparing(mapResult::get).reversed());
        orderMapResult.putAll(mapResult);

        return orderMapResult;
    }

    public Map<String, Long> getTopUserAgentCounts(int limit) {
        Map<String, Long> mapResult = logRecords.stream()
            .collect(Collectors.groupingBy(LogRecord::userAgent, Collectors.counting()));

        TreeMap<String, Long> orderMapResult =
            new TreeMap<>(Comparator.<String, Long>comparing(mapResult::get).reversed());

        orderMapResult.putAll(mapResult);

        mapResult = new LinkedHashMap<>();
        int count = 0;
        for (Map.Entry<String, Long> entry : orderMapResult.entrySet()) {
            if (count < limit) {
                mapResult.put(entry.getKey(), entry.getValue());
                count++;
            } else {
                break;
            }
        }

        return mapResult;
    }
}
