package ch.study.hsr.lecture09;

import ch.study.hsr.lecture09.stack.Stack;

class GenericsHelper {

    /*
     * Generics helper.
     * <E> - forward declaration style
     */
    static <E> Stack<E> multiPush(E value, E value2, int times) {
        var result = new Stack<E>();
        for (int i = 0; i < times; i++) {
            result.push(value);
        }
        return result;
    }

    /*
     * Variance helper.
     */
    static <T> void moveCo(Stack<? extends T> from, Stack<T> to) {  // T = Graphic
        while (!from.isEmpty()) {
            to.push(from.pop());
        }
    }

    static <T> void moveContra(Stack<T> from, Stack<? super T> to) {    // T = Rectangle
        while (!from.isEmpty()) {
            to.push(from.pop());
        }
    }

    static <T> void moveBoth(Stack<? extends T> from, Stack<? super T> to) {    // can be T = Graphik or T = Rectangle. Compiler takes the more specific type => Rectangle
        while (!from.isEmpty()) {
            to.push(from.pop());
        }
    }

}
