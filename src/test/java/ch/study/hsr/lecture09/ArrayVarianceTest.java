package ch.study.hsr.lecture09;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import ch.study.hsr.lecture09.stack.ArrayStackCastArray;
import ch.study.hsr.lecture09.stack.ArrayStackCastElement;

public class ArrayVarianceTest {

    @Test
    @DisplayName("Invalid internal cast of array")
    public void arrayStackCastArray() {
        var stack = new ArrayStackCastArray<String>(3);
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
    void arrayStackCastElement() {
        var stack = new ArrayStackCastElement<String>(3);
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
            // Array Covariance - write should be prohibited, but compiler does not perform this check.
            // Runtime checks, whether types are compatible - that is the reason, why runtime needs to know the real type of array element, and why we cannot create new T[]
            general[2] = 1;
        });
    }

}
