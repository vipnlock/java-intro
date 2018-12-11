package com.skiena.chapter5.dto;

public class Graph {

    private static final int MAX_NODE_COUNT = 1000;

    private final Node[] edges = new Node[MAX_NODE_COUNT + 1];
    private final int[] degree = new int[MAX_NODE_COUNT + 1];

    private final int verticesCount;
    private int edgesCount;

    private final boolean directed;

    public Graph(final int verticesCount,
                 final boolean directed) {
        this.verticesCount = verticesCount;
        this.directed = directed;
    }

    public void insertEdge(final int x, final int y) {
        this.insertEdge(x, y, directed);
    }

    private void insertEdge(final int x, final int y, final boolean directed) {
        if (x >= MAX_NODE_COUNT || y >= MAX_NODE_COUNT) {
            throw new IllegalStateException("Node is out of bounds: " + x + ", " + y + " (limit: " + MAX_NODE_COUNT + ")");
        }
        var tmp = edges[x];
        edges[x] = new Node(y, 0);
        edges[x].setNext(tmp);

        degree[x]++;

        if (directed) {
            edgesCount++;
        } else {
            insertEdge(y, x, true);
        }
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder();
        sb.append("Graph [")
                .append(verticesCount).append(";")
                .append(edgesCount).append("; ")
                .append(directed ? "directed" : "undirected")
                .append("]\n");
        for (int i = 1; i <= verticesCount; i++) {
            sb.append(i).append("[").append(degree[i]).append("]: ");
            var edge = edges[i];
            while (edge != null) {
                sb.append(edge.getValue()).append(" -> ");
                edge = edge.getNext();
            }
            sb.replace(sb.length() - 4, sb.length(), "\n");
        }
        return sb.toString();
    }

}
