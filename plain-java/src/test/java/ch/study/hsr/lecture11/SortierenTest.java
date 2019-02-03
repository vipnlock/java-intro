package ch.study.hsr.lecture11;

import java.util.Arrays;

import org.junit.jupiter.api.Test;

import ch.study.commons.array.ArrayUtilities;

class SortierenTest {

    private final static int NUMBER = 10_000_000;

    @Test
    void bubbleSortTest() {
        long start = System.nanoTime();

        Sortieren.bubbleSort(ArrayUtilities.getTestArray(100_000));

        System.out.println("Bubble sort: " + ((System.nanoTime() - start) / 1000_000) + " ms");
    }

    @Test
    void quickSort() {
        long start = System.nanoTime();

        Sortieren.quickSort(ArrayUtilities.getTestArray(NUMBER), 0, NUMBER - 1);

        System.out.println("Quick sort: " + ((System.nanoTime() - start) / 1000_000) + " ms");
    }

    @Test
    void javaSort() {
        long start = System.nanoTime();
        Arrays.sort(ArrayUtilities.getTestArray(NUMBER));
        System.out.println("Java dual pivot quick sort: " + ((System.nanoTime() - start) / 1_000_000) + " ms");
    }

}
