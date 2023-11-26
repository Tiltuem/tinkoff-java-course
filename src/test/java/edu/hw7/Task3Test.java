package edu.hw7;

import edu.hw7.task3.Person;
import edu.hw7.task3.PersonDatabaseImpl;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import static org.assertj.core.api.Assertions.assertThat;

public class Task3Test {
    static Arguments[] database() {
        PersonDatabaseImpl database = new PersonDatabaseImpl();
        return new Arguments[] {Arguments.of(database)};
    }

    @ParameterizedTest
    @MethodSource("database")
    @DisplayName("testConcurrentAddAndFind")
    public void testConcurrentAddAndFind(PersonDatabaseImpl database) throws Throwable {
        int numThreads = 10;
        ExecutorService executorService = Executors.newFixedThreadPool(numThreads);
        CountDownLatch latch = new CountDownLatch(numThreads);

        for (int i = 0; i < numThreads; i++) {
            final int index = i;
            executorService.submit(() -> {
                try {
                    for (int j = 0; j < 100; j++) {
                        Person person =
                            new Person(index * 1000 + j, "Name" + index, "Address" + index, "Phone" + index);
                        database.add(person);

                        Thread.sleep(1);

                        assertThat(person).isEqualTo(database.findByName("Name" + index).get(0));
                        assertThat(person).isEqualTo(database.findByAddress("Address" + index).get(0));
                        assertThat(person).isEqualTo(database.findByPhone("Phone" + index).get(0));
                    }
                } catch (Exception e) {
                    throw new RuntimeException();
                } finally {
                    latch.countDown();
                }
            });
        }

        executorService.shutdown();
        latch.await();
    }

    @ParameterizedTest
    @MethodSource("database")
    @DisplayName("testDelete")
    public void testDelete(PersonDatabaseImpl database) {
        Person person1 = new Person(1, "Kirill", "First street", "1234");
        Person person2 = new Person(2, "Artem", "Second street", "5678");

        database.add(person1);
        database.add(person2);

        database.delete(1);

        List<Person> result = database.findByName("Kirill");
        assertThat(result.size()).isEqualTo(0);
    }
}
