package ch.hsr.lecture11;

import java.util.List;
import java.util.Map;
import java.util.OptionalDouble;
import java.util.Random;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;
import java.util.stream.Collector;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import ch.hsr.commons.person.Person;
import ch.hsr.commons.person.Samples;

/**
 * Stream API.
 * (Unabhängig von Input/Output Streams)
 *
 * -> Imperative Denken (wie?) -> Declarative Denken (was?)
 * -> Wunschkonzept
 * Data-Flow / Pipeline; Chaining / Fluent interface
 * -> Source (finite, infinite)
 * -> Intermediate operations
 * -> Terminal operations
 *
 * Push-Stype Logic - Reactive programming
 * Pull-Style Logic - Es wird nichts gemacht, solange niemand das habe will
 * -> Lazy Evaluation (rückwärts aufgespannt)
 *
 * Automatische Parallelisierung (Seiteneffekte verboten)
 * Collectors
 */
public class Main {

    public static void main(String[] argv) {
        var main = new Main();

//        main.test1_StreamApi();
//        main.test2_AlternativeNotation();

//        main.test3_FiniteSources();
//        main.test3_InfiniteSources();
        // Zwischenoperationen, Terminaloperationen
//        main.test3_middleActionsWithPrimitives();
//        main.test3_middleActions();

        // Demo of PULL-Style pipeline.
//        main.test4_amountCalls();

//        main.test5_Parallelize();
        main.test6_Grouping();
    }

    private void test1_StreamApi() {
        System.out.println("=== test1_StreamApi");
        List<Person> people = Samples.getPeopleList();

        // Chaining / Fluent interface
        people.stream()
              .filter(p -> p.getAge() >= 18)
              .map(Person::getLastName)
              .sorted()
              .forEach(System.out::println);
    }

    private void test2_AlternativeNotation() {
        System.out.println("=== test2_AlternativeNotation");
        List<Person> people = Samples.getPeopleList();

        Stream<Person> stream1 = people.stream();
        Stream<Person> stream2 = stream1.filter(p -> p.getAge() >= 18);
        Stream<String> stream3 = stream2.map(Person::getLastName);
        Stream<String> stream4 = stream3.sorted();

        stream4.forEach(System.out::println);
    }

    /*
     * Endliche Quellen.
     */
    private void test3_FiniteSources() {
        System.out.println("=== test3_FiniteSources");
        IntStream stream1 = IntStream.range(1, 100);
        stream1.forEach(System.out::println);

        Stream<Integer> stream2 = Stream.of(2, 3, 5, 7, 11, 13);
        Stream<Integer> stream3 = Stream.<Integer>empty();
        // collection.stream()
        Stream stream5 = Stream.concat(stream2, stream3);
    }

    /*
     * Unendliche Quelle - Lazy evaluation.
     */
    private void test3_InfiniteSources() {
        System.out.println("=== test3_InfiniteSources");

        Random random = new Random();
        Supplier<Integer> supplier = random::nextInt;

        Stream.generate(supplier)
              .filter(x -> x % 3 == 0)
              .limit(10)
              .forEach(System.out::println);
        System.out.println();

        IntStream.generate(() -> random.nextInt())
                 .filter(x -> x % 3 == 0)
                 .limit(10)
                 .forEach(System.out::println);
        System.out.println();

        new Random().ints()
                    .filter(x -> x % 3 == 0)
                    .limit(10)
                    .forEach(System.out::println);
        System.out.println();

        // Pull-Style Logic - Es wird nichts gemacht, solange niemand das habe will
        // -> Lazy Evaluation (rückwärts aufgespannt)
        IntStream.iterate(0, i -> i + 1)
                 .filter(x -> x % 3 == 0)
                 .limit(10)
                 .forEach(System.out::println); // teminal operation - end station
    }

    /*
     * Zwischenoperationen für Primitives.
     * Terminaloperationen.
     */
    private void test3_middleActionsWithPrimitives() {
        System.out.println("=== test3_middleActionsWithPrimitives");

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
     * Zwischenoperationen für Primitives.
     * Terminaloperationen.
     */
    private void test3_middleActions() {
        System.out.println("=== test3_middleActions");

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
     */
    private static int counter;
    private void test4_amountCalls() {
        System.out.println("=== test4_amountCalls");
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
    private void test5_Parallelize() {
        System.out.println("=== test5_Parallelize");

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

    private void test6_Grouping() {
        System.out.println("=== test6_Grouping");
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

}
