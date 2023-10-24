package edu.hw2.Task2;

public class Rectangle {
    private int width;
    private int height;

    public Rectangle() {
        this.width = 0;
        this.height = 0;
    }

    public Rectangle(int width, int height) {
        if (width < 0 || height < 0) {
            throw new IllegalArgumentException("height/width cannot be negative");
        }

        this.width = width;
        this.height = height;
    }

    public void setWidth(int width) {
        if (width <= 0) {
            throw new IllegalArgumentException("width cannot be negative");
        }

        this.width = width;
    }

    public void setHeight(int height) {
        if (height <= 0) {
            throw new IllegalArgumentException("height cannot be negative");
        }

        this.height = height;
    }

    public double getArea() {
        return width * height;
    }
}
