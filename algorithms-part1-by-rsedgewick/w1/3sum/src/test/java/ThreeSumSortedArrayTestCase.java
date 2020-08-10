import main.java.ThreeSumSortedArray;
import main.java.Utils;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ThreeSumSortedArrayTestCase {
    public final ThreeSumSortedArray threeSumSortedArray = new ThreeSumSortedArray();

    @ParameterizedTest
    @CsvSource({
            "1, 12, '3, 3, 4, 6'",
            "0, 10, '3, 3, 3'",
            "0, 0,  ''"
    })
    void threeSumBruteForceCount(int expectedCount, int sum, String a) {
        int[] arr = Utils.getInts(a);
        assertEquals(expectedCount, threeSumSortedArray.count(arr, sum));
    }
}
