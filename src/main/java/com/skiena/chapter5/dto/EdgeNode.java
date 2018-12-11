package com.skiena.chapter5.dto;

public class EdgeNode {

    private final int vertexId;

    private final int weight;

    private EdgeNode next;

    public EdgeNode(int vertexId, int weight) {
        this.vertexId = vertexId;
        this.weight = weight;
    }

    public int getVertexId() {
        return vertexId;
    }

    public int getWeight() {
        return weight;
    }

    public EdgeNode getNext() {
        return next;
    }
    public void setNext(EdgeNode next) {
        this.next = next;
    }

}
