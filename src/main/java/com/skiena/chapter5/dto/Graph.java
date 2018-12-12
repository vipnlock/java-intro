package com.skiena.chapter5.dto;

public class Graph {

    private static final int MAX_VERTEX_COUNT = 1000;

    // Graph
    private final boolean directed;

    // Vertices
    private int verticesCount;
    private final Vertex[] vertices = new Vertex[MAX_VERTEX_COUNT + 1];
    private final int[] degree = new int[MAX_VERTEX_COUNT + 1];

    // Edges
    private int edgesCount;
    private final EdgeNode[] edges = new EdgeNode[MAX_VERTEX_COUNT + 1];

    public Graph(final boolean directed) {
        this.directed = directed;
    }

    public void insertVertex(final int id, final Vertex vertex) {
        if (id > MAX_VERTEX_COUNT) {
            throw new IllegalStateException("Vertex is out of bounds: id = " + id + " (limit: " + MAX_VERTEX_COUNT + ")");
        }
        vertices[id] = vertex;
        verticesCount++;
    }

    public void insertEdge(final int v1Id, final int v2Id) {
        this.insertEdge(v1Id, v2Id, directed);
    }

    private void insertEdge(final int v1Id, final int v2Id, final boolean directed) {
        var tmp = edges[v1Id];
        edges[v1Id] = new EdgeNode(v2Id,0);
        edges[v1Id].setNext(tmp);

        degree[v1Id]++;

        if (directed) {
            edgesCount++;
        } else {
            insertEdge(v2Id, v1Id, true);
        }
    }

    public Vertex getVertex(int id) {
        return vertices[id];
    }

    public EdgeNode getEdges(Vertex vertex) {
        return edges[vertex.getId()];
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

    /*
     * Technical.
     */

    public Vertex[] getVertices() {
        return vertices;
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
                sb.append("V_").append(vertices[edge.getVertexId()]).append(" -> ");
                edge = edge.getNext();
            }
            if (sb.lastIndexOf(" -> ") == sb.length() - 4) {
                sb.replace(sb.length() - 4, sb.length(), "\n");
            }
        }
        return sb.toString();
    }

}
