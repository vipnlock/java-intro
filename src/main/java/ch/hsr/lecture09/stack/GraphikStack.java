package ch.hsr.lecture09.stack;

import java.io.Serializable;

import ch.hsr.lecture09.dto.Graphik;
import ch.hsr.lecture09.stack.Stack;

/**
 *
 * @param <T> - type parameter;
 *           Upper Type-Bound: T muss mindestens Graphik sein UND ein Serializable
 */
public class GraphikStack<T extends Graphik & Serializable> extends Stack<T> {

    public void drawAll() {
        for (T item : this) {
            item.draw();    // dynamic dispatch
        }
    }

}
