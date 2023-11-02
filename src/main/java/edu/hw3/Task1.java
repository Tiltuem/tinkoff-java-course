package edu.hw3;

import java.util.Objects;

public class Task1 {
    private static final int A_UPPERCASE_ASKII = 65;
    private static final int A_LOWERCASE_ASKII = 97;
    private static final int Z_UPPERCASE_ASKII = 90;
    private static final int Z_LOWERCASE_ASKII = 122;

    private Task1() {
    }

    public static String atbashCipher(String text) {
        if (Objects.isNull(text) || text.isEmpty()) {
            return "";
        }

        StringBuilder ciphertext = new StringBuilder();
        int letter;

        for (int i = 0; i < text.length(); i++) {
            letter = text.charAt(i);

            if (letter >= A_UPPERCASE_ASKII && letter <= Z_UPPERCASE_ASKII) {
                ciphertext.append((char) (Z_UPPERCASE_ASKII + A_UPPERCASE_ASKII - letter));
            } else if (letter >= A_LOWERCASE_ASKII && letter <= Z_LOWERCASE_ASKII) {
                ciphertext.append((char) (Z_LOWERCASE_ASKII + A_LOWERCASE_ASKII - letter));
            } else {
                ciphertext.append((char) letter);
            }
        }

        return ciphertext.toString();
    }
}
