package ch.hsr.commons.person;

import java.util.ArrayList;
import java.util.List;

public class Samples {

    public static List<Person> getPeopleList() {
        final List<Person> result = new ArrayList<>();

        result.add(new Person("Joe", "Clark", 21, "Quebec"));
        result.add(new Person("Stephen", "Harper", 25, "Quebec"));
        result.add(new Person("Gerald", "Tremblay", 27, "Ottawa"));
        result.add(new Person("George", "Johnson", 35, "Quebec"));
        result.add(new Person("Kim", "Campbell" , 40, "Halifax"));
        result.add(new Person("Elizabeth", "May", 66, "Hamilton"));
        result.add(new Person("Paul", "Martin", 85, "Vancouver"));
        result.add(new Person("Justin", "Trudoe", 91, "Vancouver"));

        return result;
    }

}
