package edu.project3;


import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

public class Project3Test {
    @Test
    @DisplayName("NginxLogAnalyzerTest")
    void NginxLogAnalyzerTest() {
        NginxLogAnalyzer.main(new String[] {"--path",
            "https://raw.githubusercontent.com/elastic/examples/master/Common%20Data%20Formats/nginx_logs/nginx_logs"});
        File fileMd = new File("src/main/resources/reports", "logs.md");
        assertThat(fileMd.exists()).isTrue();

        NginxLogAnalyzer.main(new String[] {"--path",
            "https://raw.githubusercontent.com/elastic/examples/master/Common%20Data%20Formats/nginx_logs/nginx_logs", "--format", "adoc"});
        File fileAdoc = new File("src/main/resources/reports", "logs.adoc");
        assertThat(fileAdoc.exists()).isTrue();

        File fileMdValid = new File("log.md");
        writeMd(fileMdValid);

        try {
            byte[] content1 = Files.readAllBytes(fileMd.toPath());
            byte[] content2 = Files.readAllBytes(fileMdValid.toPath());
            assertThat(content1).isEqualTo(content2);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Test
    @DisplayName("exceptionTest")
    void exceptionTest() {
        assertThatThrownBy(() -> {
            NginxLogAnalyzer.main(new String[] {"--ph", "adad"});
        }).isInstanceOf(IllegalArgumentException.class).hasMessageContaining("Invalid arguments");

        assertThatThrownBy(() -> {
            NginxLogAnalyzer.main(new String[] {"--format", "adoc"});
        }).isInstanceOf(IllegalArgumentException.class).hasMessageContaining("Invalid arguments");
    }

    private void writeMd(File fileMdValid) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(fileMdValid))) {
            writer.write(
                """
                        ### Общая информация

                        | Метрика           | Значение          |
                        |:-----------------:|:-----------------:|
                        | Файл(ы)           | ``            |
                        | Начальная дата    | null            |
                        | Конечная дата     | null            |
                        | Кол-во запросов   | 51462            |
                        | Средний размер ответа     | 659509 b |
                        ### Коды ответа

                        | Код | Имя | Количество |
                        |:------:|:--------:|:--------:|
                        |404 |Not Found |33876 |
                        |304 |Not Modified |13330 |
                        |200 |OK |4028 |
                        |206 |Partial Content |186 |
                        |403 |Forbidden |38 |
                        |416 |Range Not Satisfiable |4 |
                        ### Запрашиваемые ресурсы

                        | РЕСУРС | Количество |
                        |:-----------:|:--------:|
                        | /product_1 | 30285 |
                        | /product_2 | 21104 |
                        | /product_3 | 73 |
                        ### Количество запросов с IP-адреса

                        | IP | Количество |
                        |:-----------:|:--------:|
                        | 216.46.173.126 | 2350 |
                        | 180.179.174.219 | 1720 |
                        | 204.77.168.241 | 1439 |
                        | 65.39.197.164 | 1365 |
                        | 80.91.33.133 | 1202 |
                        ### HTTP-запросы

                        | ЗАПРОС | Количество |
                        |:-----------:|:--------:|
                        | GET | 51379 |
                        | HEAD | 83 |
                        ### User-Agent

                        | АГЕНТ | Количество |
                        |:-----------:|:--------:|
                        | Debian APT-HTTP/1.3 (1.0.1ubuntu2) | 11830 |
                        | Debian APT-HTTP/1.3 (0.9.7.9) | 11365 |
                        | Debian APT-HTTP/1.3 (0.8.16~exp12ubuntu10.21) | 6719 |
                        | Debian APT-HTTP/1.3 (0.8.16~exp12ubuntu10.16) | 5740 |
                        | Debian APT-HTTP/1.3 (0.8.16~exp12ubuntu10.22) | 3855 |
                        """
            );
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
