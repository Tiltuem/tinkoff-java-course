package edu.hw3;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class Task2Test {
    @Test
    @DisplayName("clusterize")
    void clusterize() {
        HashMap<String, List<String>> test = new HashMap<>() {{
            put("", new ArrayList<>());
            put(null, new ArrayList<>());
            put("()()()", List.of("()", "()", "()"));
            put("((()))", List.of("((()))"));
            put("((()))(())()()(()())", List.of("((()))", "(())", "()", "()", "(()())"));
            put("((())())(()(()()))", List.of("((())())", "(()(()()))"));
        }};

        for (Map.Entry<String, List<String>> entry : test.entrySet()) {
            assertEquals(Task2.clusterize(entry.getKey()), entry.getValue());
        }
    }

    @Test
    @DisplayName("clusterizeException")
    void clusterException() {
        assertThatThrownBy(() -> {
            Task2.clusterize("adqawda");
            }).isInstanceOf(IllegalArgumentException.class)
            .hasMessageContaining("Your string incorrect");

        assertThatThrownBy(() -> {
            Task2.clusterize("()())(");
        }).isInstanceOf(IllegalArgumentException.class)
            .hasMessageContaining("Brackets are placed incorrectly");
    }
}
