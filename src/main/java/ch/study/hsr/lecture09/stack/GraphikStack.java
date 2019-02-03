package ch.study.hsr.lecture09.stack;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import ch.study.commons.graphik.Graphik;

/**
 * GraphikStack is demo of type-parameters usage.
 *
 * @param <T> - type-parameter, upper type bound:
 *              T muss mindestens Graphik sein UND ein Serializable
 */
public class GraphikStack<T extends Graphik & Serializable> extends Stack<T> {

    public List<String> drawAll() {
        List<String> result = new ArrayList<>();
        for (T item : this) {
            result.add(item.draw());    // dynamic dispatch
        }
        return result;

//        return StreamSupport.stream(this.spliterator(), false)
//                     .map(Graphik::draw)    // dynamic dispatch
//                     .collect(Collectors.toList());
    }

}
