package ch.study.hsr.lecture11;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.OptionalDouble;
import java.util.Random;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import ch.study.commons.person.Person;
import ch.study.commons.person.Samples;

/**
 * Stream API.
 * (Unabhängig von Input/Output Streams)
 *
 * -> Imperative Denken (wie?) -> Declarative Denken (was?)
 * -> Wunschkonzept
 *
 * Data-Flow / Pipeline;
 * Bevorzugte Schreibweise: Chaining / Fluent interface
 * -> Source (finite, infinite)
 * -> Intermediate operations
 * -> Terminal operations
 *
 * Push-Stype Logic - Reactive programming
 * Pull-Style Logic - Lazy Evaluation (rückwärts aufgespannt) - Es wird nichts gemacht, solange niemand das haben will
 *
 * Automatische Parallelisierung (Seiteneffekte verboten)
 * Collectors
 */
class StreamApiTest {

    @Test
    @DisplayName("Demo Stream API: chaining")
    void demoStreamApi() {
        final List<Person> people = Samples.getPeopleList();

        // Chaining / Fluent interface
        people.stream()
              .filter(p -> p.getAge() >= 18)
              .map(Person::getLastName)
              .sorted()
              .forEach(System.out::println);
    }

    @Test
    @DisplayName("Demo Stream API: alternative notation")
    void demoAlternativeNotation() {
        final List<Person> people = Samples.getPeopleList();

        Stream<Person> stream1 = people.stream();
        Stream<Person> stream2 = stream1.filter(p -> p.getAge() >= 18);
        Stream<String> stream3 = stream2.map(Person::getLastName);
        Stream<String> stream4 = stream3.sorted();

        stream4.forEach(System.out::println);
    }

    /*
     * Endliche Quellen.
     */
    @Test
    @DisplayName("Demo finite sources")
    void demoFiniteSources() {
        IntStream stream1 = IntStream.range(1, 100);
        stream1.forEach(System.out::println);

        Stream<Integer> stream2 = Stream.of(2, 3, 5, 7, 11, 13);
        Stream<Integer> stream3 = Stream.<Integer>empty();

        Stream stream5 = Stream.concat(stream2, stream3);
    }

    /*
     * Unendliche Quelle - Lazy evaluation.
     */
    @Test
    @DisplayName("Demo infinite sources")
    void InfiniteSources() {
        Random random = new Random();
        Supplier<Integer> supplier = random::nextInt;

        // demo 1
        Stream.generate(supplier)
              .filter(x -> x % 3 == 0)
              .limit(10)
              .forEach(System.out::println);
        System.out.println();

        // demo 2
        IntStream.generate(() -> random.nextInt())
                 .filter(x -> x % 3 == 0)
                 .limit(10)
                 .forEach(System.out::println);
        System.out.println();

        // demo 3
        new Random().ints()
                    .filter(x -> x % 3 == 0)
                    .limit(10)
                    .forEach(System.out::println);
        System.out.println();

        // demo 4
        // Pull-Style Logic - Es wird nichts gemacht, solange niemand das haben will
        // -> Lazy Evaluation (rückwärts aufgespannt)
        IntStream.iterate(0, i -> i + 1)
                 .filter(x -> x % 3 == 0)
                 .limit(10)
                 .forEach(System.out::println); // teminal operation - end station
    }

    /*
     * Intermediate operations for primitives.
     * Terminal operations.
     */
    @Test
    @DisplayName("Intermediate actions with primitives")
    void intermediateActionsWithPrimitives() {
        OptionalDouble optionalDouble = Samples.getPeopleList()
                                               .stream()
                                               .mapToInt(Person::getAge)
                                               .average();
        optionalDouble.ifPresentOrElse(System.out::println, () -> System.out.println("Missing!"));

        List.<Person>of()
                .stream()
                .mapToInt(Person::getAge)
                .average()
                .ifPresentOrElse(System.out::println, () -> System.out.println("Missing!"));
    }

    /*
     * Intermediate operations.
     * Terminal operations.
     */
    @Test
    @DisplayName("Intermediate actions")
    void intermediateActions() {
        final Predicate<Person> ageFilter = p -> p.getAge() >= 18;
        final Function<Person, String> toName = p -> p.getFirstName() + " " + p.getLastName();
        final Consumer<String> printIt = System.out::println;

        Samples.getPeopleList()
               .stream()
               .filter(ageFilter)
               .map(toName)
               .sorted()
               .distinct()
               .limit(3)
               .forEach(printIt);
    }

    /*
     * Demo of PULL-Style pipeline.
     * Pipeline wird zuerst aufmodelliert und dann mit der Terminaloperation gestartet.
     * Lazy evaluation - rückwärts aufgespannt./
     */
    private static int counter;
    @Test
    @DisplayName("Demo: pull-style pipeline")
    void amountCalls() {
        IntStream.iterate(0, i -> {
            var result = i + 1;
            System.out.println("Step A: " + result);
            return result;
        })
                 .filter(i -> i % 3 == 0)
                 .filter(i -> {
                     System.out.println("Step B " + ++counter + ": " + i + " % 3 == true");
                     return true;
                 })
                 .limit(10)
                 .average()
                 .ifPresent(System.out::println);
    }

    /*
     * Demo parallel stream.
     * Seiteneffekte prohibited
     */
    private static int counter2;
    @Test
    @DisplayName("Demo: parallel streams")
    void Parallelize() {
        Samples.getPeopleList()
               .parallelStream()
               .filter(p -> {
                   System.out.println(++counter2);          // Seiteneffekte prohibited
                   return p.getCity().equals("Vancouver");
               })
               .mapToInt(p -> p.getAge())
               .average()
               .ifPresent(System.out::println);
    }

    @Test
    @DisplayName("Demo: grouping")
    void Grouping() {
        Map<Integer, List<Person>> peopleByAge = Samples.getPeopleList()
                                                        .stream()
                                                        .collect(Collectors.groupingBy(Person::getAge));
        System.out.println(peopleByAge);

        // Agregations as in SQL
        Map<String, Integer> totalAgeByCity = Samples.getPeopleList()
                                                     .stream()
                                                     .collect(Collectors.groupingBy(Person::getCity, Collectors.summingInt(Person::getAge)));
        System.out.println(totalAgeByCity);
    }

    @Test
    @DisplayName("Demo: group by and count")
    void groupingBy() {
        List<String> list = new ArrayList<>();

        list.add("Hello");
        list.add("Hello");
        list.add("World");

        Map<String, Long> counted = list.stream()
                                        .collect(Collectors.groupingBy(str -> str.substring(3,4), Collectors.counting()));

        System.out.println(counted);
    }

}
