package ch.study.hsr.lecture13;

public class Stack<T> {

    private Object[] array;
    private int top;

    public Stack(int capacity) {
        if (capacity < 0 || capacity > 65536) {
            throw new IllegalArgumentException("Invalid capacity " + capacity);
        }
        this.array = new Object[capacity];
        this.top = 0;
    }

    public void push(T element) {
        if (element == null) {
            throw new IllegalArgumentException("Invalid null argument");
        } else if (top >= array.length) {
            throw new StackException("Stack overflow");
        }
        array[top++] = element;
    }

    @SuppressWarnings("unchecked")
    public T pop() {
        if (top == 0) {
            throw new StackException("Stack underflow");
        }
        Object tmp = array[--top];
        array[top] = null;
        return (T) tmp;
    }

    public boolean isEmpty() {
        return top == 0;
    }

}