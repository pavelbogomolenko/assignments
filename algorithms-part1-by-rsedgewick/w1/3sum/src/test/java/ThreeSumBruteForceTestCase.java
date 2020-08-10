import main.java.ThreeSumBruteForce;
import main.java.Utils;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ThreeSumBruteForceTestCase {
    public final ThreeSumBruteForce threeSumBruteForce = new ThreeSumBruteForce();

    @ParameterizedTest
    @CsvSource({
            "1, 10, '3, 3, 4'",
            "0, 10, '3, 3, 3'",
            "0, 0,  ''",
            "2, 14, '7, 7, 0, 0'",
            "2, 10, '0, 0, 5, 5'"
    })
    void threeSumBruteForceCount(int expectedCount, int sum, String a) {
        int[] arr = Utils.getInts(a);
        assertEquals(expectedCount, threeSumBruteForce.count(arr, sum));
    }
}
