package com.skiena.chapter5.search.java.pseudodfs;

import com.skiena.chapter5.dto.Graph;
import com.skiena.chapter5.dto.Vertex;
import com.skiena.chapter5.search.java.CommonSearchAlgorithm;

public abstract class JavaPseudoDfsAlgorithm extends CommonSearchAlgorithm<PseudoDfsStructure> {

    public PseudoDfsStructure dfs(Graph g, Vertex firstVertex) {
        final PseudoDfsStructure state = new PseudoDfsStructure(firstVertex, g.getVerticesCount());

        search(g, firstVertex, state);

        return state;
    }

}
