package edu.hw8;

import edu.hw8.task1.Client;
import edu.hw8.task1.Server;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.List;
import static org.assertj.core.api.Assertions.assertThat;

public class Task1Test {

    @Test
    @DisplayName("handlerTest")
    void handlerTest()  {
        Thread serverThread = new Thread(Server::startServer);
        serverThread.start();

        InputStream originalIn = System.in;

        System.setIn(new ByteArrayInputStream("kirill\nвфаф\nличности\nexit".getBytes()));

        Client.startClient();
        List<String> expectedHistory = List.of("Цитата не найдена", "Не переходи на личности там, где их нет");
        List<String> actualHistory = Client.getHistory();
        assertThat(actualHistory).isEqualTo(expectedHistory);

        System.setIn(originalIn);
        serverThread.interrupt();
    }
}
