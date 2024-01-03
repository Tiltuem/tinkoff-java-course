package edu.hw10.task1;

public class MyClass {
    @NotNull
    private String name;

    @Min(18)
    @Max(99)
    private int age;

    @NotNull
    @Min(0)
    @Max(100000)
    private Long salary;

    public static MyClass create() {
        return new MyClass();
    }

    public String getName() {
        return name;
    }

    public int getAge() {
        return age;
    }

    public Long getSalary() {
        return salary;
    }
}
