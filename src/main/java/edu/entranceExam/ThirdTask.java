package edu.entranceExam;

import java.util.Scanner;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class ThirdTask {
    private static final Logger LOGGER = LogManager.getLogger();

    private ThirdTask() {
    }

    public static void failureAnalysis() {
        Scanner scanner = new Scanner(System.in);
        int n = scanner.nextInt();
        int[] mass = new int[n];

        for (int i = 0; i < n; i++) {
            mass[i] = scanner.nextInt();
        }

        int m = scanner.nextInt();
        int count = 0;
        String[] res = new String[m];
        int[][] massD = new int[m][2];

        for (int i = 0; i < m; i++) {
            massD[i][0] = scanner.nextInt();
            massD[i][1] = scanner.nextInt();
        }

        for (int i = 0; i < m; i++) {
            int start = massD[i][0];
            int end = massD[i][1] - 1;
            boolean upper = true;
            int prev = mass[start - 1];

            for (int j = start; j <= end; j++) {
                if (mass[j] >= prev) {
                    if (!upper && mass[j] > prev) {
                        res[count] = "No";
                        break;
                    }
                } else if (mass[j] <= prev) {
                    upper = false;
                }
                prev = mass[j];
            }

            if (res[count] == null) {
                res[count] = "Yes";
            }
            count++;
        }
        for (String result : res) {
            LOGGER.info(result);
        }
    }
}
