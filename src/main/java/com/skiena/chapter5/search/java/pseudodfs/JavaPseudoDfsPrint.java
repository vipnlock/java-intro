package com.skiena.chapter5.search.java.pseudodfs;

import com.skiena.chapter5.dto.Vertex;

public class JavaPseudoDfsPrint extends JavaPseudoDfsAlgorithm {

    @Override
    protected void process_Vertex_Early(Vertex vertex) {
        System.out.println("-> Vertex: " + vertex);
    }

    @Override
    protected void process_Vertex_Late(Vertex vertex) {
        System.out.println("<- Vertex: " + vertex);
    }

    @Override
    protected void process_Edge(Vertex v1, Vertex v2) {
        System.out.println("    Edge: " + v1 + " -> " + v2);
    }

}
