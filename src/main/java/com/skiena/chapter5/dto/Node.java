package com.skiena.chapter5.dto;

public class Node {

    private final int value;

    private final int weight;

    private Node next;

    public Node(int value, int weight) {
        this.value = value;
        this.weight = weight;
    }

    public int getValue() {
        return value;
    }

    public int getWeight() {
        return weight;
    }

    public Node getNext() {
        return next;
    }
    public void setNext(Node next) {
        this.next = next;
    }

}
