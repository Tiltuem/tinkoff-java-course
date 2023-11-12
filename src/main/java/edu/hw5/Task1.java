package edu.hw5;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Task1 {
    private static final int SECONDS_PER_HOUR = 3600;
    private static final int SECONDS_PER_MINUTE = 60;

    private Task1() {
    }

    public static String calculateAverageTimeSpent(String[] input) {
        if (Objects.isNull(input)) {
            throw new NullPointerException("Invalid input");
        }
        long totalSeconds = 0;
        int countVisitors = 0;

        Pattern pattern = Pattern.compile("^\\d+-\\d{2}-\\d{2}, \\d{2}:\\d{2} - \\d+-\\d{2}-\\d{2}, \\d{2}:\\d{2}$");
        Matcher matcher;

        for (String str : input) {
            matcher = pattern.matcher(str);
            if (!matcher.matches()) {
                throw new IllegalArgumentException("Invalid format");
            }

            String[] times = str.split(" - ");
            LocalDateTime start = LocalDateTime.parse(times[0].replace(", ", "T"));
            LocalDateTime end = LocalDateTime.parse(times[1].replace(", ", "T"));

            Duration duration = Duration.between(start, end);
            totalSeconds += duration.getSeconds();
            countVisitors++;
        }

        long averageSeconds = totalSeconds / countVisitors;
        long hours = averageSeconds / SECONDS_PER_HOUR;
        long minutes = (averageSeconds % SECONDS_PER_HOUR) / SECONDS_PER_MINUTE;

        return String.format("%sч %sм", hours, minutes);
    }
}
