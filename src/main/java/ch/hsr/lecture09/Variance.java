package ch.hsr.lecture09;

import ch.hsr.commons.graphik.Graphik;
import ch.hsr.commons.graphik.Rectangle;
import ch.hsr.commons.graphik.Square;
import ch.hsr.lecture09.stack.Stack;

public class Variance {

    public static void main(String[] argv) {
        var main = new Variance();

        // Stack<Graphik> stack = new Stack<Rectangle>();           // ERROR: Invariance

        // Zuweisungskompatibilität aufweichen:
        main.test_Covariance();
        main.test_Contravariance();


        var origin = new Stack<Rectangle>();
        var target = new Stack<Graphik>();

        // Covariance
        moveCo(origin, target);

        // Contravariance
        moveContra(origin, target);
    }

    private void test_Covariance() {
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

    private void test_Contravariance() {
        var origin = new Stack<Object>();
        origin.push("ABC");

        Stack<? super Rectangle> relaxed = origin;  // more flexible Rectangle Stack

        relaxed.push(new Rectangle());  // OK: write
        relaxed.push(new Square());

        // Rectangle x = relaxed.pop(); // NOK: read the type, because it was e.g. String
        Object t = relaxed.pop();       // OK: read Object
    }

    private static <T> void moveCo(Stack<? extends T> from, Stack<T> to) {  // T = Graphic
        while (!from.isEmpty()) {
            to.push(from.pop());
        }
    }

    private static <T> void moveContra(Stack<T> from, Stack<? super T> to) {    // T = Rectangle
        while (!from.isEmpty()) {
            to.push(from.pop());
        }
    }

    private static <T> void moveBoth(Stack<? extends T> from, Stack<? super T> to) {    // can be T = Graphik or T = Rectangle. Compiler takes the more specific type => Rectangle
        while (!from.isEmpty()) {
            to.push(from.pop());
        }
    }

}
