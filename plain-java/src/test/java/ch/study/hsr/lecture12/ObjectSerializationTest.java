package ch.study.hsr.lecture12;

import static org.junit.jupiter.api.Assertions.assertSame;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import org.junit.jupiter.api.Test;

import ch.study.commons.person.Person;

/**
 * Serialization / Deserialization
 * Externalization / Internalization
 *
 *  Cycle or Sharing (tree in XML, Json) / Transitive Serialisierung
 *
 *  transient fields - default value by deserialization
 */
public class ObjectSerializationTest {

    @Test
    void serializePerson() throws IOException {
        var peter = new Person("Peter", "Meier", 21, "Zürich");
        var hans = new Person("Hans", "Müller", 30, "Zürich");
        peter.addColleague(hans);
        hans.addColleague(peter);   // cyclic structure

        try (var stream = new ObjectOutputStream(new FileOutputStream("target/person.bin"))) {
            stream.writeObject(peter);  // ERROR if not Serializable: java.io.NotSerializableException
        }
    }

    @Test
    void deserializePerson() throws IOException, ClassNotFoundException {
        try(var stream = new ObjectInputStream(new FileInputStream("src/test/resources/hsr/lecture12/person.bin"))) {
            var person = (Person) stream.readObject();  // without constructor - goes in engine
            System.out.println(person);
            System.out.println(person.getColleagues());

            // Transitive Serialisierung
            // bei XML und Json - aufpassen, hier ist ein Zyklus
            assertSame(person, person.getColleagues().get(0).getColleagues().get(0));
        }
    }

}
