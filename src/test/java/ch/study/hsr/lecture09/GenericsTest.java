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
import ch.study.hsr.lecture09.stack.GraphikStack;
import ch.study.hsr.lecture09.stack.Stack;

/**
 * Generics.
 *
 * Generics sind Statischer Polymorphismus.
 * -> generische Klasse        - mit Typ-Parameter
 *    generischer Typ          - Ausprägung (Einsatz mit Typ-Argument)
 *        upper type bound     - e.g. class Abc \<T extends Graphik\>
 * -> generische Schnittstelle - e.g. Iterable and Iterator
 * -> generische Methode       -
 *
 * Type Erasure (see the class StackTest).
 * -> proof with decompilation "javap -c Test.class"
 * -> Generics limitations (see TypeErasureRestrictions)
 * Heap pollution.
 *
 * Type Bounds (see VarianceTest)
 * -> generische Invarianz
 * -> Co-Varianz
 * -> Contra-Varianz
 */
class GenericsTest {

    @Test
    @DisplayName("Generic type")
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
    @DisplayName("Generic interface Iterable")
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
        Stack<String> stringStack = GenericsHelper.multiPush("Hello", "A", 100);

        // type inference: E = Double, E = Integer => compiler goes one level higher => E = Number
        Stack<Number> numberStack = GenericsHelper.multiPush(3.141, 123, 3);

        // manual type declaration
        var integerStack = GenericsHelper.<Integer> multiPush(123, 1, 2);
    }

    @Test
    @DisplayName("Heap pollution")
    void testHeapPollution() {
        var integerStack = new Stack<Integer>();
        Stack rawStack = integerStack;

        rawStack.push("ABC");

        assertThrows(ClassCastException.class, () -> {
            int x = integerStack.pop();
        });
    }

}
