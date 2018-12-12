package com.skiena.chapter5.search.plain.dfs;

import com.skiena.chapter5.dto.EdgeNode;
import com.skiena.chapter5.dto.Graph;
import com.skiena.chapter5.dto.Vertex;
import com.skiena.chapter5.dto.VertexState;

public abstract class DFSAlgorithm {

    public Vertex[] dfs(Graph g, Vertex firstVertex) {
        VertexState[] state = new VertexState[g.getVerticesCount() + 1];
        Vertex[] parent = new Vertex[g.getVerticesCount() + 1];

        // initialize
        for (int i = 0; i <= g.getVerticesCount(); i++) {
            state[i] = VertexState.UNDISCOVERED;
            parent[i] = null;
        }

        dfs(g, firstVertex, state, parent);
        return parent;
    }

    private void dfs(Graph g, Vertex vDiscoverer, VertexState[] state, Vertex[] parent) {
        int vDiscovererId = vDiscoverer.getId();
        state[vDiscovererId] = VertexState.DISCOVERED;

        process_Vertex_Early(vDiscoverer);

        EdgeNode edgeNode = g.getEdges(vDiscoverer);
        while (edgeNode != null) {
            final int vSuccessorId = edgeNode.getVertexId();
            final Vertex vSuccessor = g.getVertex(vSuccessorId);

            if (state[vSuccessorId] == VertexState.UNDISCOVERED) {
                parent[vSuccessorId] = vDiscoverer;
                process_Edge_Early(vDiscoverer, vSuccessor, EdgeType.TREE_EDGE);
                dfs(g, vSuccessor, state, parent);
            }
            else if ((state[vSuccessorId] != VertexState.PROCESSED && parent[vDiscovererId] != vSuccessor) || g.isDirected()) {
                process_Edge_Early(vDiscoverer, vSuccessor, EdgeType.BACK_EDGE);
            } else {
                process_Edge_Late(vDiscoverer, vSuccessor);
            }

            edgeNode = edgeNode.getNext();
        }

        process_Vertex_Late(vDiscoverer);
        state[vDiscovererId] = VertexState.PROCESSED;
    }

    protected abstract void process_Vertex_Early(Vertex vertex);

    protected abstract void process_Vertex_Late(Vertex vertex);

    protected abstract void process_Edge_Early(Vertex v1, Vertex v2, EdgeType edgeType);

    protected abstract void process_Edge_Late(Vertex v1, Vertex v2);

}
