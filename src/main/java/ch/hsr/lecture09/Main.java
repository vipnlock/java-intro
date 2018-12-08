package ch.hsr.lecture09;

import ch.hsr.commons.graphik.Circle;
import ch.hsr.commons.graphik.Rectangle;
import ch.hsr.commons.graphik.Square;
import ch.hsr.lecture09.stack.ArrayStackCast1;
import ch.hsr.lecture09.stack.ArrayStackCast2;
import ch.hsr.lecture09.stack.GraphikStack;
import ch.hsr.lecture09.stack.Stack;

public class Main {

    public static void main(String[] argv) {
        var main = new Main();
//        main.test1_StringStack();
//        main.test2_GraphikStack();
//        main.test3_genericMethod();
//        main.test4_arrayStackCastArray();
//        main.test4_arrayStackCastElement();
        main.test5_ArrayInvariance();
    }

    private void test1_StringStack() {
        System.out.println("=== test1_StringStack");
        var stack = new Stack<String>();

        stack.push("Hi!");
        stack.push("OO");
        stack.push("Prog");

        for (String item : stack) {
            System.out.println(item);
        }
    }

    private void test2_GraphikStack() {
        System.out.println("=== test2_GraphikStack");
        // var stack = new GraphikStack<String>();  - Bounds mismatch
        var stack = new GraphikStack<>();

        stack.push(new Square());
        stack.push(new Rectangle());
        stack.push(new Circle());

        stack.drawAll();
    }

    private void test3_genericMethod() {
        System.out.println("=== test3_genericMethod");
        // type inference: E = String
        Stack<String> stringStack = multiPush("Hello", "A", 100);

        // type inference: E = Double, E = Integer => compiler goes one level higher => E = Number
        Stack<Number> numberStack = multiPush(3.141, 123, 3);

        // manual type declaration
        var integerStack = this.<Integer> multiPush(123, 1, 2);
    }

    private void test4_arrayStackCastArray() {
        System.out.println("=== test4_arrayStackCastArray");
        var stack = new ArrayStackCast1<String>(3);
        stack.push("A");
        stack.push("B");
        stack.push("C");
//        System.out.println(stack.pop());
//        System.out.println(stack.pop());
//        System.out.println(stack.pop());

        Object[] internals = stack.getInternals();      // OK
//        String[] internals = stack.getInternals();    // ERROR: ClassCastException, because it is Object[], not String[]
//        var internals = stack.getInternals();         // ERROR: ClassCastException, compiler takes that typ from the right side
    }

    private void test4_arrayStackCastElement() {
        System.out.println("=== test4_arrayStackCastElement");
        var stack = new ArrayStackCast2<String>(3);
        stack.push("A");
        stack.push("B");
        stack.push("C");
        System.out.println(stack.pop());
        System.out.println(stack.pop());
        System.out.println(stack.pop());
    }

    private void test5_ArrayInvariance() {
        System.out.println("=== test5_ArrayInvariance");
        String[] specific = new String[100];
        Object[] general = specific;

        specific[0] = "ABC";
        general[1] = "DEF";
        general[2] = 1;     // ERROR: ArrayStoreException
    }

    private <E> Stack<E> multiPush(E value, E value2, int times) {
        var result = new Stack<E>();
        for (int i = 0; i < times; i++) {
            result.push(value);
        }
        return result;
    }

}
