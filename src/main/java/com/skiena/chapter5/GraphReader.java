package com.skiena.chapter5;

import com.skiena.chapter5.dto.Graph;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.Objects;
import java.util.regex.Pattern;

public class GraphReader {

    private static final Pattern CAPTION_PATTERN = Pattern.compile("(\\d+);(\\d+);(true|false)");
    private static final String NODE_PATTERN_STR = "\\((\\d+),(\\d+)\\)";
    private static final Pattern NODE_PATTERN = Pattern.compile(NODE_PATTERN_STR);

    public Graph read(File in) throws IOException {
        final var graphOptional = Files.lines(in.toPath())
                .filter(line -> !line.startsWith("#"))
                .limit(1)
                .map(line -> {
                    var matcher = CAPTION_PATTERN.matcher(line);
                    if (matcher.matches()) {
                        final int verticesCount = Integer.parseInt(matcher.group(1));
                        final int edgesCount = Integer.parseInt(matcher.group(2));
                        final boolean directed = Boolean.parseBoolean(matcher.group(3));
                        return new Graph(verticesCount, directed);
                    }
                    return null;
                })
                .filter(Objects::nonNull)
                .findFirst();

        if (graphOptional.isPresent()) {
            var graph = graphOptional.get();

            Files.lines(in.toPath())
                    .filter(line -> line.matches(NODE_PATTERN_STR))
                    .forEach(line -> {
                        var matcher = NODE_PATTERN.matcher(line);
                        matcher.matches();
                        var x = Integer.parseInt(matcher.group(1));
                        var y = Integer.parseInt(matcher.group(2));

                        graph.insertEdge(x, y);
                    });

            return graph;
        } else {
            return null;
        }
    }

}
