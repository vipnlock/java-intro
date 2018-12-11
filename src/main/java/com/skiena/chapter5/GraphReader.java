package com.skiena.chapter5;

import com.skiena.chapter5.dto.Graph;
import com.skiena.chapter5.dto.Vertex;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.regex.Pattern;

public class GraphReader {

    private static final Pattern CAPTION_PATTERN = Pattern.compile("(\\d+);(\\d+);(true|false)");
    private static final String VERTEX_PATTERN_STR = "(\\d+):(\\d+),(\\d+)";
    private static final String EDGE_PATTERN_STR = "\\((\\d+),(\\d+)\\)";
    private static final Pattern VERTEX_PATTERN = Pattern.compile(VERTEX_PATTERN_STR);
    private static final Pattern EDGE_PATTERN = Pattern.compile(EDGE_PATTERN_STR);

    public Graph read(File in) throws IOException {
        Graph graph = null;
        int verticesCount = 0;
        int edgesCount = 0;

        // parse first line
        try (final FileReader fr = new FileReader(in);
             final BufferedReader br = new BufferedReader(fr)) {
            String line;
            while ((line = br.readLine()) != null ) {
                if (line.startsWith("#")) {
                    continue;
                }
                if (graph == null) {
                    var matcher = CAPTION_PATTERN.matcher(line);
                    if (matcher.matches()) {
                        verticesCount = Integer.parseInt(matcher.group(1));
                        edgesCount = Integer.parseInt(matcher.group(2));
                        final boolean directed = Boolean.parseBoolean(matcher.group(3));
                        graph = new Graph(directed);
                    }
                } else {
                    var matcher = VERTEX_PATTERN.matcher(line);
                    if (matcher.matches()) {
                        var id = Integer.parseInt(matcher.group(1));
                        var value1 = Integer.parseInt(matcher.group(2));
                        var value2 = Integer.parseInt(matcher.group(3));
                        graph.insertVertex(id, new Vertex(id, value1, value2));

                    } else {
                        matcher = EDGE_PATTERN.matcher(line);
                        if (matcher.matches()) {
                            var v1Id = Integer.parseInt(matcher.group(1));
                            var v2Id = Integer.parseInt(matcher.group(2));
                            graph.insertEdge(v1Id, v2Id);
                        }
                    }
                }
            }
        }

        if (graph != null) {
            if (verticesCount != graph.getVerticesCount()) {
                throw new IllegalStateException("Number of vertices does not match: " + verticesCount + " != " + graph.getVerticesCount());
            }
            if (edgesCount != graph.getEdgesCount()) {
                throw new IllegalStateException("Number of edges does not match: " + edgesCount + " != " + graph.getEdgesCount());
            }
        }
        return graph;
    }

}
