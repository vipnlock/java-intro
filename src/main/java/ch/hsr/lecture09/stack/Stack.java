package ch.hsr.lecture09.stack;

import java.util.Iterator;

/**
 *
 * @param <T> - type parameter; default type bound - "extends Object"
 */
public class Stack<T> implements Iterable<T> {

    private Entry<T> top;   // T - generic type (argument, usage)

    public void push(T value) {
        top = new Entry<>(value, top);  // diamond operator (syntactic sugar)
    }

    public T pop() {
        if (top == null) {
            return null;
        }
        var value = top.getValue();
        top = top.getPrevious();
        return value;
    }

    public boolean isEmpty() {
        return top == null;
    }

    @Override
    public Iterator<T> iterator() {
        return new StackIterator<>(top);
    }

}
