package edu.hw11;

import edu.hw11.task3.FibonacciInterceptor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import net.bytebuddy.ByteBuddy;
import net.bytebuddy.description.modifier.Visibility;
import net.bytebuddy.implementation.MethodDelegation;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class Task3Test {
    @Test
    @DisplayName("generated class test")
    void generatedClassTest()
        throws IllegalAccessException, InstantiationException, InvocationTargetException, NoSuchMethodException {
        Class<?> dynamicType = new ByteBuddy()
            .subclass(Object.class)
            .defineMethod("fib", long.class, Visibility.PUBLIC)
            .withParameter(int.class, "n")
            .intercept(MethodDelegation.to(FibonacciInterceptor.class))
            .make()
            .load(getClass().getClassLoader())
            .getLoaded();

        Object instance = dynamicType.newInstance();
        Method method = dynamicType.getDeclaredMethod("fib", int.class);

        long result = (long) method.invoke(instance, 10);

        assertThat(result).isEqualTo(55);
    }
}
