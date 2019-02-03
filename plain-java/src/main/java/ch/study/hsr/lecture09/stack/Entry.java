package ch.study.hsr.lecture09.stack;

class Entry<T> {

    private final T value;

    private final Entry<T> previous;

    Entry(T value, Entry<T> previous) {
        this.value = value;
        this.previous = previous;
    }

    T getValue() {
        return value;
    }

    Entry<T> getPrevious() {
        return previous;
    }

}
