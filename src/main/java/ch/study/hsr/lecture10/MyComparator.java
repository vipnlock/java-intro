package ch.study.hsr.lecture10;

import ch.study.commons.person.Person;

@FunctionalInterface
public interface MyComparator {

    int anyMethodName(Person x, Person y);

}
