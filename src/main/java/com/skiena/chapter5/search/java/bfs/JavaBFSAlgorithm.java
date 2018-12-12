package com.skiena.chapter5.search.java.bfs;

import com.skiena.chapter5.dto.Graph;
import com.skiena.chapter5.dto.Vertex;
import com.skiena.chapter5.search.java.CommonSearchAlgorithm;

public abstract class JavaBFSAlgorithm extends CommonSearchAlgorithm<BfsStructure> {

    public BfsStructure bfs(Graph g, Vertex firstVertex) {
        final BfsStructure state = new BfsStructure(firstVertex, g.getVerticesCount());

        search(g, firstVertex, state);

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

}
