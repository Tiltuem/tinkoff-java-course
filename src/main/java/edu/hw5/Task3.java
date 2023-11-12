package edu.hw5;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Task3 {
    private static final Map<String, DateTimeFormatter> MAP_OF_DATE_FORMAT = new HashMap<>() {
        {
            put("\\d{4}-\\d{2}-\\d{2}", DateTimeFormatter.ofPattern("yyyy-MM-dd"));
            put("\\d{4}-\\d{2}-\\d{1}", DateTimeFormatter.ofPattern("yyyy-MM-d"));
            put("\\d{1,2}/\\d{1,2}/\\d{4}", DateTimeFormatter.ofPattern("M/d/yyyy"));
            put("\\d{1,2}/\\d{1,2}/\\d{2}", DateTimeFormatter.ofPattern("M/d/yy"));
        }
    };

    private Task3() {
    }

    public static Optional<LocalDate> parseDate(String string) {
        Pattern pattern;
        Matcher matcher;

        for (Map.Entry<String, DateTimeFormatter> format : MAP_OF_DATE_FORMAT.entrySet()) {
            pattern = Pattern.compile(format.getKey());
            matcher = pattern.matcher(string);
            if (matcher.matches()) {
                return Optional.of(LocalDate.parse(string, format.getValue()));
            }
        }

        pattern = Pattern.compile("^\\d+ day[s]? ago");
        matcher = pattern.matcher(string);
        if (matcher.matches()) {
            return Optional.of(LocalDate.now().minusDays(Integer.parseInt(string.split(" ")[0])));
        }

        Optional<LocalDate> result;
        switch (string.toLowerCase()) {
            case "today" -> {
                result = Optional.of(LocalDate.now());
            }
            case "tomorrow" -> {
                result = Optional.of(LocalDate.now().plusDays(1));
            }
            case "yesterday" -> {
                result = Optional.of(LocalDate.now().minusDays(1));
            }
            default -> {
                result = Optional.empty();
            }
        }

        return result;
    }
}
