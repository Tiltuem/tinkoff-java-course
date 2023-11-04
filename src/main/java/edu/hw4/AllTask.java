package edu.hw4;

import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.function.BinaryOperator;
import java.util.function.Function;
import java.util.stream.Collectors;
import static java.util.Comparator.comparingInt;
import static java.util.stream.Collectors.groupingBy;
import static java.util.stream.Collectors.summingInt;

public class AllTask {
    private static final String NEGATIVE_VALUE_SM = "Negative K";
    private static final int MIN_HEIGHT = 100;

    private AllTask() {}

    public static List<Animal> sortHeight(List<Animal> animals) {
        return animals.stream().sorted(comparingInt(Animal::height)).toList();
    }

    public static List<Animal> sortWeightFirstK(List<Animal> animals, int k) {
        return animals.stream().sorted(comparingInt(Animal::weight).reversed()).limit(k).toList();
    }

    public static Map<Animal.Type, Integer> manyAnimalsType(List<Animal> animals) {
        return animals.stream()
            .collect(groupingBy(Animal::type, summingInt(x -> 1)));
    }

    public static Animal longestName(List<Animal> animals) {
        return animals.stream()
            .max(Comparator.comparingInt(animal -> animal.name().length())).orElseThrow(IllegalArgumentException::new);
    }

    public static Animal.Sex moreMorF(List<Animal> animals) {
        Map<Animal.Sex, Long> sexCount = animals.stream()
            .collect(groupingBy(Animal::sex, Collectors.counting()));

        if (sexCount.get(Animal.Sex.M) > sexCount.get(Animal.Sex.F)) {
            return Animal.Sex.M;
        } else if (sexCount.get(Animal.Sex.M) < sexCount.get(Animal.Sex.F)) {
            return Animal.Sex.F;
        } else {
            throw new IllegalArgumentException("Amount man = amount woman");
        }
    }

    public static Map<Animal.Type, Animal> maxWeightEveryType(List<Animal> animals) {
        return animals.stream()
            .collect(Collectors.toMap(Animal::type, Function.identity(),
                BinaryOperator.maxBy(Comparator.comparing(Animal::weight))));
    }

    public static Animal old(List<Animal> animals) {
        return animals.stream().sorted(comparingInt(Animal::age)).toList().getLast();
    }

    public static Optional<Animal> maxWeightBelowK(List<Animal> animals, int k) {
        if (k <= 0) {
            throw new IllegalArgumentException(NEGATIVE_VALUE_SM);
        }

        return animals.stream().filter(o -> o.height() < k).max(Comparator.comparing(Animal::weight));
    }

    public static Integer countPaws(List<Animal> animals) {
        return animals.stream().mapToInt(Animal::paws).sum();
    }

    public static List<Animal> ageNotEqualPaws(List<Animal> animals) {
        return animals.stream().filter(o -> o.age() != o.paws()).toList();
    }

    public static List<Animal> countAnimalsWithBites(List<Animal> animals) {
        return animals.stream().filter(o -> o.bites() && o.height() > MIN_HEIGHT).toList();
    }

    public static Integer countAnimalsWeightLargerHeight(List<Animal> animals) {
        return (int) animals.stream().filter(o -> o.weight() > o.height()).count();
    }

    public static List<Animal> countAnimalsWithNameTwoWords(List<Animal> animals) {
        return animals.stream().filter(o -> o.name().split(" ").length >= 2).toList();
    }

    public static Boolean isThereDogHigherK(List<Animal> animals, int k) {
        if (k <= 0) {
            throw new IllegalArgumentException(NEGATIVE_VALUE_SM);
        }

        return animals.stream().anyMatch(o -> o.height() > k && o.type() == Animal.Type.DOG);
    }

    public static Map<Animal.Type, Integer> totalWeightType(List<Animal> animals, int k, int l) {
        if (k > l || k < 0) {
            throw new IllegalArgumentException("Invalid k/l");
        }

        return animals.stream().filter(o -> o.age() >= k && o.age() <= l)
            .collect(groupingBy(Animal::type, summingInt(Animal::weight)));
    }

    public static List<Animal> sortedAnimals(List<Animal> animals) {
        return animals.stream().sorted(Comparator.comparing(Animal::type)
                .thenComparing(Animal::sex)
                .thenComparing(Animal::name))
            .collect(Collectors.toList());
    }

    public static Boolean isTrueSpidersBitesMoreDogs(List<Animal> animals) {
        return animals.stream().anyMatch(o -> o.type() == Animal.Type.SPIDER)
            && animals.stream().anyMatch(o -> o.type() == Animal.Type.DOG)
            && animals.stream().filter(o -> o.type() == Animal.Type.SPIDER && o.bites()).count()
            > animals.stream().filter(o -> o.type() == Animal.Type.DOG && o.bites()).count();
    }

    public static Animal maxWeightFish(List<List<Animal>> animalsList) {
        return animalsList.stream().flatMap(List::stream).filter(animal -> animal.type() == Animal.Type.FISH)
            .max(Comparator.comparingInt(Animal::weight))
            .orElseThrow(() -> new IllegalArgumentException("There are no fish in the list"));
    }

    public static Map<String, Set<ValidationError>> invalidValidate(List<Animal> animals) {
        return animals.stream()
            .filter(Animal::hasError)
            .collect(Collectors.toMap(Animal::name, Animal::validate));
    }

    public static Map<String, String> prettyInvalidValidate(List<Animal> animals) {
        return animals.stream()
            .filter(Animal::hasError)
            .collect(Collectors.toMap(Animal::name, animal -> {
                return animal.validate().stream().map(Object::toString).collect(Collectors.joining(", "));
            }));
    }
}
