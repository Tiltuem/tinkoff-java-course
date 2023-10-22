package edu.hw2;

import edu.hw2.Task2.Rectangle;
import edu.hw2.Task2.Square;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

public class Task2Test {
    static Arguments[] rectangles() {
        return new Arguments[] {
            Arguments.of(new Rectangle()),
            Arguments.of(new Square())
        };
    }

    static Arguments[] squares() {
        return new Arguments[] {
            Arguments.of(new Square()),
            Arguments.of(new Square(5))
        };
    }

    @ParameterizedTest
    @MethodSource("rectangles")
    @DisplayName("rectangleArea")
    void rectangleArea(Rectangle rect) {
        rect.setWidth(20);
        rect.setHeight(10);

        assertThat(rect.getArea()).isEqualTo(200.0);
    }

    @ParameterizedTest
    @MethodSource("squares")
    @DisplayName("squareArea")
    void squareArea(Square square) {
        square.setSide(10);

        assertThat(square.getArea()).isEqualTo(100.0);
    }

    @Test
    @DisplayName("exceptionConstruct")
    void exceptionConstruct() {
        assertThatThrownBy(() -> {
            new Rectangle(-1, -1);
        }).isInstanceOf(IllegalArgumentException.class).hasMessageContaining("height/width cannot be negative");

    }

    @ParameterizedTest
    @MethodSource("rectangles")
    @DisplayName("exceptionSetLength")
    void exceptionSetLength(Rectangle rect) {
        assertThatThrownBy(() -> {
            rect.setHeight(-1);
        }).isInstanceOf(IllegalArgumentException.class).hasMessageContaining("height cannot be negative");

        assertThatThrownBy(() -> {
            rect.setWidth(-1);
        }).isInstanceOf(IllegalArgumentException.class).hasMessageContaining("width cannot be negative");
    }
}
