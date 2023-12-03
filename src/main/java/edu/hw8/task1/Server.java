package edu.hw8.task1;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import static edu.hw8.task1.Handler.handleClient;

public class Server {
    private static final Logger LOGGER = LogManager.getLogger();
    private static final int PORT = 8000;
    private static final int MAX_CONNECTIONS = 5;

    private Server() {}

    public static void startServer() {
        try (ServerSocket serverSocket = new ServerSocket(PORT)) {
            LOGGER.info("Сервер запущен");
            ExecutorService threadPool = Executors.newFixedThreadPool(MAX_CONNECTIONS);

            while (true) {
                Socket clientSocket = serverSocket.accept();

                if (threadPool.submit(() -> handleClient(clientSocket)).isDone()) {
                    LOGGER.info("Ожидание доступного слота для подключения...");
                }
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
