package edu.hw8;

import edu.hw8.task3.PasswordCracker;
import java.util.HashMap;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class Task3Test {

    @Test
    @DisplayName("decodeTest")
    public void decodeTest() {
        HashMap<String, String> result = new HashMap<>() {
            {
                put("a.v.petrov", "123");
                put("v.v.belov", "abcd");
                put("a.s.ivanov", "12qe");
                put("k.p.maslov", "qwe0");
            }
        };
        PasswordCracker.multiThreadedPasswordCrack();
        assertThat(PasswordCracker.getDecryptedPasswords()).isEqualTo(result);
    }


    @Test
    @DisplayName("md5HashTest")
    void md5HashTest() {
        String input = "password";
        String expectedHash = "5f4dcc3b5aa765d61d8327deb882cf99";

        String actualHash = PasswordCracker.md5Hash(input);
        assertThat(expectedHash).isEqualTo(actualHash);
    }
}
