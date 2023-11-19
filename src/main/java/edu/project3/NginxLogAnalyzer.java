package edu.project3;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import static edu.project3.LogUtils.loadLogRecordsFile;
import static edu.project3.LogUtils.loadLogRecordsHTTP;
import static edu.project3.LogWriter.writeAdocToFile;
import static edu.project3.LogWriter.writeMarkdownToFile;

public class NginxLogAnalyzer {
    private static final String EXTENSION_TXT = ".txt";
    private static final String EXTENSION_LOG = ".log";
    private static final String INVALID_ARGUMENTS = "Invalid arguments";
    private static final ArrayList<String> FILES = new ArrayList<>();
    private static List<LogRecord> logRecords = new ArrayList<>();
    private static String from;
    private static String to;
    private static String format;

    private NginxLogAnalyzer() {
    }

    @SuppressWarnings("UncommentedMain")
    public static void main(String[] args) {
        try {
            if (!parseArgs(args)) {
                logRecords = loadLogRecordsFile(FILES, from, to);
            }

            LogReport report = new LogReport(logRecords, FILES, from, to);

            if (Objects.nonNull(format) && format.equals("adoc")) {
                writeAdocToFile(report);
            } else {
                writeMarkdownToFile(report);
            }
        } catch (Exception e) {
            throw new IllegalArgumentException(INVALID_ARGUMENTS);
        }
    }

    private static boolean parseArgs(String[] args) {
        if (args[0].equals("--path")) {
            int count = 1;

            if (args[1].endsWith("*")) {
                File directory = new File(args[1].substring(0, args[1].length() - 1));
                File[] filesInDirectory =
                    directory.listFiles((dir, name) -> name.endsWith(EXTENSION_TXT) || name.endsWith(EXTENSION_LOG));

                for (File file : filesInDirectory) {
                    FILES.add(file.getName());
                }

            } else if (args.length > count && args[count].endsWith(EXTENSION_TXT)
                || args[count].endsWith(EXTENSION_LOG)) {

                while (args.length > count
                    && (args[count].endsWith(EXTENSION_TXT)
                    || args[count].endsWith(EXTENSION_LOG)
                   )) {
                    FILES.add(args[count]);
                    count++;
                }
            }
            count++;
            while (args.length > count) {
                switch (args[count]) {
                    case "--from" -> {
                        count++;
                        from = args[count];
                    }
                    case "--to" -> {
                        count++;
                        to = args[count];
                    }
                    case "--format" -> {
                        count++;
                        format = args[count];
                    }
                    default -> {

                    }
                }
                count++;
            }

            if (args[1].startsWith("http")) {
                logRecords = loadLogRecordsHTTP(args[1], from, to);
                return true;
            }
        } else {
            throw new IllegalArgumentException(INVALID_ARGUMENTS);
        }

        return false;
    }
}
