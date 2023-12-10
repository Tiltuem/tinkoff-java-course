package edu.hw9.task3;

import edu.project2.models.Cell;
import java.util.concurrent.RecursiveTask;

public class ParallelDFS {
    private final Cell[][] grid;
    private final int startRow;
    private final int startCol;
    private final int endRow;
    private final int endCol;

    public ParallelDFS(Cell[][] grid, Cell start, Cell end) {
        this.grid = grid;
        this.startRow = start.row();
        this.startCol = start.col();
        this.endRow = end.row();
        this.endCol = end.col();
    }

    public boolean solve() {
        MazeTask mazeTask = new MazeTask(startRow, startCol);

        return mazeTask.invoke();
    }

    private class MazeTask extends RecursiveTask<Boolean> {
        private final int currentRow;
        private final int currentCol;

        MazeTask(int currentRow, int currentCol) {
            this.currentRow = currentRow;
            this.currentCol = currentCol;
        }

        @Override
        protected Boolean compute() {
            if (isValidPosition(currentRow, currentCol)
                || grid[currentRow][currentCol].type().equals(Cell.Type.WALL)
                || grid[currentRow][currentCol].type().equals(Cell.Type.STEP)) {

                return false;
            }

            if (currentRow == endRow && currentCol == endCol) {
                grid[currentRow][currentCol] = new Cell(currentRow, currentCol, Cell.Type.STEP);

                return true;
            }

            grid[currentRow][currentCol] = new Cell(currentRow, currentCol, Cell.Type.STEP);

            MazeTask upTask = new MazeTask(currentRow - 1, currentCol);
            MazeTask downTask = new MazeTask(currentRow + 1, currentCol);
            MazeTask leftTask = new MazeTask(currentRow, currentCol - 1);
            MazeTask rightTask = new MazeTask(currentRow, currentCol + 1);

            invokeAll(upTask, downTask, leftTask, rightTask);

            return upTask.join() || downTask.join() || leftTask.join() || rightTask.join();
        }

        private boolean isValidPosition(int row, int col) {
            return row < 0 || row >= grid.length || col < 0 || col >= grid[0].length;
        }
    }
}

