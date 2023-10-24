package edu.hw2.Task2;

public class Square extends Rectangle {
    public Square() {
        super(0, 0);
    }

    public Square(int side) {
        super(side, side);
    }

    public void setSide(int side) {
        super.setHeight(side);
        super.setWidth(side);
    }
}
