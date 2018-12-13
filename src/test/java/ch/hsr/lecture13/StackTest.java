package ch.hsr.lecture13;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTimeoutPreemptively;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.Duration;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class StackTest {

    private static final Duration TIMEOUT = Duration.ofSeconds(3);

    @Test
    @DisplayName("Last in first out test")
    void testLIFO() {
        assertTimeoutPreemptively(TIMEOUT, () -> {
            var stack = new Stack<String>(3);
            stack.push("A");
            stack.push("B");
            stack.push("C");
            assertFalse(stack.isEmpty());
            assertEquals("C", stack.pop(), "LIFO violated");
            assertEquals("B", stack.pop());
            assertEquals("A", stack.pop());
            assertTrue(stack.isEmpty());
        });
    }

    @Test
    @DisplayName("Empty stack is empty")
    void testEmptyStack() {
        assertTrue(new Stack<Integer>(0).isEmpty());
    }

    @Test
    @DisplayName("Stack underflow throws exception")
    void testUnderflow() {
        var stack = new Stack<String>(1);
        assertThrows(StackException.class, stack::pop);
    }

//    @Test
//    void extraTest() {
//        assertEquals(127, 127);
//        assertSame(127, 127);
//        assertEquals(128, 128);
//        assertSame(128, 128);
//    }

}
