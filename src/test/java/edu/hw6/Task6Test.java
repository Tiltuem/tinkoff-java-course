package edu.hw6;

import java.io.IOException;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.util.HashSet;
import java.util.Set;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class Task6Test {
    @Test
    @DisplayName("checkAllPorts")
    public void checkAllPorts() {
        Task6 portChecker = new Task6();
        Set<Boolean> tcpSet = new HashSet<>();
        Set<Boolean> udpSet = new HashSet<>();

        for (int port = 0; port <= 49151; port++) {
            tcpSet.add(portChecker.checkPort(port, "TCP"));
            udpSet.add(portChecker.checkPort(port, "UDP"));
        }

        assertThat(tcpSet.size()).isEqualTo(2);
        assertThat(udpSet.size()).isEqualTo(2);
    }

    @Test
    @DisplayName("checkOccupiedPort")
    public void checkOccupiedPort() {
        Task6 portChecker = new Task6();

        try (ServerSocket serverSocket = new ServerSocket(80);
             DatagramSocket datagramSocket = new DatagramSocket(80, InetAddress.getByName("localhost"))
        ) {
            assertThat(portChecker.checkPort(80, "TCP")).isFalse();
            assertThat(portChecker.checkPort(80, "UDP")).isFalse();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    @DisplayName("checkFreePort")
    public void checkFreePort() {
        Task6 portChecker = new Task6();

        assertThat(portChecker.checkPort(800, "UDP")).isTrue();
        assertThat(portChecker.checkPort(800, "TCP")).isTrue();
    }
}

