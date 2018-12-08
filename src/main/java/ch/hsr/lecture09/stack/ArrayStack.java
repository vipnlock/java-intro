package ch.hsr.lecture09.stack;

public class ArrayStack<T> {

    private T[] array;
    private int size;

    public ArrayStack(int capacity) {
        this.array = (T[])new Object[capacity];
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
}
