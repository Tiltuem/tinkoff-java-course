package edu.hw2.Task1;

public record Constant(double value) implements Expr {
    @Override
    public double evaluate() {
        return value;
    }
}
