package edu.hw2.Task4;

public class CallingUtils {
    private static final int MIN_LENGTH_TRACE = 2;

    private CallingUtils() {}

    public static CallingInfo callingInfo() {
        StackTraceElement[] stackTrace = new Throwable().getStackTrace();

        String className = stackTrace[1].getClassName();
        String methodName = stackTrace[1].getMethodName();

        return new CallingInfo(className, methodName);
    }
}
