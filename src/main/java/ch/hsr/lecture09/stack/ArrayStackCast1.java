package ch.hsr.lecture09.stack;

public class ArrayStackCast1<T> {

    private T[] array;
    private int size;

    public ArrayStackCast1(int capacity) {
        this.array = (T[])new Object[capacity]; // that Cast is not valid, because T is not known in Runtime
        this.size = 0;
    }

    public void push(T element) {
        array[size++] = element;
    }

    public T pop() {
        T tmp = array[--size];
        array[size] = null;
        return tmp;
    }

    public T[] getInternals() {
        return array;
    }

}
