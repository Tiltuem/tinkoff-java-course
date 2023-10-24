package edu.hw2.Task3;

import edu.hw2.Task3.connectionManagers.ConnectionManager;
import edu.hw2.Task3.connections.Connection;
import edu.hw2.Task3.exceptions.ConnectionException;

public class PopularCommandExecutor {
    private final ConnectionManager manager;
    private final int maxAttempts;

    public PopularCommandExecutor(ConnectionManager manager, int maxAttempts) {
        this.manager = manager;
        this.maxAttempts = maxAttempts;
    }

    public void updatePackages() {
        tryExecute("apt update && apt upgrade -y");
    }

    private void tryExecute(String command) {
        int attempt = 0;

        while (attempt < maxAttempts) {
            try (Connection connection = manager.getConnection()) {
                connection.execute(command);
                return;
            } catch (ConnectionException e) {
                attempt++;

                if (attempt >= maxAttempts) {
                    throw new ConnectionException("Failed to execute command: " + command, e);
                }
            } catch (Exception e) {
                throw new RuntimeException("Error executing command: " + command, e);
            }
        }
    }
}
