package edu.hw1;

import java.util.Arrays;
import java.util.Objects;

public class Task3 {

    private Task3() {
    }

    public static Boolean isNestable(int[] firstArr, int[] secondArr) {
        if (Objects.isNull(firstArr) || Objects.isNull(secondArr) || secondArr.length < 2) {
            return false;
        } else if (firstArr.length == 0) {
            return true;
        }

        Arrays.sort(firstArr);
        Arrays.sort(secondArr);

        return secondArr[0] < firstArr[0] && secondArr[secondArr.length - 1] > firstArr[firstArr.length - 1];
    }
}
