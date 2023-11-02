package edu.hw3;

import edu.hw3.task5.Contact;
import edu.hw3.task5.Task5;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class Task5Test {
    static Arguments[] contacts() {
        List<Contact> contacts = new ArrayList<>() {
            {
                add(new Contact("Thomas", "Aquinas"));
                add(new Contact("Bob"));
                add(new Contact("Rene", "Descartes"));
                add(new Contact("David", "Hume"));
                add(new Contact("John", "Locke"));
            }
        };
        return new Arguments[] {
            Arguments.of(contacts)
        };
    }

    @ParameterizedTest
    @MethodSource("contacts")
    @DisplayName("parseContact")
    void parseContact(List<Contact> contacts) {
        assertEquals(Task5.parseContact(new String[] {"John Locke", "Thomas Aquinas", "David Hume", "Rene Descartes", "Bob"}, "ASC"), contacts);
        assertEquals(Task5.parseContact(new String[] {"John Locke", "Thomas Aquinas", "David Hume", "Rene Descartes", "Bob"}, "DESC"), contacts.reversed());
    }

    @Test
    @DisplayName("parseContactException")
    void parseContactException() {
        assertThatThrownBy(() -> {
            Task5.parseContact(new String[]{"John Locke", "Thomas Aquinas"}, "afua");
        }).isInstanceOf(IllegalArgumentException.class)
            .hasMessageContaining("Incorrect order");
    }
}
