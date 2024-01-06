package edu.project5;

import java.lang.invoke.CallSite;
import java.lang.invoke.LambdaMetafactory;
import java.lang.invoke.MethodHandle;
import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodType;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.concurrent.TimeUnit;
import java.util.function.Function;
import java.util.function.Supplier;
import org.openjdk.jmh.annotations.Benchmark;
import org.openjdk.jmh.annotations.Mode;
import org.openjdk.jmh.annotations.Scope;
import org.openjdk.jmh.annotations.Setup;
import org.openjdk.jmh.annotations.State;
import org.openjdk.jmh.infra.Blackhole;
import org.openjdk.jmh.runner.Runner;
import org.openjdk.jmh.runner.RunnerException;
import org.openjdk.jmh.runner.options.Options;
import org.openjdk.jmh.runner.options.OptionsBuilder;
import org.openjdk.jmh.runner.options.TimeValue;

@State(Scope.Thread)
public class ReflectionBenchmark {
    private final static int WARMUP_TIME = 5;
    private final static int NUM_ITER = 5;

    private final static int TIME = 240;
    private static final String FIELD_NAME = "name";

    private static Student student;
    private static Method method;
    private static MethodHandle methodHandle;
    private static Supplier<String> lambdaGetterSupplier;

    @SuppressWarnings({"checkstyle:uncommentedMain"})
    public static void main(String[] args) throws RunnerException {
        Options options = new OptionsBuilder()
            .include(ReflectionBenchmark.class.getSimpleName())
            .shouldFailOnError(true)
            .shouldDoGC(true)
            .mode(Mode.AverageTime)
            .timeUnit(TimeUnit.NANOSECONDS)
            .forks(1)
            .warmupForks(1)
            .warmupIterations(NUM_ITER)
            .warmupTime(TimeValue.seconds(WARMUP_TIME))
            .measurementIterations(1)
            .measurementTime(TimeValue.seconds(TIME))
            .build();

        new Runner(options).run();
    }

    @Setup
    public void setup() throws Throwable {
        student = new Student("Alexander", "Biryukov");
        method = Student.class.getMethod(FIELD_NAME);
        MethodHandles.Lookup lookup = MethodHandles.lookup();
        methodHandle = lookup.findVirtual(Student.class, FIELD_NAME, MethodType.methodType(String.class));

        MethodHandles.Lookup caller = MethodHandles.lookup();
        MethodType getterMethodType = MethodType.methodType(String.class, Student.class);

        CallSite site = LambdaMetafactory.metafactory(
            caller,
            "get",
            MethodType.methodType(Supplier.class),
            getterMethodType,
            caller.findVirtual(Student.class, FIELD_NAME, MethodType.methodType(String.class)),
            getterMethodType
        );

        lambdaGetterSupplier = (Supplier<String>) (site.getTarget()).invokeExact();
    }

    private MethodHandle getMethodHandle() throws Throwable {
        MethodHandles.Lookup lookup = MethodHandles.lookup();
        MethodType methodType = MethodType.methodType(String.class);

        return lookup.findVirtual(Student.class, FIELD_NAME, methodType);
    }

    private Function<Student, String> getLambdaFunction() throws Throwable {
        MethodType functionInterfaceMethodType = MethodType.methodType(Function.class);
        MethodType lambdaType = MethodType.methodType(Object.class, Object.class);
        MethodHandles.Lookup lookup = MethodHandles.lookup();
        MethodHandle studentMethod = lookup.findVirtual(Student.class, FIELD_NAME, MethodType.methodType(String.class));

        MethodType functionSignature = MethodType.methodType(Object.class, Student.class);
        CallSite secondSite = LambdaMetafactory.metafactory(
            lookup,
            "apply",
            functionInterfaceMethodType,
            lambdaType,
            studentMethod,
            functionSignature
        );

        return (Function<Student, String>) secondSite.getTarget().invokeExact();
    }

    @Benchmark
    public void directAccess(Blackhole blackhole) {
        String name = student.name();
        blackhole.consume(name);
    }

    @Benchmark
    public void reflection(Blackhole blackhole) throws InvocationTargetException, IllegalAccessException {
        String name = (String) method.invoke(student);
        blackhole.consume(name);
    }

    @Benchmark
    public void methodHandles(Blackhole blackhole) throws Throwable {
        String name = (String) methodHandle.invoke(student);
        blackhole.consume(name);
    }

    @Benchmark
    public void lambdaMetafactory(Blackhole blackhole) {
        String name = lambdaGetterSupplier.get();
        blackhole.consume(name);
    }
}
