package ch.hsr.lecture09.stack;

import java.io.Serializable;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import ch.hsr.commons.graphik.Graphik;

/**
 *
 * @param <T> - type parameter;
 *           Upper Type-Bound: T muss mindestens Graphik sein UND ein Serializable
 */
public class GraphikStack<T extends Graphik & Serializable> extends Stack<T> {

    public List<String> drawAll() {
        return StreamSupport.stream(this.spliterator(), false)
                     .map(Graphik::draw)    // dynamic dispatch
                     .collect(Collectors.toList());
    }

}
