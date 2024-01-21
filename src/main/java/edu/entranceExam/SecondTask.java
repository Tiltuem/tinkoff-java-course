package edu.entranceExam;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class SecondTask {
    private static final Logger LOGGER = LogManager.getLogger();

    private SecondTask() {
    }

    public static void numberOfDifferentSymbols() {
        Scanner in = new Scanner(System.in);
        int length = in.nextInt();
        int count = in.nextInt();
        String password = in.next();

        if (count > length) {
            LOGGER.info(0);
        } else {
            Map<Character, Integer> charCount = new HashMap<>();
            for (int i = 0; i < length; i++) {
                char c = password.charAt(i);
                if (charCount.containsKey(c)) {
                    charCount.put(c, charCount.get(c) + 1);
                } else {
                    charCount.put(c, 1);
                }
            }

            List<Map.Entry<Character, Integer>> charList = new ArrayList<>(charCount.entrySet());
            charList.sort(Collections.reverseOrder(Map.Entry.comparingByValue(Comparator.reverseOrder())));
            Iterator<Map.Entry<Character, Integer>> iterator = charList.iterator();
            Map.Entry<Character, Integer> entry;

            while (iterator.hasNext() && count > 0) {
                entry = iterator.next();
                int countChar = entry.getValue();
                charCount.remove(entry.getKey());
                count -= countChar;
            }

            if (count < 0) {
                LOGGER.info(charCount.size() + 1);
            } else {
                LOGGER.info(charCount.size());
            }
        }
        in.close();
    }
}
