import static org.junit.jupiter.api.Assertions.*;

import edu.princeton.cs.algs4.RectHV;
import org.junit.jupiter.api.Test;

import java.util.Iterator;

import edu.princeton.cs.algs4.Point2D;
import main.java.PointSET;

public class PointSETTestCase {

    @Test
    void testPointSETIsEmptyTrue() {
        PointSET pSet = new PointSET();
        assertTrue(pSet.isEmpty());
    }

    @Test
    void testPointSETSizeIsZero() {
        PointSET pSet = new PointSET();
        assertEquals(0, pSet.size());
    }

    @Test
    void testShouldInsertPointAndMakePsetNotEmpty() {
        PointSET pSet = new PointSET();
        pSet.insert(new Point2D(0, 0));
        assertFalse(pSet.isEmpty());
        assertEquals(1, pSet.size());
    }

    @Test
    void testShouldThrowWhenInsertingNull() {
        assertThrows(IllegalArgumentException.class, () -> {
            PointSET pSet = new PointSET();
            pSet.insert(null);
        });
    }

    @Test
    void testShouldContainGivenPointAfterInsertingIt() {
        PointSET pSet = new PointSET();
        pSet.insert(new Point2D(0, 0));
        Point2D givenPoint = new Point2D(1, 1);
        assertFalse(pSet.contains(givenPoint));
        pSet.insert(givenPoint);
        assertTrue(pSet.contains(givenPoint));
    }

    @Test
    void testShouldThrowCallingContainsMethodWithNullArg() {
        assertThrows(IllegalArgumentException.class, () -> {
            PointSET pSet = new PointSET();
            pSet.insert(new Point2D(0, 0));
            pSet.contains(null);
        });
    }

    @Test
    void testOnePointInsideGivenRectHV() {
        RectHV givenRect = new RectHV(0, 0, 4, 4);

        PointSET pSet = new PointSET();
        Point2D givenPoint = new Point2D(1, 1);
        pSet.insert(givenPoint);

        Iterator<Point2D> pointIterator = pSet.range(givenRect).iterator();
        assertEquals(givenPoint, pointIterator.next());
    }

    @Test
    void testNullPointsInsideGivenRectHV() {
        RectHV givenRect = new RectHV(0, 0, 4, 4);

        PointSET pSet = new PointSET();
        Point2D pointOutsideRect1 = new Point2D(5, 5);
        Point2D pointOutsideRect2 = new Point2D(6, 6);
        pSet.insert(pointOutsideRect1);
        pSet.insert(pointOutsideRect2);

        Iterator<Point2D> pointIterator = pSet.range(givenRect).iterator();
        assertFalse(pointIterator.hasNext());
    }

    @Test
    void testMultiplePointsInsideGivenRectHV() {
        RectHV givenRect = new RectHV(0, 0, 4, 4);

        PointSET pSet = new PointSET();
        Point2D givenPoint1 = new Point2D(1, 1);
        Point2D givenPoint2 = new Point2D(2, 2);
        pSet.insert(givenPoint1);
        pSet.insert(givenPoint2);

        Iterator<Point2D> pointIterator = pSet.range(givenRect).iterator();
        assertEquals(givenPoint1, pointIterator.next());
        assertEquals(givenPoint2, pointIterator.next());
    }

    @Test
    void testOnePointOnBoundaryOfGivenRectHV() {
        RectHV givenRect = new RectHV(0, 0, 4, 4);

        PointSET pSet = new PointSET();
        Point2D givenPoint = new Point2D(4, 0);
        pSet.insert(givenPoint);

        Iterator<Point2D> pointIterator = pSet.range(givenRect).iterator();
        assertEquals(givenPoint, pointIterator.next());
    }

    @Test
    void testShouldThrowWhenCallingRangeWithNullArg() {
        assertThrows(IllegalArgumentException.class, () -> {
            PointSET pSet = new PointSET();
            Point2D givenPoint = new Point2D(4, 0);
            pSet.insert(givenPoint);

            pSet.range(null).iterator();
        });
    }

    @Test
    void testNullNearestPointsWhenSetIsEmpty() {
        PointSET pSet = new PointSET();

        Point2D givenNearest = new Point2D(0, 0);

        assertEquals(null, pSet.nearest(givenNearest));
    }

    @Test
    void testOneNearestPointWhenThereIsOnlyOnePointInSet() {
        PointSET pSet = new PointSET();
        Point2D p = new Point2D(0,0);
        pSet.insert(p);

        Point2D givenNearest = new Point2D(10, 10);

        assertEquals(p, pSet.nearest(givenNearest));
    }


    @Test
    void testGivenMultiplePointsInSetFindNearestPoint() {
        PointSET pSet = new PointSET();
        Point2D p0 = new Point2D(0,0);
        Point2D p1 = new Point2D(100,100);
        Point2D p2 = new Point2D(50,50);
        Point2D p3 = new Point2D(75,75);
        Point2D p4 = new Point2D(40,0);
        Point2D nearestToSomePoint = new Point2D(20, 20);
        pSet.insert(p0);
        pSet.insert(p1);
        pSet.insert(p2);
        pSet.insert(p3);
        pSet.insert(p4);
        pSet.insert(nearestToSomePoint);

        Point2D somePoint = new Point2D(25, 20);
        assertEquals(nearestToSomePoint, pSet.nearest(somePoint));
    }

    @Test
    void testNearestShouldThrowWhenGivenNullArg() {
        assertThrows(IllegalArgumentException.class, () -> {
            PointSET pSet = new PointSET();
            pSet.insert(new Point2D(0,0));
            pSet.insert(new Point2D(100,100));

            pSet.nearest(null);
        });
    }
}
