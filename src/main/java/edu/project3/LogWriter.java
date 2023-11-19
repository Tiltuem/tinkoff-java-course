package edu.project3;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class LogWriter {
    @SuppressWarnings("MagicNumber")
    private static final HashMap<Integer, String> STATUS_CODE = new HashMap<>() {
        {
            put(200, "OK");
            put(206, "Partial Content");
            put(304, "Not Modified");
            put(400, "Bad Request");
            put(403, "Forbidden");
            put(404, "Not Found");
            put(416, "Range Not Satisfiable");
            put(500, "Internal Server Error");
            put(502, "Bad Gateway");
        }
    };
    private static final String PATH_TO_STORAGE = "src/main/resources/reports/";
    private static final String EXTENSION_ADOC = "logs.adoc";
    private static final String EXTENSION_MD = "logs.md";
    private static final String AGENT_STATE = "Агент";
    private static final String RESOURCES_STATE = "Ресурс";
    private static final String UNKNOWN = "UNKNOWN";

    private static final int LIMIT = 5;
    private static final String TABLE_ADOC = "|===\n";
    private static final String HEADER_TABLE_ADOC = """
        [cols=\\"^,^\\",options=\\"header\\",]
        |===
        |%S |Количество
        """;
    private static final String HEADER_TABLE_MD = """
        | %S | Количество |
        |:-----------:|:--------:|
        """;

    private LogWriter() {
    }

    public static void writeMarkdownToFile(LogReport report) {
        String filePath = PATH_TO_STORAGE + EXTENSION_MD;

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath))) {
            writer.write("### Общая информация\n\n");
            writer.write(String.format(
                """
                    | Метрика           | Значение          |
                    |:-----------------:|:-----------------:|
                    | Файл(ы)           | `%s`            |
                    | Начальная дата    | %s            |
                    | Конечная дата     | %s            |
                    | Кол-во запросов   | %d            |
                    | Средний размер ответа     | %d b |
                    """,
                String.join("\n", report.getFiles()),
                report.getStartDate(),
                report.getEndDate(),
                report.getTotalRequests(),
                (int) report.getAverageResponseSize()
            ));

            writer.write("### Коды ответа\n\n");
            StringBuilder responseStatus =
                new StringBuilder("""
                    | Код | Имя | Количество |
                    |:------:|:--------:|:--------:|
                    """);
            for (Map.Entry<Integer, Long> entry : report.getResponseStatusCounts().entrySet()) {
                responseStatus.append(String.format(
                    "|%d |%s |%d |\n",
                    entry.getKey(),
                    STATUS_CODE.getOrDefault(entry.getKey(), UNKNOWN),
                    entry.getValue()
                ));
            }
            writer.write(responseStatus.toString());

            writer.write("### Запрашиваемые ресурсы\n\n");
            writeState(report.getTopRequestedResources(LIMIT), RESOURCES_STATE, writer);

            writer.write("### Количество запросов с IP-адреса\n\n");
            writeState(report.getTopClientIpCounts(LIMIT), "IP", writer);

            writer.write("### HTTP-запросы\n\n");
            writeState(report.getMethodCounts(), "Запрос", writer);

            writer.write("### User-Agent\n\n");
            writeState(report.getTopUserAgentCounts(LIMIT), AGENT_STATE, writer);

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private static void writeState(Map<String, Long> report, String name, BufferedWriter writer) throws IOException {
        StringBuilder sb = new StringBuilder(String.format(HEADER_TABLE_MD, name));
        for (Map.Entry<String, Long> entry : report.entrySet()) {
            sb
                .append("| ")
                .append(entry.getKey())
                .append(" | ")
                .append(entry.getValue())
                .append(" |\n");
        }
        writer.write(sb.toString());
    }

    public static void writeAdocToFile(LogReport report) {
        String filePath = PATH_TO_STORAGE + EXTENSION_ADOC;

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath))) {
            writer.write("== Общая информация\n\n");
            writer.write(TABLE_ADOC);
            writer.write(String.format(
                """
                    |Метрика |Значение
                    |Файл(ы) |%s
                    |Начальная дата |%s
                    |Конечная дата |%s
                    |Кол-во запросов |%d
                    |Средний размер ответа |%d b
                    |===
                    """,
                String.join("\n", report.getFiles()),
                report.getStartDate(),
                report.getEndDate(),
                report.getTotalRequests(),
                (int) report.getAverageResponseSize()
            ));

            writer.write("== Коды ответа\n\n");
            writer.write(TABLE_ADOC);
            writer.write("|Код |Значение |Количество\n");
            for (Map.Entry<Integer, Long> entry : report.getResponseStatusCounts().entrySet()) {
                writer.write(String.format(
                    "|%d |%s |%d \n",
                    entry.getKey(),
                    STATUS_CODE.getOrDefault(entry.getKey(), UNKNOWN),
                    entry.getValue()
                ));
            }
            writer.write(TABLE_ADOC);

            writer.write("== Запрашиваемые ресурсы\n\n");
            writeStateAdoc(report.getTopRequestedResources(LIMIT), RESOURCES_STATE, writer);

            writer.write("== Количество запросов с IP-адреса\n\n");
            writeStateAdoc(report.getTopClientIpCounts(LIMIT), "IP", writer);

            writer.write("== HTTP-запросы\n\n");
            writeStateAdoc(report.getMethodCounts(), "Метод", writer);

            writer.write("== User-Agent\n\n");
            writeStateAdoc(report.getTopUserAgentCounts(LIMIT), AGENT_STATE, writer);

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private static void writeStateAdoc(Map<String, Long> report, String name, BufferedWriter writer)
        throws IOException {
        writer.write(String.format(HEADER_TABLE_ADOC, name));
        for (Map.Entry<String, Long> entry : report.entrySet()) {
            writer.write(String.format("|%s |%d\n", entry.getKey(), entry.getValue()));
        }
        writer.write(TABLE_ADOC);
    }
}
