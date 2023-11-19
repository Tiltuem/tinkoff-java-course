package edu.project2.solving;

import edu.project2.models.Cell;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Set;
import static edu.project2.models.Cell.Type.STEP;
import static edu.project2.models.Cell.Type.WALL;
import static java.util.Comparator.comparingInt;

public class Fugitive {
    private static final int[][] DELTAS = {{-1, 0}, {0, -1}, {0, 1}, {1, 0}};
    private final int height;
    private final int width;
    private final Node[][] grid;
    private final Node start;
    private final Node end;
    private final PriorityQueue<Node> open = new PriorityQueue<>(comparingInt(Node::getFinalCost));
    private final Set<Node> closed = new HashSet<>();

    public Fugitive(Cell[][] grid, Cell start, Cell end) {
        this.height = grid.length;
        this.width = grid[0].length;
        this.grid = new Node[height][width];
        this.start = new Node(start.row(), start.col(), false);
        this.end = new Node(end.row(), end.col(), false);
        createNodes(grid);
    }

    private void createNodes(Cell[][] grid) {
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                Node node = new Node(i, j, grid[i][j].type() == WALL);
                node.calcHeuristicTo(end);
                this.grid[i][j] = node;
            }
        }
    }

    public List<Cell> findEscape() {
        open.add(start);

        while (!open.isEmpty()) {
            Node cur = open.poll();

            if (isEnd(cur)) {
                return reconstructPath(cur);
            }

            closed.add(cur);
            updateNeighbors(cur);
        }

        return new ArrayList<>();
    }

    private boolean isEnd(Node currentNode) {
        return currentNode.equals(end);
    }

    private List<Cell> reconstructPath(Node cur) {
        List<Cell> path = new LinkedList<>();
        path.add(toCell(cur));
        Node curNode = cur;

        while (curNode.getParent() != curNode) {
            Node parent = curNode.getParent();
            path.addFirst(toCell(parent));
            curNode = parent;
        }

        return path;
    }

    private Cell toCell(Node node) {
        return new Cell(node.getRow(), node.getColumn(), STEP);
    }

    private void updateNeighbors(Node cur) {
        for (int[] delta : DELTAS) {
            int row = cur.getRow() + delta[0];
            int column = cur.getColumn() + delta[1];

            if (inBounds(row, column)) {
                Node node = grid[row][column];

                if (!node.isWall() && !closed.contains(node)) {
                    if (open.contains(node) && node.hasBetterPath(cur)) {
                        open.remove(node);
                    } else if (node.hasBetterPath(cur)) {
                        continue;
                    }

                    node.updatePath(cur);
                    open.add(node);
                }
            }
        }
    }

    private boolean inBounds(int row, int column) {
        return row >= 0 && row < height && column >= 0 && column < width;
    }
}

