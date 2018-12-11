package com.skiena.chapter5.bfs;

import com.skiena.chapter5.dto.Vertex;
import com.skiena.chapter5.dto.VertexState;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class BfsStructure {

    private final Vertex vStart;

    private final VertexState[] state;
    private final Vertex[] parent;

    private final Queue<Vertex> queue;

    public BfsStructure(Vertex vStart, int verticesCount) {
        this.vStart = vStart;

        state = new VertexState[verticesCount + 1];
        parent = new Vertex[verticesCount + 1];
        queue = new LinkedList<>();

        // initialize
        for (int i = 0; i <= verticesCount; i++) {
            state[i] = VertexState.UNDISCOVERED;
            parent[i] = null;
        }
    }

    public void markDiscovered(Vertex discoverer, Vertex vertex) {
        state[vertex.getId()] = VertexState.DISCOVERED;
        parent[vertex.getId()] = discoverer;
        queue.add(vertex);
    }

    public void markProcessed(Vertex vertex) {
        state[vertex.getId()] = VertexState.PROCESSED;
    }

    public boolean isUndiscovered(Vertex vertex) {
        return state[vertex.getId()] == VertexState.UNDISCOVERED;
    }

    public boolean isProcessed(Vertex vertex) {
        return state[vertex.getId()] == VertexState.PROCESSED;
    }

    public boolean isDone() {
        return queue.isEmpty();
    }

    public Vertex getNextDiscoverer() {
        return queue.poll();
    }

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
