package com.skiena.chapter5.search.plain.bfs;

import com.skiena.chapter5.dto.EdgeNode;
import com.skiena.chapter5.dto.Graph;
import com.skiena.chapter5.dto.Vertex;
import com.skiena.chapter5.dto.VertexState;

import java.util.Arrays;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Objects;
import java.util.Queue;
import java.util.Set;
import java.util.stream.Collectors;

public abstract class BSFAlgorithm {

    public Vertex[] bfs(Graph g, Vertex firstVertex) {
        VertexState[] state = new VertexState[g.getVerticesCount() + 1];
        Vertex[] parent = new Vertex[g.getVerticesCount() + 1];
        Queue<Vertex> queue = new LinkedList<>();

        for (int i = 0; i <= g.getVerticesCount(); i++) {
            state[i] = VertexState.UNDISCOVERED;
            parent[i] = null;
        }

        state[firstVertex.getId()] = VertexState.DISCOVERED;
        queue.add(firstVertex);

        while (!queue.isEmpty()) {
            final Vertex vDiscoverer = queue.poll();

            process_Vertex_Early(vDiscoverer);
            state[vDiscoverer.getId()] = VertexState.PROCESSED;

            EdgeNode edgeNode = g.getEdges(vDiscoverer);
            while (edgeNode != null) {
                final int vSuccessorId = edgeNode.getVertexId();
                final Vertex vSuccessor = g.getVertex(vSuccessorId);

                if (state[vSuccessorId] != VertexState.PROCESSED || g.isDirected()) {
                    process_Edge(vDiscoverer, vSuccessor);
                }
                if (state[vSuccessorId] == VertexState.UNDISCOVERED) {
                    state[vSuccessorId] = VertexState.DISCOVERED;
                    parent[vSuccessorId] = vDiscoverer;
                    queue.add(vSuccessor);
                }

                edgeNode = edgeNode.getNext();
            }

            process_Vertex_Late(vDiscoverer);
        }

        return parent;
    }

    public void findPath(Vertex vStart, Vertex vEnd, Vertex[] parent) {
        if (vEnd == null || vStart.getId() == vEnd.getId()) {
            System.out.print("Path: " + vStart);
        } else {
            findPath(vStart, parent[vEnd.getId()], parent);
            System.out.print(" -> " + vEnd);
        }
    }

    public void printBfsTree(Graph g, Vertex firstVertex, Vertex[] parent) {
        for (Vertex leaf : findAllLeaves(g.getVertices(), parent)) {
            findPath(firstVertex, leaf, parent);
            System.out.println();
        }
    }

    private Set<Vertex> findAllLeaves(Vertex[] allVertices, Vertex[] parent) {
        Set<Integer> allParents = new HashSet<>();
        Arrays.stream(parent)
                .filter(Objects::nonNull)
                .forEach(vertex -> allParents.add(vertex.getId()));
        return Arrays.stream(allVertices)
                .filter(Objects::nonNull)
                .filter(vertex -> !allParents.contains(vertex.getId()))
                .collect(Collectors.toSet());
    }

    protected abstract void process_Vertex_Early(Vertex vertex);

    protected abstract void process_Vertex_Late(Vertex vertex);

    protected abstract void process_Edge(Vertex v1, Vertex v2);

}
