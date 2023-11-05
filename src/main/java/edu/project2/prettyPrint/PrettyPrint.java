package edu.project2.prettyPrint;

import edu.project2.models.Cell;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import static edu.project2.models.Cell.Type.STEP;
import static edu.project2.models.Cell.Type.WALL;

public class PrettyPrint {
    private static final Logger LOGGER = LogManager.getLogger();

    private PrettyPrint() {}

    public static void printMaze(Cell[][] grid) {
        StringBuilder result = new StringBuilder();

        for (Cell[] row : grid) {
            for (Cell cell : row) {
                if (cell.type() == WALL) {
                    result.append("██");
                } else if (cell.type() == STEP) {
                    result.append("**");
                } else {
                    result.append("  ");
                }
            }
            result.append('\n');
        }

        LOGGER.info("Лабиринт \n" + result);
    }
}
