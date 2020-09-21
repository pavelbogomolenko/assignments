import main.java.FindMaxSubArray;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class FindMaxSubArrayTestCase {
//    @Test
//    void testFindMaxSubArrayGivenArrayOfOne() {
//        double[] input_array = new double[] {1};
//        input_array[0] = 10;
//
//        double[] expectedResult = new double[] {0, 0, 10};
//        assertArrayEquals(expectedResult, FindMaxSubArray.find(input_array));
//    }
//
//    @Test
//    void testFindMaxSubArrayGivenArrayOfTwoPositive() {
//        double[] input_array = new double[] {10, 20};
//
//        double[] expectedResult = new double[] {0, 1, 30};
//        assertArrayEquals(expectedResult, FindMaxSubArray.find(input_array));
//    }
//
//    @Test
//    void testFindMaxSubArrayGivenArrayOfThreeTwoPositiveOneNegative() {
//        double[] input_array = new double[] {10, 20, -20};
//
//        double[] expectedResult = new double[] {0, 1, 30};
//        assertArrayEquals(expectedResult, FindMaxSubArray.find(input_array));
//    }

    @Test
    void testFindMaxSubArrayGivenArray() {
        double[] input_array = new double[] {13, -3, -25, 20, -3, -16, -23, 18, 20, -7, 12, -5, -22, 15, -4, 7};

        double[] expectedResult = new double[] {7, 10, 43};
        assertArrayEquals(expectedResult, FindMaxSubArray.find(input_array));
    }
}
