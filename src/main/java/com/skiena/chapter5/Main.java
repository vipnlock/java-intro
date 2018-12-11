package com.skiena.chapter5;

import com.skiena.chapter5.bfs.BfsPrint;
import com.skiena.chapter5.bfs.BfsStructure;
import com.skiena.chapter5.dto.Graph;

import java.io.File;
import java.io.IOException;

public class Main {

    public static void main(String[] argv) {
        var main = new Main();

        try {
            main.testBFS();
        } catch (IOException e) {
            System.out.println("Exception: " + e.getMessage());
        }
    }

    private void testBFS() throws IOException {
        System.out.println("=== testBFS");
        Graph g = readTestGraph();

        BfsPrint alg = new BfsPrint();
        BfsStructure state = alg.bfs(g, g.getVertex(1));

        state.printOut(g.getVertices());
    }

    private Graph readTestGraph() throws IOException {
        return new GraphReader().read(new File("src/main/resources/chapter5/test_graph.txt"));
    }

}
