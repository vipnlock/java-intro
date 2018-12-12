package com.skiena.chapter5.search.java;

import com.skiena.chapter5.dto.Vertex;
import com.skiena.chapter5.dto.VertexState;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

public abstract class CommonSearchStructure {

    private final Vertex vStart;

    private final VertexState[] state;
    private final Vertex[] parent;

    public CommonSearchStructure(Vertex vStart, int verticesCount) {
        this.vStart = vStart;

        state = new VertexState[verticesCount + 1];
        parent = new Vertex[verticesCount + 1];

        // initialize
        for (int i = 0; i <= verticesCount; i++) {
            state[i] = VertexState.UNDISCOVERED;
            parent[i] = null;
        }
    }

    void markDiscovered(Vertex discoverer, Vertex vertex) {
        state[vertex.getId()] = VertexState.DISCOVERED;
        parent[vertex.getId()] = discoverer;
        rememberVertex(vertex);
    }

    void markProcessed(Vertex vertex) {
        state[vertex.getId()] = VertexState.PROCESSED;
    }

    boolean isUndiscovered(Vertex vertex) {
        return state[vertex.getId()] == VertexState.UNDISCOVERED;
    }

    boolean isProcessed(Vertex vertex) {
        return state[vertex.getId()] == VertexState.PROCESSED;
    }

    protected abstract void rememberVertex(Vertex vertex);

    protected abstract boolean isDone();

    protected abstract Vertex getNextDiscoverer();

    /*
     * Technical.
     */

    public Vertex[] getParent() {
        return parent;
    }

    public void printOut(Vertex[] vertices) {
        for (Vertex leaf : findAllLeaves(vertices)) {
            findPath(leaf);
            System.out.println();
        }
    }

    private Set<Vertex> findAllLeaves(Vertex[] vertices) {
        Set<Integer> allParents = new HashSet<>();
        Arrays.stream(parent)
                .filter(Objects::nonNull)
                .forEach(vertex -> allParents.add(vertex.getId()));
        return Arrays.stream(vertices)
                .filter(Objects::nonNull)
                .filter(vertex -> !allParents.contains(vertex.getId()))
                .collect(Collectors.toSet());
    }

    private void findPath(Vertex vEnd) {
        if (vEnd == null || vStart.getId() == vEnd.getId()) {
            System.out.print("Path: " + vStart.getId());
        } else {
            findPath(parent[vEnd.getId()]);
            System.out.print(" -> " + vEnd.getId());
        }
    }

}
