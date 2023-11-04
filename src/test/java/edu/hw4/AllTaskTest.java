package edu.hw4;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class AllTaskTest {
    static Arguments[] animalsLite() {
        List<Animal> animals = new ArrayList<>() {
            {
                add(new Animal("Шарик", Animal.Type.DOG, Animal.Sex.M, 4, 40, 15, false));
                add(new Animal("Бобик", Animal.Type.DOG, Animal.Sex.F, 2, 140, 20, true));
                add(new Animal("Кот Борис", Animal.Type.CAT, Animal.Sex.M, 4, 20, 3, false));
            }
        };
        return new Arguments[] {Arguments.of(animals)};
    }
    static Arguments[] animals() {
        List<Animal> animals = new ArrayList<>() {
            {
                add(new Animal("Шарик", Animal.Type.DOG, Animal.Sex.M, 12, 40, 20, false));
                add(new Animal("Бобик", Animal.Type.DOG, Animal.Sex.F, 2, 140, 20, false));
                add(new Animal("Борис", Animal.Type.CAT, Animal.Sex.M, 1, 20, 3, false));
                add(new Animal("Леопольд", Animal.Type.CAT, Animal.Sex.M, 10, 35, 5, true));
                add(new Animal("Кар карыч", Animal.Type.BIRD, Animal.Sex.M, 1, 10, 1, true));
                add(new Animal("Соловей", Animal.Type.BIRD, Animal.Sex.F, 2, 40, 20, false));
                add(new Animal("Немо", Animal.Type.FISH, Animal.Sex.F, 1, 7, 10, true));
                add(new Animal("Марлин", Animal.Type.FISH, Animal.Sex.M, 2, 9, 12, false));
                add(new Animal("Добряк", Animal.Type.SPIDER, Animal.Sex.M, 7, 1, 1, false));
                add(new Animal("Человек паук", Animal.Type.SPIDER, Animal.Sex.M, 3, 2, 1, true));
            }
        };
        return new Arguments[] {Arguments.of(animals)};
    }

    static Arguments[] invalidAnimals() {
        List<Animal> animals = new ArrayList<>() {
            {
                add(new Animal(null, Animal.Type.DOG, Animal.Sex.M, 12, 40, 20, false));
                add(new Animal("Бобик", Animal.Type.DOG, Animal.Sex.F, 2, -140, -20, false));
                add(new Animal("Борис", Animal.Type.CAT, Animal.Sex.M, -1, 20, 3, false));
            }
        };
        return new Arguments[] {Arguments.of(animals)};
    }

    @ParameterizedTest
    @MethodSource("animals")
    @DisplayName("animalsTest")
    void animalsTest(List<Animal> animals) {
        //У какого животного самое длинное имя
        assertThat(AllTask.longestName(animals).name()).isEqualTo("Человек паук");

        // Каких животных больше: самцов или самок
        assertThat(AllTask.moreMorF(animals)).isEqualTo(Animal.Sex.M);

        // K-е самое старое животное
        assertThat(AllTask.old(animals).name()).isEqualTo("Шарик");

        // Самое тяжелое животное среди животных ниже k см
        assertThat(AllTask.maxWeightBelowK(animals, 30).get().name()).isEqualTo("Марлин");

        // Сколько в сумме лап у животных в списке
        assertThat(AllTask.countPaws(animals)).isEqualTo(36);

        // Сколько в списке животных, вес которых превышает рост
        assertThat(AllTask.countAnimalsWeightLargerHeight(animals)).isEqualTo(2);

        // Есть ли в списке собака ростом более k см
        assertThat(AllTask.isThereDogHigherK(animals, 100)).isTrue();

        // Правда ли, что пауки кусаются чаще, чем собаки
        assertThat(AllTask.isTrueSpidersBitesMoreDogs(animals)).isTrue();

        // Найти самую тяжелую рыбку в 2-х или более списках
        assertThat(AllTask.maxWeightFish(List.of(animals, new ArrayList<>())).name()).isEqualTo("Марлин");
    }

    @ParameterizedTest
    @MethodSource("animalsLite")
    @DisplayName("animalsTest")
    void animalsListOrMapTest(List<Animal> animals) {
        List<Animal> resultAnimals = new ArrayList<>() {
            {
                add(new Animal("Кот Борис", Animal.Type.CAT, Animal.Sex.M, 4, 20, 3, false));
                add(new Animal("Шарик", Animal.Type.DOG, Animal.Sex.M, 4, 40, 15, false));
                add(new Animal("Бобик", Animal.Type.DOG, Animal.Sex.F, 2, 140, 20, true));
            }
        };

        Map<Animal.Type, Integer> countAnimalsType = new HashMap<>() {
            {
                put(Animal.Type.DOG, 2);
                put(Animal.Type.CAT, 1);
            }
        };

        Map<Animal.Type, Animal> maxWeightEveryType = new HashMap<>() {
            {
                put(Animal.Type.DOG, resultAnimals.get(2));
                put(Animal.Type.CAT, resultAnimals.get(0));
            }
        };

        // Отсортировать животных по росту от самого маленького к самому большому
        assertThat(AllTask.sortHeight(animals)).isEqualTo(resultAnimals);

        // Отсортировать животных по весу от самого тяжелого к самому легкому, выбрать k первых
        assertThat(AllTask.sortWeightFirstK(animals, 3)).isEqualTo(resultAnimals.reversed());

        // Сколько животных каждого вида
        assertThat(AllTask.manyAnimalsType(animals)).isEqualTo(countAnimalsType);

        // Самое тяжелое животное каждого вида
        assertThat(AllTask.maxWeightEveryType(animals)).isEqualTo(maxWeightEveryType);

        // Список животных, возраст у которых не совпадает с количеством лап
        assertThat(AllTask.ageNotEqualPaws(animals)).isEqualTo(List.of(resultAnimals.get(2)));

        // Список животных, которые могут укусить (bites == true) и рост которых превышает 100 см
        assertThat(AllTask.countAnimalsWithBites(animals)).isEqualTo(List.of(resultAnimals.get(2)));

        // Список животных, имена которых состоят из более чем двух слов
        assertThat(AllTask.countAnimalsWithNameTwoWords(animals)).isEqualTo(List.of(resultAnimals.get(0)));

        // Найти суммарный вес животных каждого вида, которым от k до l лет
        assertThat(AllTask.totalWeightType(animals, 1, 10)).isEqualTo(Map.of(Animal.Type.DOG, 35, Animal.Type.CAT, 3));

        // Список животных, отсортированный по виду, затем по полу, затем по имени
        assertThat(AllTask.sortedAnimals(animals)).isEqualTo(resultAnimals);
    }
    @Test
    @DisplayName("exceptionTest")
    void exceptionTest() {
        assertThatThrownBy(() -> {
            AllTask.moreMorF(List.of(
                new Animal("dog", Animal.Type.DOG, Animal.Sex.M, 1, 1, 1, true),
                new Animal("cat", Animal.Type.DOG, Animal.Sex.F, 1, 1, 1, true)
            ));
        }).isInstanceOf(IllegalArgumentException.class).hasMessageContaining("Amount man = amount woman");

        assertThatThrownBy(() -> {
            AllTask.maxWeightBelowK(null, -1);
        }).isInstanceOf(IllegalArgumentException.class).hasMessageContaining("Negative K");

        assertThatThrownBy(() -> {
            AllTask.totalWeightType(null, 10, 1);
        }).isInstanceOf(IllegalArgumentException.class).hasMessageContaining("Invalid k/l");

        assertThatThrownBy(() -> {
            AllTask.maxWeightFish(List.of(new ArrayList<>(), new ArrayList<>()));
        }).isInstanceOf(IllegalArgumentException.class).hasMessageContaining("There are no fish in the list");
    }

    @ParameterizedTest
    @MethodSource("invalidAnimals")
    @DisplayName("invalidAnimalsTest")
    void invalidAnimalsTest(List<Animal> animals) {
        Map<String, Set<ValidationError>> invalidValidate = new HashMap<>() {
            {
                put(null, Set.of(new ValidationError("Null value", "name")));
                put("Бобик",
                    Set.of(
                        new ValidationError("Negative value", "weight"),
                        new ValidationError("Negative value", "height")
                    )
                );
                put("Борис", Set.of(new ValidationError("Negative value", "age")));
            }
        };

        Map<String, String> prettyInvalidValidate = new HashMap<>() {
            {
                put(null, "name: 'Null value'");
                put("Бобик", "weight: 'Negative value', height: 'Negative value'");
                put("Борис", "age: 'Negative value'");
            }
        };

        // Животные, в записях о которых есть ошибки: вернуть имя и список ошибок
        assertEquals(AllTask.invalidValidate(animals), invalidValidate);

        
        assertEquals(AllTask.prettyInvalidValidate(animals), prettyInvalidValidate);
    }
}
