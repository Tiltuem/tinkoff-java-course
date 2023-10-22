package edu.hw2.Task1;

public record Multiplication(Expr firstNum, Expr secondNum) implements Expr {
    @Override
    public double evaluate() {
        return firstNum.evaluate() * secondNum.evaluate();
    }
}
