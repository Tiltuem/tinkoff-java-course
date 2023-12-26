package edu.hw8.task1;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;

public class Handler {
    private static final int SIZE_BUFFER = 1024;

    private static final Map<String, String> QUOTE_MAP = new HashMap<>() {
        {
            put("личности", "Не переходи на личности там, где их нет");
            put(
                "оскорбления",
                "Если твои противники перешли на личные оскорбления, будь уверен — твоя победа не за горами"
            );
            put(
                "глупый",
                "А я тебе говорил, что ты глупый? Так вот, я забираю свои слова обратно... Ты просто бог идиотизма."
            );
            put("интеллект", "Чем ниже интеллект, тем громче оскорбления");
        }
    };

    private Handler() {}

    public static void handleClient(Socket socket) {
        try {
            InputStream inputStream = socket.getInputStream();
            OutputStream outputStream = socket.getOutputStream();

            byte[] buffer = new byte[SIZE_BUFFER];
            int bytesRead = inputStream.read(buffer);

            if (bytesRead != -1) {
                String request = new String(buffer, 0, bytesRead).trim();
                String response = QUOTE_MAP.getOrDefault(request, "Цитата не найдена");
                outputStream.write(response.getBytes());
            }

            socket.close();
        } catch (IOException e) {
            throw new RuntimeException();
        }
    }

}
