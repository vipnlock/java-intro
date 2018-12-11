package com.skiena.chapter5.dto;

public class Graph {

    private static final int MAX_NODE_COUNT = 1000;

    private final boolean directed;

    private int verticesCount;
    private final Node[] vertices = new Node[MAX_NODE_COUNT + 1];
    private final int[] degree = new int[MAX_NODE_COUNT + 1];

    private int edgesCount;
    private final EdgeNode[] edges = new EdgeNode[MAX_NODE_COUNT + 1];

    public Graph(final boolean directed) {
        this.directed = directed;
    }

    public void insertVertex(final int id, final Node node) {
        if (id > MAX_NODE_COUNT) {
            throw new IllegalStateException("Node is out of bounds: id = " + id + " (limit: " + MAX_NODE_COUNT + ")");
        }
        vertices[id] = node;
        verticesCount++;
    }

    public void insertEdge(final int node1Id, final int node2Id) {
        this.insertEdge(node1Id, node2Id, directed);
    }

    private void insertEdge(final int node1Id, final int node2Id, final boolean directed) {
        var tmp = edges[node1Id];
        edges[node1Id] = new EdgeNode(node2Id,0);
        edges[node1Id].setNext(tmp);

        degree[node1Id]++;

        if (directed) {
            edgesCount++;
        } else {
            insertEdge(node2Id, node1Id, true);
        }
    }

    public EdgeNode[] getEdges() {
        return edges;
    }

    public int[] getDegree() {
        return degree;
    }

    public int getVerticesCount() {
        return verticesCount;
    }

    public int getEdgesCount() {
        return edgesCount;
    }

    public boolean isDirected() {
        return directed;
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
                sb.append("V_").append(vertices[edge.getNodeId()]).append(" -> ");
                edge = edge.getNext();
            }
            sb.replace(sb.length() - 4, sb.length(), "\n");
        }
        return sb.toString();
    }

}
