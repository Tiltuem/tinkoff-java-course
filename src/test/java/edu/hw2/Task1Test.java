package edu.hw2;

import edu.hw2.Task1.Addition;
import edu.hw2.Task1.Constant;
import edu.hw2.Task1.Exponent;
import edu.hw2.Task1.Expr;
import edu.hw2.Task1.Multiplication;
import edu.hw2.Task1.Negate;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatIllegalArgumentException;

public class Task1Test {
    static Arguments[] numbers() {
        return new Arguments[] {
            Arguments.of(new Constant(-5.0), new Constant(2.0)),
        };
    }

    static Arguments[] exception() {
        return new Arguments[] {
            Arguments.of(new Constant(-5.0), 1.2),
        };
    }

    @ParameterizedTest
    @MethodSource("numbers")
    @DisplayName("addition")
    void addition(Expr firstNum, Expr secondNum) {
        var addition = new Addition(firstNum, secondNum);

        assertThat(addition.evaluate()).isEqualTo(-3.0);
    }

    @ParameterizedTest
    @MethodSource("numbers")
    @DisplayName("exponent")
    void exponent(Expr num, Expr degree) {
        var exponent = new Exponent(num, degree);

        assertThat(exponent.evaluate()).isEqualTo(25.0);
    }

    @ParameterizedTest
    @MethodSource("exception")
    @DisplayName("exponentException")
    void exponentException(Expr num, double degree) {
        var exponent = new Exponent(num, degree);

        assertThatIllegalArgumentException().isThrownBy(exponent::evaluate);
    }

    @ParameterizedTest
    @MethodSource("numbers")
    @DisplayName("multiplication")
    void multiplication(Expr firstNum, Expr secondNum) {
        var multiplication = new Multiplication(firstNum, secondNum);

        assertThat(multiplication.evaluate()).isEqualTo(-10.0);
    }

    @Test
    @DisplayName("negate")
    void negate() {
        var number = new Constant(101.0);
        var negate = new Negate(number);

        assertThat(negate.evaluate()).isEqualTo(-101.0);
    }

    @Test
    @DisplayName("calculation")
    void calculation() {
        var two = new Constant(2);
        var four = new Constant(4);
        var negOne = new Negate(new Constant(1));
        var sumTwoFour = new Addition(two, four);
        var mult = new Multiplication(sumTwoFour, negOne);
        var exp = new Exponent(mult, 2);
        var res = new Addition(exp, new Constant(1));

        assertThat(res.evaluate()).isEqualTo(37.0);
    }
}
