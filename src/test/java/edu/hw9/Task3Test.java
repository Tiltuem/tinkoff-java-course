package edu.hw9;

import edu.hw9.task3.ParallelDFS;
import edu.project2.models.Cell;
import edu.project2.models.Maze;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import static org.assertj.core.api.Assertions.assertThat;

public class Task3Test {
    static Arguments[] maze() {
        Maze maze = new Maze(100, 100);

        return new Arguments[] {Arguments.of(maze)};
    }

    @ParameterizedTest
    @MethodSource("maze")
    @DisplayName("testSolverWithSolution")
    public void testSolverWithSolution(Maze maze) {
        ParallelDFS solver = new ParallelDFS(maze.getGrid(), maze.getEntrance(), maze.getExit());
        boolean solutionFound = solver.solve();

        assertThat(solutionFound).isTrue();
        assertThat(maze.getExit().type()).isEqualTo(Cell.Type.STEP);
    }
    @Test
    @DisplayName("testSolverWithoutSolution")
    public void testSolverWithoutSolution() {
        Cell[][] grid = {
            {new Cell(0, 0, Cell.Type.WALL), new Cell(0, 1, Cell.Type.PASSAGE), new Cell(0, 2, Cell.Type.WALL)},
            {new Cell(1, 0, Cell.Type.WALL), new Cell(1, 1, Cell.Type.PASSAGE), new Cell(1, 2, Cell.Type.WALL)},
            {new Cell(2, 0, Cell.Type.WALL), new Cell(2, 1, Cell.Type.WALL), new Cell(2, 2, Cell.Type.WALL)}
        };

        ParallelDFS solver = new ParallelDFS(grid, new Cell(0, 0, Cell.Type.WALL), new Cell(2, 2, Cell.Type.WALL));
        boolean solutionFound = solver.solve();

        assertThat(solutionFound).isFalse();
    }

}
