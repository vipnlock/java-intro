package ch.study.hsr.lecture09.typeerasure;

/**
 * Demo of restrictions due to Type Erasure.
 *
 * @param <T> - non reifiable type
 */
public class Restrictions<T> {

    /**
     * Keine primitive Typen als Typ-Argument.
     */
    public void test1() {
//        Stack<int> intStack;    // ERROR: no primitive types, as type-arguments
    }

    /**
     * Kein instanceof.
     */
    public void test2(Object x) {
//        if (x instanceof T) {   // ERROR: no type tests, with type variables
//        }
    }

    /**
     * Type-Casts zu T sind ungeprüft.
     */
    public void test3(Object x) {
        T y = (T)x;             // WARNING: type casts not checked, for type variables
    }

    /**
     * Kein new T().
     */
    public void test4() {
//        new T();
    }

    /**
     * Arrays T[] sind ungeprüft.
     */
    public void test5() {
//        T[] array = new T[100];             // ERROR: Cannot instantiate array of type variable
        T[] array2 = (T[])new Object[100];  // WARNING: Array type casts not checked
    }

    /**
     * Kein Ableiten von T (extends T);
     */
//    private class MyTest extends T { }      // ERROR: Cannot inherit from T (because of unknown constructor)

    /**
     * Keine generische Exceptions
     */
//    private class MyExceptionClass<T> extends Exception { }

}
