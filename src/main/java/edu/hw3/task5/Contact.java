package edu.hw3.task5;

import java.util.Objects;
import org.jetbrains.annotations.NotNull;

public class Contact implements Comparable<Contact> {
    private String firstName;
    private String lastName;

    public Contact(String firstName) {
        this.firstName = firstName;
    }

    public Contact(String firstName, String lastName) {
        this.firstName = firstName;
        this.lastName = lastName;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    @Override
    public int compareTo(@NotNull Contact contact) {
        String fieldForCompareThis = Objects.isNull(lastName) ? firstName : lastName;

        String fieldForCompareContact =
            Objects.isNull(contact.getLastName()) ? contact.getFirstName() : contact.getLastName();

        return fieldForCompareThis.compareTo(fieldForCompareContact);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }

        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        Contact contact = (Contact) o;

        return Objects.equals(firstName, contact.firstName) && Objects.equals(lastName, contact.lastName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(firstName, lastName);
    }
}
