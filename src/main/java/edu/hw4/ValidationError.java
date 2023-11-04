package edu.hw4;

import java.util.Objects;

public class ValidationError {
    private final String error;
    private final String field;


    public ValidationError(String error, String field) {
        this.error = error;
        this.field = field;
    }

    @Override public boolean equals(Object o) {
        if (this == o) {
            return true;
        }

        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        ValidationError that = (ValidationError) o;
        return Objects.equals(error, that.error) && Objects.equals(field, that.field);
    }

    @Override
    public int hashCode() {
        return Objects.hash(error, field);
    }

    @Override public String toString() {
        return String.format("%s: '%s'", field, error);
    }
}
