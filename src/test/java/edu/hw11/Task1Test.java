package edu.hw11;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class Task1Test {
    @Test
    @DisplayName("hello world test")
    void byteBuddyHelloWorldTest() throws InstantiationException, IllegalAccessException {
        assertThat(new ByteBuddyHelloWorld().helloWorld()).isEqualTo("Hello, ByteBuddy!");
    }
}
