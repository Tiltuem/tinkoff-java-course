package edu.hw2.Task3.connections.impl;

import edu.hw2.Task3.connections.Connection;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class StableConnection implements Connection {
    private static final Logger LOGGER = LogManager.getLogger();

    @Override
    public void execute(String command) {
        LOGGER.info("Executing command: " + command);
    }

    @Override
    public void close() {
        LOGGER.info("Closing stable connection");
    }
}
