package edu.hw2.Task1;

public record Addition(Expr firstNum, Expr secondNum) implements Expr {
    @Override
    public double evaluate() {
        return firstNum.evaluate() + secondNum.evaluate();
    }
}
