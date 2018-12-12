package com.skiena.chapter5.search.java.bfs;

import com.skiena.chapter5.dto.Vertex;
import com.skiena.chapter5.search.java.CommonSearchStructure;

import java.util.LinkedList;
import java.util.Queue;

public class BfsStructure extends CommonSearchStructure {

    private final Queue<Vertex> queue;

    BfsStructure(Vertex vStart, int verticesCount) {
        super(vStart, verticesCount);

        queue = new LinkedList<>();
    }

    @Override
    protected void rememberVertex(Vertex vertex) {
        queue.add(vertex);
    }

    @Override
    protected boolean isDone() {
        return queue.isEmpty();
    }

    @Override
    protected Vertex getNextDiscoverer() {
        return queue.poll();
    }

}
