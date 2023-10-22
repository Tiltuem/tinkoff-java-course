package edu.hw2.Task3.connections.impl;

import edu.hw2.Task3.connections.Connection;
import edu.hw2.Task3.exceptions.ConnectionException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class FaultyConnection implements Connection {
    private static final Logger LOGGER = LogManager.getLogger();

    @Override
    public void execute(String command) {
        throw new ConnectionException("You have faulty connection");
    }

    @Override
    public void close() {
        LOGGER.info("Closing faulty connection");
    }
}
