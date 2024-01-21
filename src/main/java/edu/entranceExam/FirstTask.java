package edu.entranceExam;

import java.util.Scanner;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class FirstTask {
    private static final Logger LOGGER = LogManager.getLogger();

    private FirstTask() {

    }

    public static void countDatasets() {
        Scanner in = new Scanner(System.in);
        short countServers = in.nextShort();
        int countData = in.nextInt();
        short currentServer = countServers;

        while (countData >= currentServer) {
            countData -= currentServer;

            if (currentServer == 1) {
                currentServer = countServers;
            } else {
                currentServer--;
            }
        }

        in.close();
        LOGGER.info(countData);
    }
}
