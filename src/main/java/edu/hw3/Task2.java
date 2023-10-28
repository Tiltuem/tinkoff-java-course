package edu.hw3;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Stack;

public class Task2 {
    private static final String REGEX_BRACKETS = "[()]+";
    private static List<String> clusterizeBrackets;
    private static Stack<Character> stackBrackets;
    private static boolean isValid;
    private static int countLeftBrackets;
    private static int countRightBrackets;
    private static int beginIndex;

    private Task2() {
    }

    private static void init() {
        clusterizeBrackets = new ArrayList<>();
        stackBrackets = new Stack<>();
        isValid = true;
        countLeftBrackets = 0;
        countRightBrackets = 0;
        beginIndex = 0;
    }

    public static List<String> clusterize(String brackets) {
        if (Objects.isNull(brackets) || brackets.isEmpty()) {
            return new ArrayList<>();
        }

        if (!brackets.matches(REGEX_BRACKETS) || brackets.length() % 2 == 1) {
            throw new IllegalArgumentException("Your string incorrect");
        }

        init();

        for (int i = 0; i < brackets.length(); i++) {
            if (!isValid) {
                throw new IllegalArgumentException("Brackets are placed incorrectly");
            }

            if (brackets.charAt(i) == ')') {
                if (stackBrackets.empty() || stackBrackets.pop() != '(') {
                    isValid = false;
                    continue;
                }

                countRightBrackets++;
            } else {
                stackBrackets.push(brackets.charAt(i));
                countLeftBrackets++;
            }

            isCluster(brackets, i);
        }

        return clusterizeBrackets;
    }

    public static void isCluster(String brackets, int index) {
        if (countLeftBrackets == countRightBrackets) {
            clusterizeBrackets.add(brackets.substring(beginIndex, index + 1));
            beginIndex = index + 1;
        }
    }
}
