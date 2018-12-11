package com.skiena.chapter5.dto;

public class Vertex {

    private final int id;

    private final int value1;
    private final int value2;

    public Vertex(int id, int value1, int value2) {
        this.id = id;
        this.value1 = value1;
        this.value2 = value2;
    }

    public int getId() {
        return id;
    }

    public int getValue1() {
        return value1;
    }

    public int getValue2() {
        return value2;
    }

    @Override
    public String toString() {
        return id + "(" + value1 + "," + value2 + ")";
    }
}
