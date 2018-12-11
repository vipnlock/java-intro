package com.skiena.chapter5.bfs;

import com.skiena.chapter5.dto.EdgeNode;
import com.skiena.chapter5.dto.Graph;
import com.skiena.chapter5.dto.Vertex;

public abstract class BFSAlgorithm {

    public BfsStructure bfs(Graph g, Vertex firstVertex) {
        final BfsStructure state = new BfsStructure(firstVertex, g.getVerticesCount());

        state.markDiscovered(null, firstVertex);

        // queue
        while (!state.isDone()) {
            final Vertex discoverer = state.getNextDiscoverer();

            process_Vertex_Early(discoverer);

            state.markProcessed(discoverer);

            EdgeNode edgeNode = g.getEdges(discoverer);
            while (edgeNode != null) {
                final Vertex successor = g.getVertex(edgeNode.getVertexId());

                if (state.isProcessed(successor) || g.isDirected()) {
                    process_Edge(discoverer, successor);
                }
                if (state.isUndiscovered(successor)) {
                    state.markDiscovered(discoverer, successor);
                }

                edgeNode = edgeNode.getNext();
            }

            process_Vertex_Late(discoverer);
        }

        return state;
    }

    public void findPath(Vertex vStart, Vertex vEnd, Vertex[] parent) {
        if (vEnd == null || vStart.getId() == vEnd.getId()) {
            System.out.print("Path: " + vStart.getId());
        } else {
            findPath(vStart, parent[vEnd.getId()], parent);
            System.out.print(" -> " + vEnd.getId());
        }
    }

    protected abstract void process_Vertex_Early(Vertex vertex);

    protected abstract void process_Vertex_Late(Vertex vertex);

    protected abstract void process_Edge(Vertex v1, Vertex v2);

}
