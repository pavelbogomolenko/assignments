import main.java.MergeSort;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.Collections;

import static org.junit.jupiter.api.Assertions.*;

public class MergeSortTestCase {
    @Test
    void ascSortIntArrayOfTwoItems() {
        Comparable<Integer>[] a = new Comparable[] {2, 1};
        Comparable<Integer>[] aCopy = Arrays.copyOf(a, a.length);
        Arrays.sort(a);
        MergeSort.sort(aCopy);

        assertArrayEquals(a, aCopy);
    }

    @Test
    void ascSortIntArrayOfThreeItems() {
        Comparable<Integer>[] a = new Comparable[] {2, 1, 4};
        Comparable<Integer>[] aCopy = Arrays.copyOf(a, a.length);
        Arrays.sort(a);
        MergeSort.sort(aCopy);

        assertArrayEquals(a, aCopy);
    }
}
