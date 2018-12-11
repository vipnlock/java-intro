package com.skiena.chapter5;

import com.skiena.chapter5.search.bfs.BfsPrint;
import com.skiena.chapter5.search.bfs.BfsStructure;
import com.skiena.chapter5.search.pseudodfs.PseudoDfsPrint;
import com.skiena.chapter5.search.pseudodfs.PseudoDfsStructure;
import com.skiena.chapter5.dto.Graph;

import java.io.File;
import java.io.IOException;

public class Main {

    public static void main(String[] argv) {
        var main = new Main();

        try {
//            main.test_BFS();
            main.test_DFS();
        } catch (IOException e) {
            System.out.println("Exception: " + e.getMessage());
        }
    }

    private void test_BFS() throws IOException {
        System.out.println("=== test_BFS");
        Graph g = readTestGraph();

        BfsPrint alg = new BfsPrint();
        BfsStructure state = alg.bfs(g, g.getVertex(1));

        state.printOut(g.getVertices());
    }

    private void test_DFS() throws IOException {
        System.out.println("=== test_DFS");
        Graph g = readTestGraph();

        PseudoDfsPrint alg = new PseudoDfsPrint();
        PseudoDfsStructure state = alg.dfs(g, g.getVertex(1));

        state.printOut(g.getVertices());
    }

    private Graph readTestGraph() throws IOException {
        return new GraphReader().read(new File("src/main/resources/chapter5/test_graph.txt"));
    }

}
