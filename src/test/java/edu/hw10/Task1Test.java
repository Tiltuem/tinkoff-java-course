package edu.hw10;


import edu.hw10.task1.MyClass;

import edu.hw10.task1.MyRecord;
import edu.hw10.task1.RandomObjectGenerator;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import java.lang.reflect.InvocationTargetException;
import static org.assertj.core.api.Assertions.assertThat;

public class Task1Test {
    @Test
    @DisplayName("random object generator test")
    void randomObjectGeneratorTest()
        throws InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException {
        RandomObjectGenerator rog = new RandomObjectGenerator();

        MyClass myClass = rog.nextObject(MyClass.class, "create");
        MyRecord myRecord = rog.nextObject(MyRecord.class);

        assertThat(myClass.getName()).isNotNull();
        assertThat(myRecord.name()).isNotNull();

        assertThat(myClass.getAge() > 18 && myClass.getAge() < 99).isTrue();
        assertThat(myRecord.age() > 18 && myRecord.age() < 99).isTrue();

        assertThat(myClass.getSalary() > 0 && myClass.getAge() < 100000).isTrue();
        assertThat(myRecord.salary() > 0 && myRecord.salary() < 100000).isTrue();
    }
}
