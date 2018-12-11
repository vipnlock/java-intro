package com.skiena.chapter5.dto;

public class EdgeNode {

    private final int nodeId;

    private final int weight;

    private EdgeNode next;

    public EdgeNode(int nodeId, int weight) {
        this.nodeId = nodeId;
        this.weight = weight;
    }

    public int getNodeId() {
        return nodeId;
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
