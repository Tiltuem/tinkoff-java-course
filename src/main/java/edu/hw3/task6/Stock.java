package edu.hw3.task6;

public class Stock {
    private double price;
    private String name;

    public Stock() {
        this.price = 0.0;
        this.name = "null";
    }

    public Stock(String name, double price) {
        this.price = price;
        this.name = name;
    }

    public double getPrice() {
        return price;
    }
}
