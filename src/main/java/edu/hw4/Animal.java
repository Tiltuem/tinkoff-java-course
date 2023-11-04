package edu.hw4;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;


public record Animal(
    String name,
    Type type,
    Sex sex,
    int age,
    int height,
    int weight,
    boolean bites
) {
    private static final String NEGATIVE_VALUE_ERROR = "Negative value";

    enum Type {
        CAT, DOG, BIRD, FISH, SPIDER
    }

    enum Sex {
        M, F
    }

    @SuppressWarnings("MagicNumber")
    public int paws() {
        return switch (type) {
            case CAT, DOG -> 4;
            case BIRD -> 2;
            case FISH -> 0;
            case SPIDER -> 8;
        };
    }

    public boolean hasError() {
        return !validate().isEmpty();
    }

    public Set<ValidationError> validate() {
        Set<ValidationError> errors = new HashSet<>();

        if (age < 0) {
            errors.add(new ValidationError(NEGATIVE_VALUE_ERROR, "age"));
        }

        if (Objects.isNull(name)) {
            errors.add(new ValidationError("Null value", "name"));
        }

        if (weight < 0) {
            errors.add(new ValidationError(NEGATIVE_VALUE_ERROR, "weight"));
        }

        if (height < 0) {
            errors.add(new ValidationError(NEGATIVE_VALUE_ERROR, "height"));
        }

        return errors;
    }
}
