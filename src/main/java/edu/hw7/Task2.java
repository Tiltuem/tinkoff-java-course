package edu.hw7;

import java.math.BigInteger;
import java.util.stream.LongStream;

public class Task2 {
    private Task2() {}

    public static BigInteger calculateFactorial(int n) {
        if (n < 0) {
            throw new IllegalArgumentException("Negative n");
        }

        return LongStream.rangeClosed(1, n)
            .parallel()
            .mapToObj(BigInteger::valueOf)
            .reduce(BigInteger.ONE, BigInteger::multiply);
    }
}
