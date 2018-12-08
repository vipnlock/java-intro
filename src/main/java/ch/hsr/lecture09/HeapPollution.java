package ch.hsr.lecture09;

import ch.hsr.lecture09.stack.Stack;

public class HeapPollution {

    public static void main(String[] argv) {
        var integerStack = new Stack<Integer>();
        Stack rawStack = integerStack;

        rawStack.push("ABC");

        integerStack.pop();

        // int x = integerStack.pop();  --> ClassCastException
    }

}
