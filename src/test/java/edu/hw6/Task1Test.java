package edu.hw6;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Map;
import java.util.Set;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import static org.assertj.core.api.Assertions.assertThat;

public class Task1Test {
    static Arguments[] diskMap() throws IOException {
        Path tempDirectory = Files.createTempDirectory("fileClonerTest");
        Path originalFilePath = tempDirectory.resolve("test.txt");

        DiskMap diskMap = new DiskMap(originalFilePath.toString());
        diskMap.put("key1", "value1");
        diskMap.put("key2", "value2");

        return new Arguments[] {Arguments.of(diskMap)};
    }

    @ParameterizedTest
    @MethodSource("diskMap")
    @DisplayName("putAndGetTest")
    void putAndGetTest(DiskMap diskMap) {
        assertThat(diskMap.get("key1")).isEqualTo("value1");
        assertThat(diskMap.get("key2")).isEqualTo("value2");
        assertThat(diskMap.get("affea")).isNull();
    }

    @ParameterizedTest
    @MethodSource("diskMap")
    @DisplayName("RemoveTest")
    void removeTest(DiskMap diskMap) {
        assertThat(diskMap.remove("key2")).isEqualTo("value2");
        assertThat(diskMap.remove("affea")).isNull();
    }

    @ParameterizedTest
    @MethodSource("diskMap")
    @DisplayName("SizeAndClearTest")
    void sizeAndClearTest(DiskMap diskMap) {
        assertThat(diskMap.size()).isEqualTo(2);

        diskMap.clear();
        assertThat(diskMap.isEmpty()).isTrue();
    }

    @ParameterizedTest
    @MethodSource("diskMap")
    @DisplayName("KeySetTest")
    void keySetTest(DiskMap diskMap) {
        Set<String> keySet = diskMap.keySet();

        assertThat(keySet.contains("key1")).isTrue();
        assertThat(keySet.contains("key2")).isTrue();
        assertThat(keySet.contains("fafa")).isFalse();
    }

    @ParameterizedTest
    @MethodSource("diskMap")
    @DisplayName("ValuesTest")
    void valuesTest(DiskMap diskMap) {
        assertThat(diskMap.containsValue("value1")).isTrue();
        assertThat(diskMap.containsValue("value2")).isTrue();
        assertThat(diskMap.containsValue("nonexistentValue")).isFalse();
    }

    @ParameterizedTest
    @MethodSource("diskMap")
    @DisplayName("putAllTest")
    void putAllTest(DiskMap diskMap) {
        diskMap.putAll(Map.of("key3", "value3", "key4", "value4"));

        assertThat(diskMap.containsValue("value3")).isTrue();
        assertThat(diskMap.containsValue("value4")).isTrue();
        assertThat(diskMap.size()).isEqualTo(4);

    }

}
