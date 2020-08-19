import main.java.FastCollinearPoints;
import main.java.LineSegment;
import main.java.Point;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class FastCollinearPointsTestCase {
    @Test
    void constructorShouldThrowIfArgIsNull() {
        assertThrows(IllegalArgumentException.class, ()-> {
            new FastCollinearPoints(null);
        });
    }

    @Test
    void constructorShouldThrowIfAnyArgIsNull() {
        assertThrows(IllegalArgumentException.class, ()-> {
            Point[] pointsIncludingNull = new Point[] { new Point(1, 2), new Point(0,0), new Point(3,4), null};
            new FastCollinearPoints(pointsIncludingNull);
        });
    }

    @Test
    void constructorShouldThrowIfArgCointainsDuplicatePoints() {
        assertThrows(IllegalArgumentException.class, ()-> {
            Point[] someDublicatePoints = new Point[] { new Point(1, 2), new Point(0,0), new Point(0,1), new Point(1, 2)};
            new FastCollinearPoints(someDublicatePoints);
        });
    }

    @Test
    void given11CollinearPointsShouldReturn2Segment() {
        Point[] points = new Point[]{
                new Point(2, 2),
                new Point(2, 4),
                new Point(5, 6),
                new Point(3, 2),
                new Point(5, 4),
                new Point(6, 5),
                new Point(8, 7),
                new Point(2, 7),
                new Point(3, 6),
                new Point(4, 5),
                new Point(8, 6),
        };

        FastCollinearPoints collinearPoints = new FastCollinearPoints(points);

        LineSegment[] segments = collinearPoints.segments();
        assertEquals(2, collinearPoints.numberOfSegments());
        assertEquals("(3, 2) -> (8, 7)", segments[0].toString());
        assertEquals("(5, 4) -> (2, 7)", segments[1].toString());
    }

    @Test
    void given6CollinearPointsShouldReturn1Segment() {
        Point[] points = new Point[] {
                new Point(19000, 10000),
                new Point(18000, 10000),
                new Point(32000, 10000),
                new Point(21000, 10000),
                new Point(1234, 5678),
                new Point(14000, 10000)
        };

        FastCollinearPoints collinearPoints = new FastCollinearPoints(points);

        LineSegment[] segments = collinearPoints.segments();
        assertEquals(1, collinearPoints.numberOfSegments());
        assertEquals("(14000, 10000) -> (32000, 10000)", segments[0].toString());
    }

    @Test
    void inputFromCourseraExample() {
        Point[] points = new Point[] {
                new Point(10000,0),
                new Point(0,10000),
                new Point(3000,7000),
                new Point(7000,3000),
                new Point(20000, 21000),
                new Point(3000,4000),
                new Point(14000,15000),
                new Point(6000, 7000),
        };
        FastCollinearPoints collinearPoints = new FastCollinearPoints(points);

        LineSegment[] segments = collinearPoints.segments();
        assertEquals(2, collinearPoints.numberOfSegments());
        assertEquals("(10000, 0) -> (0, 10000)", segments[0].toString());
        assertEquals("(3000, 4000) -> (20000, 21000)", segments[1].toString());
    }
}
