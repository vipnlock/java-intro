package ch.hsr.lecture10;

import ch.hsr.commons.person.Person;

@FunctionalInterface
public interface MyComparator {

    int anyMethodName(Person x, Person y);

}
