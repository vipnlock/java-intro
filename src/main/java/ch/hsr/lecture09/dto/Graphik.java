package ch.hsr.lecture09.dto;

import java.io.Serializable;

public interface Graphik extends Serializable {

    default void draw() {
        System.out.println("Graphik");
    }

}
