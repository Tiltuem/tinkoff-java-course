package edu.hw1;

public class Task2 {
    private static final int TEN = 10;

    private Task2() {
    }

    public static int numberOfDigits(int value) {
        int number = value;
        int count = 0;

        while (number != 0) {
            number /= TEN;
            count++;
        }

        return count;
    }
}
