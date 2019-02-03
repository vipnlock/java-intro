package ch.study.commons.person;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Person implements Comparable<Person>, Serializable {

    /**
     * Magic reserved constant name - version number for this class, is checked during deserialization.
     * BTW coding convention not fulfilled (Grossbuchstaben in Constant-name).
     * data evolution -> not compatible classes:
     * ERROR reason: java.io.InvalidClassException: ch.hsr.commons.person.Person; local class incompatible: stream classdesc serialVersionUID = 1, local class serialVersionUID = 2
     */
    private static final long serialVersionUID = 1L;

    private final String firstName;
    private final String lastName;
    private final transient int age;
    private final String city;

    private final List<Person> colleagues = new ArrayList<>();

    public Person(String firstName, String lastName, int age, String city) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.age = age;
        this.city = city;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public int getAge() {
        return age;
    }

    public String getCity() {
        return city;
    }

    public void addColleague(final Person person) {
        colleagues.add(person);
    }
    public List<Person> getColleagues() {
        return colleagues;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;
        Person person = (Person) o;
        return age == person.age &&
                firstName.equals(person.firstName) &&
                lastName.equals(person.lastName) &&
                city.equals(person.city);
    }

    @Override
    public int hashCode() {
        return Objects.hash(firstName, lastName, age, city);
    }

    @Override
    public String toString() {
        return firstName + " " + lastName + "(" + age + ", " + city + ")\n";
    }

    @Override
    public int compareTo(Person other) {
        return Integer.compare(age, other.age);
    }

}
