package com.skiena.chapter5;

import com.skiena.chapter5.dto.Graph;

import java.io.File;
import java.io.IOException;

public class Main {

    public static void main(String[] argv) {
        var main = new Main();

        try {
            main.test1();
        } catch (IOException e) {
            System.out.println("Exception: " + e.getMessage());
        }
    }

    private void test1() throws IOException {
        System.out.println("=== test1");

        final Graph graph = new GraphReader().read(new File("src/main/resources/chapter5/test_graph.txt"));
        System.out.println(graph);
    }

}
