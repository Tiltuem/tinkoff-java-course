package edu.hw2;

import edu.hw2.Task4.CallingInfo;
import edu.hw2.Task4.CallingUtils;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class Task4Test {
    @Test
    @DisplayName("CallingInfo")
    void callingInfo() {
        String className = this.getClass().getName();
        String methodName = "callingInfo";

        CallingInfo callingInfo = CallingUtils.callingInfo();

        assertThat(callingInfo.className()).isEqualTo(className);
        assertThat(callingInfo.methodName()).isEqualTo(methodName);
    }
}
