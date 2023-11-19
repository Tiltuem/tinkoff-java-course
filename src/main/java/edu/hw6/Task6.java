package edu.hw6;

import java.io.IOException;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.UnknownHostException;
import java.util.HashMap;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@SuppressWarnings("MagicNumber")
public class Task6 {
    private static final String FREE_PORT = "%s\t %d\t Free\t %s";
    private static final String OCCUPIED_PORT = "%s\t %d\t Occupied\t %s";
    private static final String TCP_PORT = "TCP";
    private static final String UDP_PORT = "UDP";

    private static final HashMap<Integer, String> SERVICES = new HashMap<>() {
        {
            put(135, "EPMAP");
            put(137, "NetBIOS Name Service");
            put(138, "NetBIOS Datagram Service");
            put(139, "NetBIOS Session Service");
            put(445, "Microsoft-DS Active Directory");
            put(843, "Adobe Flash");
            put(1521, "Oracle Database");
            put(1900, "Simple Service Discovery Protocol (SSDP)");
            put(3702, "Dynamic Web Services Discovery");
            put(5353, "Multicast DNS");
            put(5355, "Link-Local Multicast Name Resolution (LLMNR)");
            put(5432, "PostgreSQL Database");
            put(17500, "Dropbox");
            put(27017, "MongoDB");
            put(49150, "inspider");
        }
    };
    private static final Logger LOGGER = LogManager.getLogger();

    public void checkAllPorts() {
        for (int port = 0; port <= 49151; port++) {
            if (checkPort(port, TCP_PORT)) {
                LOGGER.info(String.format(FREE_PORT, TCP_PORT, port, SERVICES.get(port)));
            } else {
                LOGGER.error(String.format(OCCUPIED_PORT, TCP_PORT, port, SERVICES.get(port)));
            }

            if (checkPort(port, UDP_PORT)) {
                LOGGER.info(String.format(FREE_PORT, UDP_PORT, port, SERVICES.get(port)));
            } else {
                LOGGER.error(String.format(OCCUPIED_PORT, UDP_PORT, port, SERVICES.get(port)));
            }
        }
    }

    public boolean checkPort(int port, String type) {
        InetAddress inetAddress;
        try {
            inetAddress = InetAddress.getByName("localhost");
        } catch (UnknownHostException e) {
            throw new RuntimeException(e);
        }

        switch (type) {
            case TCP_PORT -> {
                try {
                    ServerSocket serverSocket = new ServerSocket(port, 1, inetAddress);
                    serverSocket.close();

                    return true;
                } catch (IOException e) {
                    return false;
                }
            }
            case UDP_PORT -> {
                try {
                    DatagramSocket datagramSocket = new DatagramSocket(port, inetAddress);
                    datagramSocket.close();

                    return true;
                } catch (IOException e) {
                    return false;
                }
            }
            default -> throw new IllegalArgumentException();
        }
    }

}
