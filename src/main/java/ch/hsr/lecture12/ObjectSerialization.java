package ch.hsr.lecture12;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import ch.hsr.commons.person.Person;

/**
 * Serialization / Deserialization
 * Externalization / Internalization
 *
 *  Cycle or Sharing (tree in XML, Json) / Transitive Serialisierung
 *
 *  transient fields - default value by deserialization
 */
public class ObjectSerialization {

    public static void main(String[] argv) {
        var main = new ObjectSerialization();

        try {
//            main.test1_SerializePerson();
            main.test2_DeserializePerson();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    private void test1_SerializePerson() throws IOException {
        System.out.println("=== test1_SerializePerson");
        var peter = new Person("Peter", "Meier", 21, "Zürich");
        var hans = new Person("Hans", "Müller", 30, "Zürich");
        peter.addColleague(hans);
        hans.addColleague(peter);   // cyclic structure

        try (var stream = new ObjectOutputStream(new FileOutputStream("target/person.bin"))) {
            stream.writeObject(peter);  // ERROR if not Serializable: java.io.NotSerializableException
        }
    }

    private void test2_DeserializePerson() throws IOException, ClassNotFoundException {
        System.out.println("=== test2_DeserializePerson");

        try(var stream = new ObjectInputStream(new FileInputStream("target/person.bin"))) {
            var person = (Person) stream.readObject();  // without constructor - goes in engine
            System.out.println(person);
            System.out.println(person.getColleagues());

            // Transitive Serialisierung
            System.out.println(person.getColleagues().get(0).getColleagues().get(0) == person); // bei XML und Json - aufpassen, hier ist ein Zyklus
        }
    }

}
