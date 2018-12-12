package com.skiena.chapter5.search.java;

import com.skiena.chapter5.dto.EdgeNode;
import com.skiena.chapter5.dto.Graph;
import com.skiena.chapter5.dto.Vertex;

public abstract class CommonSearchAlgorithm<S extends CommonSearchStructure> {

    public void search(Graph g, Vertex firstVertex, S state) {
        state.markDiscovered(null, firstVertex);

        // queue
        while (!state.isDone()) {
            final Vertex discoverer = state.getNextDiscoverer();

            process_Vertex_Early(discoverer);

            state.markProcessed(discoverer);

            EdgeNode edgeNode = g.getEdges(discoverer);
            while (edgeNode != null) {
                final Vertex vSuccessor = g.getVertex(edgeNode.getVertexId());

                if (!state.isProcessed(vSuccessor) || g.isDirected()) {
                    process_Edge(discoverer, vSuccessor);
                }
                if (state.isUndiscovered(vSuccessor)) {
                    state.markDiscovered(discoverer, vSuccessor);
                }

                edgeNode = edgeNode.getNext();
            }

            process_Vertex_Late(discoverer);
        }
    }

    protected abstract void process_Vertex_Early(Vertex vertex);

    protected abstract void process_Vertex_Late(Vertex vertex);

    protected abstract void process_Edge(Vertex v1, Vertex v2);

}
