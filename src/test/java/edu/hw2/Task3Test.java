package edu.hw2;

import edu.hw2.Task3.connectionManagers.ConnectionManager;
import edu.hw2.Task3.connectionManagers.impl.DefaultConnectionManager;
import edu.hw2.Task3.connectionManagers.impl.FaultyConnectionManager;
import edu.hw2.Task3.connections.Connection;
import edu.hw2.Task3.connections.impl.FaultyConnection;
import java.util.HashSet;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import static org.assertj.core.api.Assertions.assertThat;

public class Task3Test {
    static Arguments[] faultyConnectionManager() {
        return new Arguments[] {
            Arguments.of(new FaultyConnectionManager()),
        };
    }

    static Arguments[] defaultConnectionManager() {
        return new Arguments[] {
            Arguments.of(new DefaultConnectionManager(50)),
        };
    }

    @ParameterizedTest
    @MethodSource("faultyConnectionManager")
    @DisplayName("faultyManager")
    void faultyManager(ConnectionManager manager) {
        Connection connection;
        for (int i = 0; i < 10; i++) {
            connection = manager.getConnection();
            assertThat(connection).isInstanceOf(FaultyConnection.class);
        }
    }

    @ParameterizedTest
    @MethodSource("defaultConnectionManager")
    @DisplayName("defaultManager")
    void defaultManager(ConnectionManager manager) {
        Connection connection;
        HashSet<String> connections = new HashSet<>();

        for (int i = 0; i < 100; i++) {
            connection = manager.getConnection();
            connections.add(connection.getClass().getName());
        }

        assertThat(connections.size()).isEqualTo(2);
    }

}
