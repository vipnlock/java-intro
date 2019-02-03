package ch.study.hsr.lecture09;

import static ch.study.hsr.lecture09.Lecture09Helper.moveCo;
import static ch.study.hsr.lecture09.Lecture09Helper.moveContra;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import ch.study.commons.graphik.Graphik;
import ch.study.commons.graphik.Rectangle;
import ch.study.commons.graphik.Square;
import ch.study.hsr.lecture09.stack.Stack;

public class VarianceTest {

    @Test
    @DisplayName("ERROR: Invariance")
    void invariance() {
        // Stack<Graphik> stack = new Stack<Rectangle>();
    }

    @Test
    @DisplayName("Zuweisungskompatibilität aufweichen: Covariance")
    void covariance() {
        Stack<Rectangle> origin = new Stack<>();
        origin.push(new Square());
        origin.push(new Rectangle());

        // Co-variance, not type bounds
        Stack<? extends Graphik> relaxed = origin;  // more flexible Graphik Stack
        // relaxed = new Stack<Graphik>();    // OK: hier kommt the Graphic
        // relaxed = new Stack<Circle>();     // OK: oder ein Subtyp von Graphik

        relaxed.pop();                  // OK: read
        // relaxed.push(new Circle());  // NOK: write
    }

    @Test
    @DisplayName("Zuweisungskompatibilität aufweichen: Contravariance")
    void contravariance() {
        var origin = new Stack<Object>();
        origin.push("ABC");

        Stack<? super Rectangle> relaxed = origin;  // more flexible Rectangle Stack

        relaxed.push(new Rectangle());  // OK: write
        relaxed.push(new Square());

        // Rectangle x = relaxed.pop(); // NOK: read the type, because it was e.g. String
        Object t = relaxed.pop();       // OK: read Object
    }

    @Test
    @DisplayName("Move: Covariance")
    void moveCovariance() {
        var origin = new Stack<Rectangle>();
        var target = new Stack<Graphik>();

        moveCo(origin, target);
    }

    @Test
    @DisplayName("Move: Contravariance")
    void moveContravariance() {
        var origin = new Stack<Rectangle>();
        var target = new Stack<Graphik>();

        moveContra(origin, target);
    }

}
