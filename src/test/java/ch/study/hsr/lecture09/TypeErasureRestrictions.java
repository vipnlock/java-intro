package ch.study.hsr.lecture09;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

/**
 * Demo of restrictions due to Type Erasure.
 *
 * @param <T> - non reifiable type
 */
class TypeErasureRestrictions<T> {

    @Test
    @DisplayName("ERROR: type-argument cannot be of primitive type")
    void test1() {
//        Stack<int> intStack;
    }

    @Test
    @DisplayName("ERROR: No new")
    void test4() {
//        new T();                  // ERROR: Cannot instantiate type parameter directly
//        T[] array = new T[100];   // ERROR: Cannot instantiate array of type variable
    }

    @Test
    @DisplayName("ERROR: no instanceof with type-variables")
    void test2(Object x) {
//        if (x instanceof T) {
//        }
    }

    /*
     * Type-Casts zu T sind ungeprüft.
     */
    @Test
    @DisplayName("WARNING: type casts are not checked for type-variables")
    void test3(Object x) {
        T y = (T) x;
    }

    /*
     * Type-Casts zu T[] sind ungeprüft.
     */
    @Test
    @DisplayName("WARNING: Array type casts are not checked")
    void test5() {
        T[] array2 = (T[]) new Object[100];
    }

    /*
     * Kein Ableiten von T (extends T);
     * ERROR: Cannot inherit from T (because of unknown constructor)
     */
//    private class MyTest extends T { }

    /*
     * Keine generische Exceptions;
     * ERROR: because catch(generic-exception) is the same as instanceof
     */
//    private class MyExceptionClass<T> extends Exception { }

}
