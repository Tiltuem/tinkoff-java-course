package edu.project3;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URI;
import java.net.URL;
import java.net.URLConnection;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.OffsetDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class LogUtils {
    private static final DateTimeFormatter FORMATTER =
        DateTimeFormatter.ofPattern("dd/MMM/yyyy:HH:mm:ss Z", Locale.ENGLISH);
    private static final DateTimeFormatter FORMATTER_INFO =
        DateTimeFormatter.ofPattern("yyyy-MM-dd:HH:mm:ss Z", Locale.ENGLISH);

    private LogUtils() {
    }

    public static List<LogRecord> loadLogRecordsFile(List<String> pathArg, String fromArg, String toArg) {
        List<LogRecord> logRecords = new ArrayList<>();
        File log;

        for (String path : pathArg) {
            log = new File(path);
            URI uri = log.toURI();

            try (Stream<String> lines = Files.lines(Paths.get(uri))) {
                logRecords.addAll(lines.map(LogUtils::parseLogLine)
                    .filter(line -> fromArg == null
                        || line.timestamp().isAfter(OffsetDateTime.parse(fromArg, FORMATTER_INFO)))
                    .filter(line -> toArg == null
                        || line.timestamp().isBefore(OffsetDateTime.parse(toArg, FORMATTER_INFO)))
                    .toList());
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }

        return logRecords;
    }

    public static List<LogRecord> loadLogRecordsHTTP(String url, String fromArg, String toArg) {
        List<LogRecord> logRecords;
        URL logUrl;
        URLConnection connection;

        try {
            logUrl = new URL(url);
            connection = logUrl.openConnection();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        try (InputStream inputStream = connection.getInputStream();
             InputStreamReader inputStreamReader = new InputStreamReader(inputStream, StandardCharsets.UTF_8);
             BufferedReader bufferedReader = new BufferedReader(inputStreamReader)) {

            logRecords = bufferedReader.lines()
                .map(LogUtils::parseLogLine)
                .filter(line -> fromArg == null
                    || line.timestamp().isAfter(OffsetDateTime.parse(fromArg, FORMATTER_INFO)))
                .filter(line -> toArg == null || line.timestamp().isBefore(OffsetDateTime.parse(toArg, FORMATTER_INFO)))
                .collect(Collectors.toList());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        return logRecords;
    }

    @SuppressWarnings("MagicNumber")
    private static LogRecord parseLogLine(String line) {
        String pattern =
            "^(\\S+) (\\S+) (\\S+) \\[([^\\]]+)\\] \"(\\S+) (\\S+) (\\S+)\" (\\d+) (\\d+) \"([^\"]*)\" \"([^\"]*)\"";
        Pattern logPattern = Pattern.compile(pattern);
        Matcher matcher = logPattern.matcher(line);

        if (matcher.matches()) {
            String clientIp = matcher.group(1);
            OffsetDateTime timestamp = OffsetDateTime.parse(matcher.group(4), FORMATTER);
            String method = matcher.group(5);
            String resource = matcher.group(6).substring(matcher.group(6).lastIndexOf("/"));
            int statusCode = Integer.parseInt(matcher.group(8));
            int responseSize = Integer.parseInt(matcher.group(9));
            String userAgent = matcher.group(11);

            return new LogRecord(clientIp, timestamp, method, resource, statusCode, responseSize, userAgent);
        } else {
            throw new IllegalArgumentException("Invalid log entry format");
        }
    }
}
