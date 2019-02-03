package ch.study.commons.array;

import java.util.Random;
import java.util.stream.IntStream;

public class ArrayUtilities {

    public static int[] getTestArray(int count) {
        final Random random = new Random(4711);
        return IntStream.generate(random::nextInt).limit(count).toArray();
    }

}
