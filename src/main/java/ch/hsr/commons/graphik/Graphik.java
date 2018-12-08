package ch.hsr.commons.graphik;

import java.io.Serializable;

public interface Graphik extends Serializable {

    default void draw() {
        System.out.println("Graphik");
    }

}
