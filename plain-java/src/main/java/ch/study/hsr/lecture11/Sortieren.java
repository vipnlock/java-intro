package ch.study.hsr.lecture11;

public class Sortieren {

    static void bubbleSort(int[] array) {
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

    static void quickSort(int[] array, int left, int right) {
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
