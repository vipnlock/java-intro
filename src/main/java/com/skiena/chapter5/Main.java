package com.skiena.chapter5;

import com.skiena.chapter5.dto.Graph;
import com.skiena.chapter5.dto.Vertex;
import com.skiena.chapter5.search.java.bfs.BfsStructure;
import com.skiena.chapter5.search.java.bfs.JavaBfsPrint;
import com.skiena.chapter5.search.java.pseudodfs.JavaPseudoDfsPrint;
import com.skiena.chapter5.search.java.pseudodfs.PseudoDfsStructure;
import com.skiena.chapter5.search.plain.bfs.BfsPrint;
import com.skiena.chapter5.search.plain.dfs.DfsPrint;

import java.io.File;
import java.io.IOException;

public class Main {

    public static void main(String[] argv) {
        var main = new Main();

        final Graph g;
        try {
            g = main.readDirectedGraph();
            System.out.println("Graph: " + g);
        } catch (IOException e) {
            System.out.println("Exception: " + e.getMessage());
            return;
        }

//        main.test_JavaBfs(g);
//        main.test_JavaPseudoDfs(g);
        main.test_BFS(g);
        main.test_DFS(g);
    }

    private void test_JavaBfs(Graph g) {
        System.out.println("=== test_JavaBfs");
        final BfsStructure state = new JavaBfsPrint().bfs(g, g.getVertex(1));
        state.printOut(g.getVertices());
    }

    private void test_JavaPseudoDfs(Graph g) {
        System.out.println("=== test_JavaPseudoDfs");
        final PseudoDfsStructure state = new JavaPseudoDfsPrint().dfs(g, g.getVertex(1));
        state.printOut(g.getVertices());
    }

    private void test_BFS(Graph g) {
        System.out.println("=== test_BFS");
        final BfsPrint alg = new BfsPrint();
        final Vertex firstVertex = g.getVertex(1);

        final Vertex[] parent = alg.bfs(g, firstVertex);
        alg.printBfsTree(g, firstVertex, parent);
    }

    private void test_DFS(Graph g) {
        System.out.println("=== test_DFS");
        final DfsPrint alg = new DfsPrint();

        Vertex firstVertex = g.getVertex(1);
        final Vertex[] parent = alg.dfs(g, firstVertex);
        new BfsPrint().printBfsTree(g, firstVertex, parent);
    }

    private Graph readDirectedGraph() throws IOException {
        return new GraphReader().read(new File("src/main/resources/chapter5/test_directed_graph.txt"));
    }

    private Graph readUndirectedGraph() throws IOException {
        return new GraphReader().read(new File("src/main/resources/chapter5/test_undirected_graph.txt"));
    }

}
