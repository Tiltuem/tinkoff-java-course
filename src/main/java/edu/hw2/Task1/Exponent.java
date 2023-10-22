package edu.hw2.Task1;

public record Exponent(Expr num, Expr degree) implements Expr {
    public Exponent(Expr num, double degree) {
        this(num, new Constant(degree));
    }

    @Override
    public double evaluate() {
        if (num.evaluate() < 0 && degree.evaluate() % 1 != 0) {
            throw new IllegalArgumentException(
                "A negative number can only be raised to an integer power.");
        }

        return Math.pow(num.evaluate(), degree.evaluate());
    }
}
