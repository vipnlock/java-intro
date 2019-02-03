package ch.study.commons.graphik;

import java.io.Serializable;

public interface Graphik extends Serializable {

    default String draw() {
        return "Graphik";
    }

}
