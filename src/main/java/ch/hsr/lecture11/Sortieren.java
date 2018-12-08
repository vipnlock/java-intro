package ch.hsr.lecture11;

import java.util.Arrays;

import ch.hsr.commons.array.ArrayUtilities;

public class Sortieren {

    private final static int NUMBER = 10_000_000;

    public static void main(String[] argv) {
        var sMain = new Sortieren();

        long start = System.nanoTime();
//        sMain.bubbleSort(ArrayUtilities.getTestArray(NUMBER));
//        System.out.println("Bubble sort: " + ((System.nanoTime() - start) / 1000_000) + " ms");

//        start = System.nanoTime();
        sMain.quickSort(ArrayUtilities.getTestArray(NUMBER), 0, NUMBER - 1);
        System.out.println("Quick sort: " + ((System.nanoTime() - start) / 1000_000) + " ms");

        // java sort
        start = System.nanoTime();
        Arrays.sort(ArrayUtilities.getTestArray(NUMBER));
        System.out.println("Java dual pivor quick sort: " + ((System.nanoTime() - start) / 1_000_000) + " ms");
    }

    private void bubbleSort(int[] array) {
        boolean swapped;
        do {
            swapped = false;
            for (int i = 1; i < array.length; i++) {
                if (array[i - 1] > array[i]) {
                    swap(array, i - 1, i);
                    swapped = true;
                }
            }
        } while (swapped);
    }

    private void quickSort(int[] array, int left, int right) {
        if (right <= left) {
            return;
        }
        // choose pivot
        int pivot = array[(left + right) / 2];

        // divide: partitioning
        int lower = left, upper = right;
        while (lower <= upper) {
            while (array[lower] < pivot) { lower++; }
            while (array[upper] > pivot) { upper--; }
            if (lower <= upper) {
                swap(array, lower++, upper--);
            }
        }

        // conquer: recursion
        quickSort(array, left, upper);
        quickSort(array, lower, right);
        // recursion
    }

    private static void swap(int[] array, int index1, int index2) {
        var tmp = array[index1];
        array[index1] = array[index2];
        array[index2] = tmp;
    }
}
