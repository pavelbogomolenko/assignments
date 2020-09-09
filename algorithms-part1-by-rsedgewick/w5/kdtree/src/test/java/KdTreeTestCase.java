import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.RectHV;
import main.java.KdTree;
import org.junit.jupiter.api.Test;

import java.util.Iterator;

import static org.junit.jupiter.api.Assertions.*;

public class KdTreeTestCase {

    @Test
    void testPointSETIsEmptyTrue() {
        KdTree kdTree = new KdTree();
        assertTrue(kdTree.isEmpty());
    }

    @Test
    void testPointSETSizeIsZero() {
        KdTree kdTree = new KdTree();
        assertEquals(0, kdTree.size());
    }

    @Test
    void testShouldInsertRootPointAndMakeKdTreeNotEmpty() {
        KdTree kdTree = new KdTree();
        kdTree.insert(new Point2D(0, 0));
        assertFalse(kdTree.isEmpty());
        assertEquals(1, kdTree.size());
    }

    @Test
    void testShouldHaveLeftAndRightPointsToRoot() {
        KdTree kdTree = new KdTree();
        kdTree.insert(new Point2D(0.7, 0.2));
        kdTree.insert(new Point2D(0.5, 0.4));
        kdTree.insert(new Point2D(0.2, 0.3));
        kdTree.insert(new Point2D(0.4, 0.7));
        kdTree.insert(new Point2D(0.9, 0.6));
        kdTree.insert(new Point2D(0.6, 0.1));
//        kdTree.insert(new Point2D(0.45, 0.8));

        assertEquals(6, kdTree.size());
    }

    @Test
    void testShouldThrowWhenInsertingNull() {
        assertThrows(IllegalArgumentException.class, () -> {
            KdTree kdTree = new KdTree();
            kdTree.insert(null);
        });
    }

    @Test
    void testShouldContainGivenRootPoint() {
        KdTree kdTree = new KdTree();
        Point2D givenPoint = new Point2D(0.7, 0.2);
        kdTree.insert(givenPoint);
        kdTree.insert(new Point2D(0.5, 0.4));
        kdTree.insert(new Point2D(0.2, 0.3));
        kdTree.insert(new Point2D(0.4, 0.7));
        kdTree.insert(new Point2D(0.9, 0.6));
        kdTree.insert(new Point2D(0.6, 0.1));
        kdTree.insert(new Point2D(0.1, 0.1));
        kdTree.insert(new Point2D(0.8, 0.2));

        assertTrue(kdTree.contains(givenPoint));
    }

    @Test
    void testShouldContainGivenPoint() {
        KdTree kdTree = new KdTree();
        kdTree.insert(new Point2D(0.7, 0.2));
        kdTree.insert(new Point2D(0.5, 0.4));
        kdTree.insert(new Point2D(0.2, 0.3));
        kdTree.insert(new Point2D(0.4, 0.7));
        Point2D givenPoint = new Point2D(0.9, 0.6);
        kdTree.insert(givenPoint);
        assertTrue(kdTree.contains(givenPoint));
    }

    @Test
    void testShouldContainGivenPointHavingSameXCoordPoint1() {
        KdTree kdTree = new KdTree();
        kdTree.insert(new Point2D(0.7, 0.2));
        kdTree.insert(new Point2D(0.5, 0.4));
        kdTree.insert(new Point2D(0.9, 0.6));
        Point2D givenPoint = new Point2D(0.9, 0.7);
        kdTree.insert(givenPoint);
        assertTrue(kdTree.contains(givenPoint));
    }

    @Test
    void testShouldContainGivenPointHavingSameXCoordPoint3() {
        KdTree kdTree = new KdTree();
        kdTree.insert(new Point2D(0.7, 0.2));
        Point2D givenPoint = new Point2D(0.7, 0.3);
        kdTree.insert(givenPoint);
        kdTree.insert(new Point2D(0.9, 0.6));
        assertTrue(kdTree.contains(givenPoint));
    }
    @Test
    void testShouldContainGivenPointHavingSameXCoordPoint4() {
        KdTree kdTree = new KdTree();
        kdTree.insert(new Point2D(0.7, 0.2));
        kdTree.insert(new Point2D(0.7, 0.3));
        kdTree.insert(new Point2D(0.8, 0.6));
        Point2D givenPoint = new Point2D(0.7, 0.1);
        kdTree.insert(givenPoint);

        assertTrue(kdTree.contains(givenPoint));
    }

    @Test
    void testShouldContainGivenPointHavingSameXCoordPoint5() {
        KdTree kdTree = new KdTree();
//        kdTree.insert(new Point2D(0.7, 0.2));
//        kdTree.insert(new Point2D(0.7, 0.3));
//        kdTree.insert(new Point2D(0.8, 0.2));
//        Point2D givenPoint = new Point2D(0.7, 0.1);
//        kdTree.insert(givenPoint);

        kdTree.insert(new Point2D(1.0, 0.1));
        kdTree.insert(new Point2D(0.1, 0.1));
        kdTree.insert(new Point2D(0.2, 0.1));
        kdTree.insert(new Point2D(0.5, 0.5));

        assertTrue(kdTree.contains(new Point2D(0.5, 0.5)));
    }

    @Test
    void testShouldContainGivenPointHavingSameXCoordPoint6() {
        KdTree kdTree = new KdTree();
        kdTree.insert(new Point2D(0.7, 0.2));
        kdTree.insert(new Point2D(0.7, 0.3));
        kdTree.insert(new Point2D(0.6, 0.2));
        kdTree.insert(new Point2D(0.8, 0.2));
        Point2D givenPoint = new Point2D(0.9, 0.5);
        kdTree.insert(givenPoint);

        assertTrue(kdTree.contains(givenPoint));
    }

    @Test
    void testShouldContainGivenPointHavingSameXCoordPoint2() {
        KdTree kdTree = new KdTree();
        kdTree.insert(new Point2D(0.7, 0.2));
        kdTree.insert(new Point2D(0.5, 0.4));
        Point2D givenPoint = new Point2D(0.5, 0.3);
        kdTree.insert(givenPoint);
        kdTree.insert(new Point2D(0.9, 0.6));
        assertTrue(kdTree.contains(givenPoint));
    }

    @Test
    void testShouldContainGivenPointHavingSameYCoordPoint1() {
        KdTree kdTree = new KdTree();
        kdTree.insert(new Point2D(0.7, 0.2));
        kdTree.insert(new Point2D(0.5, 0.4));
        kdTree.insert(new Point2D(0.8, 0.6));
        Point2D givenPoint = new Point2D(0.9, 0.6);
        kdTree.insert(givenPoint);
        assertTrue(kdTree.contains(givenPoint));
    }

    @Test
    void testShouldNotContainGivenPoint() {
        KdTree kdTree = new KdTree();
        kdTree.insert(new Point2D(0.7, 0.2));
        kdTree.insert(new Point2D(0.5, 0.4));
        kdTree.insert(new Point2D(0.2, 0.3));
        kdTree.insert(new Point2D(0.4, 0.7));
        kdTree.insert(new Point2D(0.9, 0.6));
        Point2D givenPoint = new Point2D(3, 6);
        assertFalse(kdTree.contains(givenPoint));
    }

    @Test
    void testMultiplePointsInsideGivenRectHV() {
        RectHV givenRect = new RectHV(0, 0, 4, 4);

        KdTree kdTree = new KdTree();
        Point2D givenPoint1 = new Point2D(1, 1);
        Point2D givenPoint2 = new Point2D(2, 2);
        kdTree.insert(givenPoint1);
        kdTree.insert(givenPoint2);

        Iterator<Point2D> pointIterator = kdTree.range(givenRect).iterator();
        assertEquals(givenPoint1, pointIterator.next());
        assertEquals(givenPoint2, pointIterator.next());
    }

    @Test
    void testShouldThrowWhenCallingRangeWithNullArg() {
        assertThrows(IllegalArgumentException.class, () -> {
            KdTree kdTree = new KdTree();
            Point2D givenPoint = new Point2D(4, 0);
            kdTree.insert(givenPoint);

            kdTree.range(null).iterator();
        });
    }

    @Test
    void testNullNearestPointsWhenSetIsEmpty() {
        KdTree kdTree = new KdTree();

        Point2D givenNearest = new Point2D(0, 0);

        assertEquals(null, kdTree.nearest(givenNearest));
    }

    @Test
    void testOneNearestPointWhenThereIsOnlyOnePointInSet() {
        KdTree kdTree = new KdTree();
        Point2D p = new Point2D(0,0);
        kdTree.insert(p);

        Point2D givenNearest = new Point2D(10, 10);

        assertEquals(p, kdTree.nearest(givenNearest));
    }

    @Test
    void testGivenMultiplePointsInSetFindNearestPointCase1() {
        KdTree kdTree = new KdTree();
        Point2D p0 = new Point2D(0,0);
        Point2D p1 = new Point2D(1.0,1.0);
        Point2D p2 = new Point2D(0.5,0.5);
        Point2D p3 = new Point2D(0.75,0.75);
        Point2D p4 = new Point2D(0.4,0);
        Point2D nearestToSomePoint = new Point2D(0.2, 0.2);
        kdTree.insert(p0);
        kdTree.insert(p1);
        kdTree.insert(p2);
        kdTree.insert(p3);
        kdTree.insert(p4);
        kdTree.insert(nearestToSomePoint);

        Point2D somePoint = new Point2D(0.25, 0.20);
        assertEquals(nearestToSomePoint, kdTree.nearest(somePoint));
    }

    @Test
    void testGivenMultiplePointsInSetFindNearestPointCase2() {
        KdTree kdTree = new KdTree();

        kdTree.insert(new Point2D(0.372, 0.497));
        kdTree.insert(new Point2D(0.564, 0.413));
        kdTree.insert(new Point2D(0.226, 0.577));
        kdTree.insert(new Point2D(0.144, 0.179));
        kdTree.insert(new Point2D(0.083, 0.510));
        kdTree.insert(new Point2D(0.320, 0.708));
        kdTree.insert(new Point2D(0.417, 0.362));
        kdTree.insert(new Point2D(0.862, 0.825));
        kdTree.insert(new Point2D(0.785, 0.725));
        kdTree.insert(new Point2D(0.499, 0.208));

        Point2D somePoint = new Point2D(0.11, 0.11);

        assertEquals(new Point2D(0.144, 0.179), kdTree.nearest(somePoint));
    }

    @Test
    void testNearestShouldThrowWhenGivenNullArg() {
        assertThrows(IllegalArgumentException.class, () -> {
            KdTree kdTree = new KdTree();
            kdTree.insert(new Point2D(0,0));
            kdTree.insert(new Point2D(100,100));

            kdTree.nearest(null);
        });
    }
}
