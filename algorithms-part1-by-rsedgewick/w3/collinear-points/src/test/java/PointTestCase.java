import main.java.Point;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

public class PointTestCase {

    @ParameterizedTest
    @CsvSource({
            //x0, y0, x1, y1
            "1, 3, 0, 4",
            "1, 3, 2, 3",
            "-2, -3, -1, -2"
    })
    void compareToShouldReturnNegativeIntIfInvokingPointIsLessThanArgumentPoint(int x0, int y0, int x1, int y1) {
        Point invokingPoint = new Point(x0, y0);
        Point argumentPoint = new Point(x1, y1);
        assertEquals(-1, invokingPoint.compareTo(argumentPoint));
    }

    @ParameterizedTest
    @CsvSource({
            //x0, y0, x1, y1
            "1, 4, 0, 3",
            "1, 4, 0, 4",
    })
    void compareToShouldReturnPositiveIntIfInvokingPointIsGreaterThanArgumentPoint(int x0, int y0, int x1, int y1) {
        Point invokingPoint = new Point(x0, y0);
        Point argumentPoint = new Point(x1, y1);
        assertEquals(1, invokingPoint.compareTo(argumentPoint));
    }

    @Test
    void compareToShouldReturnZeroIfInvokingPointIsEqualToArgumentPoint() {
        Point invokingPoint = new Point(2, 3);
        Point argumentPoint = new Point(2, 3);
        assertEquals(0, invokingPoint.compareTo(argumentPoint));
    }

    @ParameterizedTest
    @CsvSource({
            //x0, y0, x1, y1
            "2, 2, 2, 7",
            "-3, -1, -3, -6"
    })
    void slopeToReturnsPosInfIfThereIsHorizontalLineBetweenInvokingPointAndArgumentPoint(int x0, int y0, int x1, int y1) {
        Point invokingPoint = new Point(x0, y0);
        Point argumentPoint = new Point(x1, y1);
        assertEquals(Double.POSITIVE_INFINITY, invokingPoint.slopeTo(argumentPoint));
    }

    @ParameterizedTest
    @CsvSource({
            //x0, y0, x1, y1
            "1, 2, 3, 4",
            "12, 2, 5, 6",
            "5, 4, 2, 7"
    })
    void slopeToReturnsDoubleIfThereIsSlopeBetweenInvokingPointAndArgumentPoint(int x0, int y0, int x1, int y1) {
        Point invokingPoint = new Point(x0, y0);
        Point argumentPoint = new Point(x1, y1);
        assertTrue(invokingPoint.slopeTo(argumentPoint) == (double) (y1 - y0) / (x1 - x0));
    }

    @Test
    void slopeToReturnsNegInfIfInvokingPointIsEqualToArgumentPoint() {
        Point invokingPoint = new Point(2, 3);
        Point argumentPoint = new Point(2, 3);
        assertEquals(Double.NEGATIVE_INFINITY, invokingPoint.slopeTo(argumentPoint));
    }

    @Test
    void slopeToHorizontalSlope() {
        Point invokingPoint = new Point(2, 2);
        Point argumentPoint = new Point(3, 2);
        assertEquals(0.0, invokingPoint.slopeTo(argumentPoint));
    }

    @Test
    void testSlopeOrderComparatorGreater() {
        Point p0 = new Point(1, 1);
        Point p1 = new Point(2, 3);
        Point p2 = new Point(5, 5);

        assertEquals(1, p0.slopeOrder().compare(p1, p2));
    }

    @Test
    void testSlopeOrderComparatorLess() {
        Point p0 = new Point(1, 1);
        Point p1 = new Point(6, 4);
        Point p2 = new Point(5, 5);

        assertEquals(-1, p0.slopeOrder().compare(p1, p2));
    }

    @Test
    void testSlopeOrderComparatorAllEqual() {
        Point p0 = new Point(1, 1);
        Point p1 = new Point(1, 1);
        Point p2 = new Point(1, 1);

        assertEquals(0, p0.slopeOrder().compare(p1, p2));
    }

    @Test
    void testSlopeOrderComparatorP0AndP1AreEqual() {
        Point p0 = new Point(1, 1);
        Point p1 = new Point(1, 1);
        Point p2 = new Point(2, 2);

        assertEquals(-1, p0.slopeOrder().compare(p1, p2));
    }
}
