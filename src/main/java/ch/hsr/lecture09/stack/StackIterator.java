package ch.hsr.lecture09.stack;

import java.util.Iterator;

/**
 * "Gedächtnis" der Iteration.
 *
 * @param <T>
 */
public class StackIterator<T> implements Iterator<T> {

    private Entry<T> current;

    public StackIterator(Entry<T> top) {
        this.current = top;
    }

    @Override
    public boolean hasNext() {
        return current != null;
    }

    @Override
    public T next() {
        var tmp = current.getValue();
        current = current.getPrevious();
        return tmp;
    }

}
