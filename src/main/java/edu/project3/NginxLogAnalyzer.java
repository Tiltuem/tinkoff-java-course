package edu.project3;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.GnuParser;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;
import static edu.project3.LogWriter.writeAdocToFile;
import static edu.project3.LogWriter.writeMarkdownToFile;

public class NginxLogAnalyzer {
    private static final String EXTENSION_TXT = ".txt";
    private static final String EXTENSION_LOG = ".log";
    private static final String INVALID_ARGUMENTS = "Invalid arguments";
    private static final ArrayList<String> FILES = new ArrayList<>();
    private static String path;
    private static String from;
    private static String to;
    private static String format;

    private NginxLogAnalyzer() {
    }

    @SuppressWarnings("UncommentedMain")
    public static void main(String[] args) {
        try {
            parseArgs(args);

            List<LogRecord> logRecords;
            if (path.endsWith("*")) {
                checkDirectory();
                logRecords = LogUtils.loadLogRecordsFile(FILES, from, to);
            } else if (path.startsWith("http")) {
                logRecords = LogUtils.loadLogRecordsHTTP(path, from, to);
            } else {
                FILES.add(path);
                logRecords = LogUtils.loadLogRecordsFile(FILES, from, to);
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

    @SuppressWarnings("MultipleStringLiterals")
    private static void parseArgs(String[] args) {
        Options options = new Options();
        options.addRequiredOption("path", "path", true, "Путь к NGINX лог-файлам (локальный шаблон или URL)");
        options.addOption("from", true, "Временной параметр from ");
        options.addOption("to", true, "Временной параметр to");
        options.addOption("format", true, "Формат вывода результата (markdown или adoc)");

        CommandLineParser parser = new GnuParser();
        try {
            CommandLine cmd = parser.parse(options, args);

            path = cmd.getOptionValue("path");
            from = cmd.getOptionValue("from");
            to = cmd.getOptionValue("to");
            format = cmd.getOptionValue("format");
        } catch (ParseException e) {
            throw new IllegalArgumentException(INVALID_ARGUMENTS);
        }
    }

    private static void checkDirectory() {
        File directory = new File(path.substring(0, path.length() - 1));
        File[] filesInDirectory =
            directory.listFiles((dir, name) -> name.endsWith(EXTENSION_TXT) || name.endsWith(EXTENSION_LOG));

        if (Objects.isNull(filesInDirectory)) {
            throw new IllegalArgumentException("No matching files");
        }

        for (File file : filesInDirectory) {
            FILES.add(file.toString());
        }
    }
}
