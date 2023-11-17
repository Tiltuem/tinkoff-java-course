package edu.hw6;


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

}

