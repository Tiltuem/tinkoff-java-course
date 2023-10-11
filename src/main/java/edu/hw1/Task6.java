package edu.hw1;

import java.util.Arrays;

public class Task6 {
    private static final int TEN = 10;
    private static final int MIN_VALUE = 1000;
    private static final int MAX_VALUE = 10000;
    private static final int NECESSARY_RESULT = 6174;
    private static int count = 0;

    private Task6() {
    }

    public static void setCount(int count) {
        Task6.count = count;
    }


    public static int numberOfStepsForKaprekar(int value) {
        int countNum = (int) String.valueOf(value)
            .chars()
            .mapToObj(ch -> (char) ch)
            .filter(Character::isDigit)
            .distinct()
            .count();

        if (value < MIN_VALUE || value >= MAX_VALUE || countNum < 2) {
            return -1;
        }

        if (value == NECESSARY_RESULT) {
            return count;
        }

        char[] digits = String.valueOf(value).toCharArray();
        Arrays.sort(digits);
        String minValueString = String.valueOf(digits);
        int minValue = Integer.parseInt(minValueString);

        StringBuilder maxValueString = new StringBuilder(minValueString);
        int maxValue = Integer.parseInt(maxValueString.reverse().toString());

        count++;
        int newValue = maxValue - minValue;
        while (newValue < MIN_VALUE) {
            newValue *= TEN;
        }

        return numberOfStepsForKaprekar(newValue);
    }
}
