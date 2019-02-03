package ch.study.hsr.lecture09;

import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

import ch.study.hsr.lecture09.stack.Stack;

public class HeapPollutionTest {

    @Test
    void testHeapPollution() {
        var integerStack = new Stack<Integer>();
        Stack rawStack = integerStack;

        rawStack.push("ABC");

        integerStack.pop();

        assertThrows(ClassCastException.class, () -> {
            int x = integerStack.pop();
        });
    }

}
