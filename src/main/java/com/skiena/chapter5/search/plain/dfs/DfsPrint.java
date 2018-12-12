package com.skiena.chapter5.search.plain.dfs;

import com.skiena.chapter5.dto.Vertex;

public class DfsPrint extends DFSAlgorithm {

    @Override
    protected void process_Vertex_Early(Vertex vertex) {
        System.out.println(">>> Vertex: " + vertex);
    }

    @Override
    protected void process_Vertex_Late(Vertex vertex) {
        System.out.println("<<< Vertex: " + vertex);
    }

    @Override
    protected void process_Edge_Early(Vertex v1, Vertex v2, EdgeType edgeType) {
        System.out.println((edgeType == EdgeType.TREE_EDGE ? "tree" : "BACK") + "  Edge: " + v1 + " -> " + v2);
    }

    @Override
    protected void process_Edge_Late(Vertex v1, Vertex v2) {
        System.out.println(" | " + v2 + " <- " + v1);
    }

}
