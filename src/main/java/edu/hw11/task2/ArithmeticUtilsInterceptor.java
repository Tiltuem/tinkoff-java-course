package edu.hw11.task2;

import net.bytebuddy.implementation.bind.annotation.RuntimeType;

public class ArithmeticUtilsInterceptor {
    private ArithmeticUtilsInterceptor() {}

    @RuntimeType
    public static int intercept(@RuntimeType int a, @RuntimeType int b) {
        return a * b;
    }
}
