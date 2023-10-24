package edu.hw2.Task3.connectionManagers.impl;

import edu.hw2.Task3.connectionManagers.ConnectionManager;
import edu.hw2.Task3.connections.Connection;
import edu.hw2.Task3.connections.impl.FaultyConnection;

public class FaultyConnectionManager implements ConnectionManager {
    @Override
    public Connection getConnection() {
        return new FaultyConnection();
    }
}
