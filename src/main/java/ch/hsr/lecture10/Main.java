package ch.hsr.lecture10;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import ch.hsr.commons.person.Person;
import ch.hsr.commons.person.Samples;

public class Main {

    public static void main(String[] argv) {
        Main main = new Main();

//        main.test1_Comparator();
//        main.test2_MethodReferences();
//        main.test3_FunctionalInterface();
        main.test4_Lambda();
    }

    private void test1_Comparator() {
        System.out.println("=== test1_Comparator");
        List<Person> people = Samples.getPeopleList();
        Collections.sort(people);
        System.out.println(people);
    }

    private void test2_MethodReferences() {
        System.out.println("=== test2_MethodReferences");
        List<Person> people = Samples.getPeopleList();

        people.sort(this::compareByAge);
        System.out.println(people);

        people.sort(this::compareByCity);
        System.out.println(people);
    }

    private void test3_FunctionalInterface() {
        System.out.println("=== test3_FunctionalInterface");

        // Poly-Type
        MyComparator comparator = this::compareByAge;
        Comparator<Person> comparator2 = this::compareByAge;
    }

    private void test4_Lambda() {
        System.out.println("=== test4_Lambda");
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



    private int compareByAge(Person person1, Person person2) {
        return Integer.compare(person1.getAge(), person2.getAge());
    }

    private int compareByCity(Person person1, Person person2) {
        return person1.getCity().compareTo(person2.getCity());
    }

}
