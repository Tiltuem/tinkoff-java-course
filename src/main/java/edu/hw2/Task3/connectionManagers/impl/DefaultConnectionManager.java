package edu.hw2.Task3.connectionManagers.impl;

import edu.hw2.Task3.connectionManagers.ConnectionManager;
import edu.hw2.Task3.connections.Connection;
import edu.hw2.Task3.connections.impl.FaultyConnection;
import edu.hw2.Task3.connections.impl.StableConnection;
import java.util.Random;

public class DefaultConnectionManager implements ConnectionManager {
    private final Random random = new Random();
    private static final int BOUND_RANDOM = 100;
    private final int faultyChance;

    public DefaultConnectionManager(int faultyChance) {
        this.faultyChance = faultyChance;
    }

    @Override
    public Connection getConnection() {
        if (random.nextInt(BOUND_RANDOM) < faultyChance) {
            return new FaultyConnection();
        } else {
            return new StableConnection();
        }
    }
}
