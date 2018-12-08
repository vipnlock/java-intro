package ch.hsr.commons.person;

import java.util.Objects;

public class Person implements Comparable<Person> {

    private final String firstName;
    private final String lastName;
    private final int age;
    private final String city;

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
