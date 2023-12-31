package edu.project2.models;

public record Cell(int row, int col, Type type) {
    public enum Type { WALL, PASSAGE, STEP }
}
