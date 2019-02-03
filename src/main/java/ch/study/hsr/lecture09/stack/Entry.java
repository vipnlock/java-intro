package ch.study.hsr.lecture09.stack;

public class Entry<T> {

    private final T value;

    private final Entry<T> previous;

    public Entry(T value, Entry<T> previous) {
        this.value = value;
        this.previous = previous;
    }

    public T getValue() {
        return value;
    }

    public Entry<T> getPrevious() {
        return previous;
    }

}
