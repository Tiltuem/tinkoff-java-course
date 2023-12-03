package edu.hw8.task1;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.util.Scanner;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class Client {
    private static final Logger LOGGER = LogManager.getLogger();
    private static final int PORT = 8000;
    private static final int SIZE_BUFFER = 1024;

    private Client() {}

    public static void startClient() {
        try (Scanner scanner = new Scanner(System.in)) {
            LOGGER.info("Введите ваше имя: ");
            LOGGER.info("Для остановки напишите exit");
            String name = scanner.nextLine();
            while (true) {
                Socket socket = new Socket("localhost", PORT);
                LOGGER.info(name);
                String request = scanner.nextLine();

                if (request.equals("exit")) {
                    socket.close();
                    break;
                }

                OutputStream outputStream = socket.getOutputStream();
                outputStream.write(request.getBytes());

                InputStream inputStream = socket.getInputStream();
                byte[] buffer = new byte[SIZE_BUFFER];
                int bytesRead = inputStream.read(buffer);

                if (bytesRead != -1) {
                    String response = new String(buffer, 0, bytesRead).trim();
                    LOGGER.info("Сервер: " + response);
                }

                socket.close();
            }
        } catch (IOException e) {
            throw new RuntimeException();
        }
    }
}
