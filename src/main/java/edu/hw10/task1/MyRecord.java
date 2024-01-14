package edu.hw10.task1;

public record MyRecord(@NotNull String name, @Min(18) @Max(99) int age, @NotNull @Min(0) @Max(100000) Long salary) {
}
