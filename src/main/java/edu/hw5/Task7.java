package edu.hw5;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Task7 {
    private static Pattern pattern;
    private static Matcher matcher;

    private Task7() {
    }

    public static boolean containsMinThreeCharAndThirdIsZero(String text) {
        pattern = Pattern.compile("^([01])[10]*\\1$");
        matcher = pattern.matcher(text);

        return matcher.matches();
    }

    public static boolean firstCharEqualLast(String text) {
        pattern = Pattern.compile("^[10]{2}0[10]*$");
        matcher = pattern.matcher(text);

        return matcher.matches();
    }

    public static boolean lengthMinOneMaxThree(String text) {
        pattern = Pattern.compile("^[01]{1,3}$");
        matcher = pattern.matcher(text);

        return matcher.matches();
    }
}
