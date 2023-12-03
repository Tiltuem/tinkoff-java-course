package edu.hw8.task3;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class PasswordCracker {
    private static final Logger LOGGER = LogManager.getLogger();
    private static final HashMap<String, String> USER_HASH = new HashMap<>() {
        {
            put("202cb962ac59075b964b07152d234b70", "a.v.petrov");
            put("e2fc714c4727ee9395f324cd2e7f331f", "v.v.belov");
            put("66d23d3878eb5b43848c9ce051d1a412", "a.s.ivanov");
            put("a54fe18b3137c36d936f07122129c816", "k.p.maslov");
        }
    };
    private static final String CHARACTER_SET = "abcdefghijklmnopqrstuvwxyz0123456789";
    private static final int MAX_PASSWORD_LENGTH = 4;
    private static final int NUM_THREADS = 8;
    private static final int BITE_MASK = 0xFF;

    private static Map<String, String> decryptedPasswords = new HashMap<>();
    private static Map<String, String> passwordDictionary = new HashMap<>();

    private PasswordCracker() {}

    private static void generatePasswordDictionary() {
        for (int length = 1; length <= MAX_PASSWORD_LENGTH; length++) {
            nextPassword("", length);
            if (USER_HASH.isEmpty()) {
                break;
            }
        }
    }

    private static void nextPassword(String password, int remainingLength) {
        if (remainingLength == 0) {
            String hash = md5Hash(password);
            passwordDictionary.put(hash, password);
            if (USER_HASH.containsKey(hash)) {
                decryptedPasswords.put(USER_HASH.get(hash), passwordDictionary.get(hash));
                USER_HASH.remove(hash);
            }
            return;
        }

        for (int i = 0; i < CHARACTER_SET.length(); i++) {
            nextPassword(password + CHARACTER_SET.charAt(i), remainingLength - 1);
        }
    }

    public static String md5Hash(String input) {
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            byte[] messageDigest = md.digest(input.getBytes());

            StringBuilder hexString = new StringBuilder();
            for (byte b : messageDigest) {
                String hex = Integer.toHexString(BITE_MASK & b);
                if (hex.length() == 1) {
                    hexString.append('0');
                }
                hexString.append(hex);
            }

            return hexString.toString();
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException();
        }
    }

    public static void multiThreadedPasswordCrack() {
        passwordDictionary.clear();
        generatePasswordDictionary();
        ExecutorService executorService = Executors.newFixedThreadPool(NUM_THREADS);

        for (Map.Entry<String, String> entry : USER_HASH.entrySet()) {
            executorService.execute(() -> decoderPassword(entry.getValue(), entry.getKey()));
        }

        if (decryptedPasswords.isEmpty()) {
            LOGGER.info("Не удалось расшифровать ни один из паролей");
        } else {
            print();
        }

        executorService.shutdown();
    }

    private static void print() {
        for (Map.Entry<String, String> entry : decryptedPasswords.entrySet()) {
            LOGGER.info("Найден пароль для пользователя: " + entry.getKey() + " - " + entry.getValue());
        }
    }

    private static void decoderPassword(String username, String hash) {
        for (Map.Entry<String, String> entry : passwordDictionary.entrySet()) {
            if (entry.getKey().equals(hash)) {
                decryptedPasswords.put(username, entry.getValue());
                break;
            }
        }
    }

    public static Map<String, String> getDecryptedPasswords() {
        return decryptedPasswords;
    }
}
