package com.skiena.chapter5.search.pseudodfs;

import com.skiena.chapter5.dto.Graph;
import com.skiena.chapter5.dto.Vertex;
import com.skiena.chapter5.search.SearchAlgorithm;

public abstract class PseudoDfsAlgorithm extends SearchAlgorithm<PseudoDfsStructure> {

    public PseudoDfsStructure dfs(Graph g, Vertex firstVertex) {
        final PseudoDfsStructure state = new PseudoDfsStructure(firstVertex, g.getVerticesCount());

        search(g, firstVertex, state);

        return state;
    }

}
