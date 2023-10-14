package edu.hw1;

import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Task1 {
    private static final int ONE_MINUTE = 60;

    private Task1() {
    }

    public static int lengthInSeconds(String time) {
        if (Objects.isNull(time)) {
            return -1;
        }

        Pattern pattern = Pattern.compile("\\d+:\\d+");
        Matcher matcher = pattern.matcher(time);
        if (!matcher.matches()) {
            return -1;
        }

        String[] minutesAndSeconds = time.split(":");
        int minutes = Integer.parseInt(minutesAndSeconds[0]);
        int seconds = Integer.parseInt(minutesAndSeconds[1]);

        if (seconds >= ONE_MINUTE || seconds < 0) {
            return -1;
        } else {
            return minutes * ONE_MINUTE + seconds;
        }
    }
}
