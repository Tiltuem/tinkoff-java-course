package edu.project2.solving;

import java.util.Objects;

class Node {
    private static final int EDGE_COST = 1;
    private final int row;
    private final int column;
    private final boolean isWall;

    private Node parent;
    private int costFromStart;
    private int costToEnd;
    private int finalCost;

    Node(int row, int column, boolean isWall) {
        this.row = row;
        this.column = column;
        this.isWall = isWall;
        parent = this;
    }

    int getRow() {
        return row;
    }

    int getColumn() {
        return column;
    }

    boolean isWall() {
        return isWall;
    }

    Node getParent() {
        return parent;
    }

    int getFinalCost() {
        return finalCost;
    }

    void calcHeuristicTo(Node node) {
        this.costToEnd = Math.abs(node.row - this.row)
            + Math.abs(node.column - this.column);
    }

    boolean hasBetterPath(Node node) {
        return node.costFromStart + EDGE_COST < this.costFromStart;
    }

    void updatePath(Node node) {
        this.parent = node;
        this.costFromStart = node.costFromStart + EDGE_COST;
        finalCost = costFromStart + costToEnd;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }

        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        Node node = (Node) o;
        return row == node.row
            && column == node.column
            && isWall == node.isWall;
    }

    @Override
    public int hashCode() {
        return Objects.hash(row, column, isWall);
    }
}
