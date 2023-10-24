package edu.hw2.Task1;

public record Negate(Expr value) implements Expr {
    @Override
    public double evaluate() {
        return -value.evaluate();
    }
}
