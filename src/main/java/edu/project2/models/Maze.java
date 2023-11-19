package edu.project2.models;

import edu.project2.generate.PassageTree;
import edu.project2.prettyPrint.PrettyPrint;
import edu.project2.solving.Fugitive;
import java.util.function.Consumer;
import static edu.project2.models.Cell.Type.PASSAGE;
import static edu.project2.models.Cell.Type.WALL;

public final class Maze {
    private static final int MIN_SIZE = 3;
    private final int height;
    private final int width;
    private final Cell[][] grid;
    private boolean isSolved;

    public Maze(int height, int width) {
        if (height < MIN_SIZE || width < MIN_SIZE) {
            throw new IllegalArgumentException("size < 3");
        }

        this.height = height;
        this.width = width;
        this.isSolved = false;
        grid = new Cell[height][width];
        fillGrid();
    }

    private void fillGrid() {
        fillAlternately();
        fillGaps();
        makeEntranceAndExit();
        generatePassages();
    }

    private void putCell(int row, int column, Cell.Type type) {
        grid[row][column] = new Cell(row, column, type);
    }

    private void fillAlternately() {
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {

                if ((i & 1) == 0 || (j & 1) == 0) {
                    putCell(i, j, WALL);
                } else {
                    putCell(i, j, PASSAGE);
                }

            }
        }
    }

    private void fillGaps() {
        if (height % 2 == 0) {
            wallLastRow();
        }
        if (width % 2 == 0) {
            wallLastColumn();
        }
    }

    private void wallLastColumn() {
        for (int i = 0; i < height; i++) {
            putCell(i, width - 1, WALL);
        }
    }

    private void wallLastRow() {
        for (int i = 0; i < width; i++) {
            putCell(height - 1, i, WALL);
        }
    }

    @SuppressWarnings("MagicNumber")
    private int getExitColumn() {
        return width - 3 + width % 2;
    }

    private void makeEntranceAndExit() {
        putCell(0, 1, PASSAGE);

        putCell(height - 1, getExitColumn(), PASSAGE);
        if (height % 2 == 0) {
            putCell(height - 2, getExitColumn(), PASSAGE);
        }
    }

    private void generatePassages() {
        new PassageTree(height, width).generate().forEach(putCell());
    }

    private Consumer<Cell> putCell() {
        return cell -> grid[cell.row()][cell.col()] = cell;
    }

    public Cell[][] getGrid() {
        return grid;
    }

    public Cell getEntrance() {
        return grid[0][1];
    }

    public Cell getExit() {
        return grid[height - 1][getExitColumn()];
    }

    public boolean isSolved() {
        return isSolved;
    }

    public void findEscape() {
        new Fugitive(grid, getEntrance(), getExit()).findEscape().forEach(putCell());
        this.isSolved = true;
        PrettyPrint.printMaze(grid);
    }
}
