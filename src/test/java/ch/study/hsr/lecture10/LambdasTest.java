package ch.study.hsr.lecture10;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.function.Consumer;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import ch.study.commons.person.Person;
import ch.study.commons.person.Samples;

/**
 * Lambdas.
 *
 * Inverse Programmierung.
 * Inverse Programmierung mit Seiteneffekten.
 * Höherwertige Funktionen.
 * Methodenreferenz.
 * Anonyme Methode.
 * Type Inferenz.
 * Seiten-Effekte.
 * Closure
 * -> variable defined in enclosing scope
 * -> effectively final
 */
class LambdasTest {

    @Test
    @DisplayName("Sort Comparable objects (compareTo method)")
    void sortWithComparator() {
        final List<Person> people = Samples.getPeopleList();

        Collections.sort(people);

        for (int i = 0; i < people.size() - 1; i++) {
            assertThat(people.get(i).getAge()).isLessThanOrEqualTo(people.get(i + 1).getAge());
        }
    }

    @Test
    @DisplayName("Sort by given method reference")
    void methodReferences() {
        List<Person> people = Samples.getPeopleList();

        people.sort(this::compareByAge);
        for (int i = 0; i < people.size() - 1; i++) {
            assertThat(people.get(i).getAge()).isLessThanOrEqualTo(people.get(i + 1).getAge());
        }

        people.sort(this::compareByCity);
        for (int i = 0; i < people.size() - 1; i++) {
            assertThat(people.get(i).getCity()).isLessThanOrEqualTo(people.get(i + 1).getCity());
        }
    }

    @Test
    @DisplayName("Functional interface: Poly-Type")
    void functionalInterface() {
        MyComparator comparator = this::compareByAge;
        Comparator<Person> comparator2 = this::compareByAge;
    }

    @Test
    @DisplayName("Sort by lambdas")
    void Lambda() {
        List<Person> people = Samples.getPeopleList();

        people.sort((Person person1, Person person2) -> {
            return Integer.compare(person1.getAge(), person2.getAge());
        });

        // parameter types inference:
        people.sort((person1, person2) -> {
            return Integer.compare(person1.getAge(), person2.getAge());
        });

        // one expression:
        people.sort((person1, person2) -> Integer.compare(person1.getAge(), person2.getAge()));

        // Instanz-Methode wird statifiziert:
        people.sort(Person::compareTo);     // people.sort((this, y) -> this.compareTo(y));

        // Comparator building block:
        people.sort(Comparator.comparing(Person::getCity)
                              .thenComparing(Person::getLastName)
                              .reversed());
        System.out.println(people);
    }

    /*
     * Inverse Programmierung mit Seiteneffekten.
     * Method escaping - das potenzielle Überleben der Umgebung
     * Closure - effectively final (automatisch final dahinter)
     */
    @Test
    @DisplayName("Method escaping")
    void testMethodEscaping() {
        var list = List.of("A", "B", "C");

        list.forEach(System.out::println);

        list.forEach(methodEscaping());
    }

    /*
     * Counter - auf den Heap bringen
     */
    @Test
    @DisplayName("Method escaping + Closure")
    void forEachWithCounter() {
        System.out.println("=== test6_ForEachWithCounter");
        var list = List.of("A", "B", "C");

        list.forEach(methodEscapingWithCounter());
        list.forEach(methodEscapingWithCounter());
    }

    /*
     * Helper methods.
     */

    private int compareByAge(Person person1, Person person2) {
        return Integer.compare(person1.getAge(), person2.getAge());
    }

    private int compareByCity(Person person1, Person person2) {
        return person1.getCity().compareTo(person2.getCity());
    }


    private static Consumer<String> methodEscaping() {
        String prefixClosure = "Item: ";
        return x -> System.out.println(prefixClosure + x);
    }

    /*
     * counter - is also a closure (variable defined in enclosing scope),
     * not from local variable, but from (surrounding) class
     */
    private static int counter;
    private static Consumer<String> methodEscapingWithCounter() {
        String prefixClosure = "Item: ";
        return x -> System.out.println(prefixClosure + ++counter + ". " + x);
    }

}
