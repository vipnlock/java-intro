package ch.study.hsr.lecture09;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import ch.study.commons.graphik.Circle;
import ch.study.commons.graphik.Rectangle;
import ch.study.commons.graphik.Square;
import ch.study.hsr.lecture09.stack.ArrayStackCast1;
import ch.study.hsr.lecture09.stack.ArrayStackCast2;
import ch.study.hsr.lecture09.stack.GraphikStack;
import ch.study.hsr.lecture09.stack.Stack;

/**
 * Generics.
 *
 * Statischer Polymorphismus
 * -> generische Klasse
 * -> generische Schnittstelle
 * -> generische Methode
 * Type Erasure
 * -> Generics limitations
 * Type Bounds:
 * -> generische Invarianz
 * -> Co-Varianz
 * -> Contra-Varianz
 */
public class GenericsTest {

    @Test
    void simpleStackOfStrings() {
        var stack = new Stack<String>();

        stack.push("Hi!");
        stack.push("OO");
        stack.push("Prog");

        final List<String> expected = Arrays.asList("Prog", "OO", "Hi!");
        int expectedIndex = 0;
        for (String item : stack) {
            assertEquals(expected.get(expectedIndex++), item);
        }
    }

    @Test
    void graphikStack() {
        // var stack = new GraphikStack<String>();  - Bounds mismatch
        var stack = new GraphikStack<>();

        stack.push(new Square());
        stack.push(new Rectangle());
        stack.push(new Circle());

        assertEquals(List.of("Circle", "Rectangle", "Square"), stack.drawAll());
    }

    @Test
    @DisplayName("Generic method")
    void genericMethod() {
        // type inference: E = String
        Stack<String> stringStack = Lecture09Helper.multiPush("Hello", "A", 100);

        // type inference: E = Double, E = Integer => compiler goes one level higher => E = Number
        Stack<Number> numberStack = Lecture09Helper.multiPush(3.141, 123, 3);

        // manual type declaration
        var integerStack = Lecture09Helper.<Integer> multiPush(123, 1, 2);
        var integerStack2 = this.<Integer> multiPush(123, 1, 2);
    }

    private <E> Stack<E> multiPush(E value, E value2, int times) {
        var result = new Stack<E>();
        for (int i = 0; i < times; i++) {
            result.push(value);
        }
        return result;
    }

    @Test
    @DisplayName("Invalid internal cast of array")
    public void arrayStackCastArray() {
        var stack = new ArrayStackCast1<String>(3);
        stack.push("A");
        stack.push("B");
        stack.push("C");

        Object[] internals = stack.getInternals();      // OK

        // ClassCastException, because it is Object[], not String[]
        assertThrows(ClassCastException.class, () -> {
            String[] internals2 = stack.getInternals();
        });

        // ClassCastException, compiler takes that typ from the right side
        assertThrows(ClassCastException.class, () -> {
            var internals3 = stack.getInternals();
        });
    }

    @Test
    @DisplayName("Internal cast of element")
    void test4_arrayStackCastElement() {
        var stack = new ArrayStackCast2<String>(3);
        stack.push("A");
        stack.push("B");
        stack.push("C");
        assertEquals("C", stack.pop());
        assertEquals("B", stack.pop());
        assertEquals("A", stack.pop());
    }

    @Test
    @DisplayName("Array Invariance")
    void arrayInvariance() {
        String[] specific = new String[100];
        Object[] general = specific;

        specific[0] = "ABC";
        general[1] = "DEF";
        assertThrows(ArrayStoreException.class, () -> {
            general[2] = 1;
        });
    }


}
