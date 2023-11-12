package edu.hw5;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Task8 {
    private static Pattern pattern;
    private static Matcher matcher;
    private static final int DIVIDER = 3;

    private Task8() {
    }

    public static boolean isOddLength(String text) {
        pattern = Pattern.compile("^[10]([10]{2})*$");
        matcher = pattern.matcher(text);

        return matcher.matches();
    }

    public static boolean parityBasedOnFirstChar(String text) {
        pattern = Pattern.compile("^1[10]([10]{2})*$|^0([10]{2})*");
        matcher = pattern.matcher(text);
        return matcher.matches();
    }

    public static boolean countZeroMultipleThree(String text) {
        pattern = Pattern.compile("^[10]{3,}$");
        matcher = pattern.matcher(text);
        int count = 0;

        if (matcher.matches()) {
            pattern = Pattern.compile("0");
            matcher = pattern.matcher(text);
            while (matcher.find()) {
                count++;
            }
        }

        return count % DIVIDER == 0 && count != 0;
    }

    public static boolean everyOddCharIsOne(String text) {
        pattern = Pattern.compile("^(1[10]?)+$");
        matcher = pattern.matcher(text);

        return matcher.matches();
    }

    public static boolean containsMinTwoZeroAndMaxOne(String text) {
        pattern = Pattern.compile("^[10]{2,}$");
        matcher = pattern.matcher(text);
        int countZero = 0;
        int countUnit = 0;

        if (matcher.matches()) {
            pattern = Pattern.compile("1");
            matcher = pattern.matcher(text);
            while (matcher.find()) {
                countUnit++;
                if (countUnit > 1) {
                    return false;
                }
            }

            pattern = Pattern.compile("0");
            matcher = pattern.matcher(text);
            while (matcher.find()) {
                countZero++;
            }
        }

        return countZero >= 2;
    }

    public static boolean noConsecutiveOne(String text) {
        pattern = Pattern.compile("^(0|10)*([01])?$");
        matcher = pattern.matcher(text);

        return matcher.matches();
    }
}
