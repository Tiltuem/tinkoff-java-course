package edu.project2;

import edu.project2.models.Cell;
import edu.project2.models.Maze;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import java.util.Arrays;
import java.util.HashSet;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class Project2Test {
    @Test
    @DisplayName("testGenerate")
    public void testGenerate() {
        Maze maze = new Maze(10, 10);
        HashSet<Cell.Type> result = new HashSet<>() {
            {
                add(Cell.Type.PASSAGE);
                add(Cell.Type.WALL);
            }
        };
        HashSet<Cell.Type> uniqueType = new HashSet<>();

        for (Cell[] cells : maze.getGrid()) {
            for (Cell cell : cells) {
                uniqueType.add(cell.type());
            }
        }

        int entrance = (int) Arrays.stream(maze.getGrid()[0]).filter(cell -> cell.type() == Cell.Type.PASSAGE).count();
        int exit = (int) Arrays.stream(maze.getGrid()[maze.getGrid().length - 1]).filter(cell -> cell.type() == Cell.Type.PASSAGE).count();

        assertEquals(uniqueType, result);
        assertEquals(entrance, 1);
        assertEquals(exit, 1);
        assertThat(maze.isSolved()).isFalse();
    }

    @Test
    @DisplayName("solving")
    public void solving() {
        Maze maze = new Maze(10, 10);
        maze.findEscape();

        Cell[][] grid = maze.getGrid();
        HashSet<Cell.Type> result = new HashSet<>() {
            {
                add(Cell.Type.PASSAGE);
                add(Cell.Type.WALL);
                add(Cell.Type.STEP);
            }
        };
        HashSet<Cell.Type> uniqueType = new HashSet<>();

        for (Cell[] cells : grid) {
            for (Cell cell : cells) {
                uniqueType.add(cell.type());
            }
        }

        int entrance = (int) Arrays.stream(grid[0]).filter(cell -> cell.type() == Cell.Type.STEP).count();
        int exit = (int) Arrays.stream(grid[grid.length - 1]).filter(cell -> cell.type() == Cell.Type.STEP).count();

        assertEquals(uniqueType, result);
        assertEquals(entrance, 1);
        assertEquals(exit, 1);
        assertThat(maze.isSolved()).isTrue();
    }

    @Test
    @DisplayName("invalidArgument")
    public void invalidArgument() {
        assertThatThrownBy(() -> {
            new Maze(10, -1);
        }).isInstanceOf(IllegalArgumentException.class).hasMessageContaining("size < 3");
    }
}
