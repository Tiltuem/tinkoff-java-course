package edu.hw1;

public class Task4 {
    private Task4() {
    }

    public static String fixString(String str) {
        if (str.length() == 0 || str.length() == 1) {
            return str;
        }

        char[] strToCharArray = str.toCharArray();

        for (int i = 1; i < strToCharArray.length; i += 2) {
            strToCharArray[i] = str.charAt(i - 1);
            strToCharArray[i - 1] = str.charAt(i);
        }

        return String.valueOf(strToCharArray);
    }
}
