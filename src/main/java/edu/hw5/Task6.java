package edu.hw5;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Task6 {
    private Task6() {
    }

    public static boolean isSubstring(String subsequence, String text) {
        StringBuilder regex = new StringBuilder(".*");
        for (int i = 0; i < subsequence.length(); i++) {
            regex.append(subsequence.charAt(i)).append(".*");
        }

        Pattern pattern = Pattern.compile(regex.toString());
        Matcher matcher = pattern.matcher(text);

        return matcher.matches();
    }
}
