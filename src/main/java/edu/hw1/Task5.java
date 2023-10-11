package edu.hw1;

public class Task5 {
    private static final int MIN_VALUE = 10;

    private Task5() {

    }

    public static Boolean isPalindrome(int value) {
        if (value < MIN_VALUE) {
            return false;
        }

        String valueStr = String.valueOf(value);
        StringBuilder valueReverse = new StringBuilder(valueStr);
        String valueStrReverse = String.valueOf(valueReverse.reverse());

        if (valueStr.equals(valueStrReverse)) {
            return true;
        }

        if (valueStr.length() % 2 == 1) {
            return false;
        }

        StringBuilder newValue = new StringBuilder();
        int sum;
        for (int i = 0; i < valueStr.length(); i += 2) {
            sum = Character.getNumericValue(valueStr.charAt(i)) + Character.getNumericValue(valueStr.charAt(i + 1));
            newValue.append(sum);
        }

        return isPalindrome(Integer.parseInt(newValue.toString()));
    }
}
