package edu.hw11;

import edu.hw11.task2.ArithmeticUtils;
import edu.hw11.task2.ArithmeticUtilsInterceptor;
import java.lang.reflect.InvocationTargetException;
import net.bytebuddy.ByteBuddy;
import net.bytebuddy.agent.ByteBuddyAgent;
import net.bytebuddy.dynamic.loading.ClassReloadingStrategy;
import net.bytebuddy.implementation.MethodDelegation;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import static net.bytebuddy.matcher.ElementMatchers.named;
import static org.assertj.core.api.Assertions.assertThat;

public class Task2Test {
    @Test
    @DisplayName("sum intercept with multiply test")
    void sumInterceptWithMultiplyTest()
        throws NoSuchMethodException, InvocationTargetException, InstantiationException, IllegalAccessException {
        ByteBuddyAgent.install();

        Class<?> dynamicType = new ByteBuddy()
            .redefine(ArithmeticUtils.class)
            .method(named("getSum"))
            .intercept(MethodDelegation.to(ArithmeticUtilsInterceptor.class))
            .make()
            .load(ClassLoader.getSystemClassLoader(), ClassReloadingStrategy.fromInstalledAgent())
            .getLoaded();

        ArithmeticUtils instance = (ArithmeticUtils) dynamicType.getDeclaredConstructor().newInstance();
        int result = instance.getSum(5, 3);

        assertThat(result).isEqualTo(15);
    }
}
