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

    @Test
    void test8Points() {
        Point[] points = new Point[] {
                new Point(3, 3),
                new Point(3, 4),
                new Point(3, 5),
                new Point(3, 6),
                new Point(4, 3),
                new Point(5, 3),
                new Point(6, 3),
        };
        FastCollinearPoints collinearPoints = new FastCollinearPoints(points);

        LineSegment[] segments = collinearPoints.segments();
        assertEquals(2, collinearPoints.numberOfSegments());
        assertEquals("(3, 3) -> (6, 3)", segments[0].toString());
        assertEquals("(3, 3) -> (3, 6)", segments[1].toString());
    }

    @Test
    void input9FromCourseraExample() {
        Point[] points = new Point[] {
                new Point(1000, 1000),
                new Point(2000, 2000),
                new Point(3000, 3000),
                new Point(4000, 4000),
                new Point(5000, 5000),
                new Point(6000, 6000),
                new Point(7000, 7000),
                new Point(8000, 8000),
                new Point(9000, 9000),
        };
        FastCollinearPoints collinearPoints = new FastCollinearPoints(points);

        LineSegment[] segments = collinearPoints.segments();
        assertEquals(1, collinearPoints.numberOfSegments());
        assertEquals("(1000, 1000) -> (9000, 9000)", segments[0].toString());
    }

    @Test
    void inputHorizontalAndVerticalExample() {
        Point[] points = new Point[] {
                new Point(10375, 12711),
                new Point(14226, 12711),
                new Point(18177, 12711),
                new Point(20385, 12711),
                new Point(1234, 4356),
                new Point(10000, 12711),
                new Point(10000, 13711),
                new Point(10000, 14711),
                new Point(10000, 15711),
        };
        FastCollinearPoints collinearPoints = new FastCollinearPoints(points);

        LineSegment[] segments = collinearPoints.segments();
        assertEquals(2, collinearPoints.numberOfSegments());
        assertEquals("(10000, 12711) -> (20385, 12711)", segments[0].toString());
        assertEquals("(10000, 12711) -> (10000, 15711)", segments[1].toString());
    }

    @Test
    void testFewHorizontalLines() {
        Point[] points = new Point[] {
                new Point(1, 1),
                new Point(2, 1),
                new Point(3, 1),
                new Point(4, 1),
                new Point(1, 2),
                new Point(2, 2),
                new Point(3, 2),
                new Point(4, 2),
        };
        FastCollinearPoints collinearPoints = new FastCollinearPoints(points);

        LineSegment[] segments = collinearPoints.segments();
        assertEquals(2, collinearPoints.numberOfSegments());
        assertEquals("(1, 1) -> (4, 1)", segments[0].toString());
        assertEquals("(1, 2) -> (4, 2)", segments[1].toString());
    }

    @Test
    void testGrid4x4() {
        Point[] points = new Point[] {
                new Point(1, 1),
                new Point(2, 1),
                new Point(3, 1),
                new Point(4, 1),
                new Point(1, 2),
                new Point(2, 2),
                new Point(3, 2),
                new Point(4, 2),
                new Point(1, 3),
                new Point(2, 3),
                new Point(3, 3),
                new Point(4, 3),
                new Point(1, 4),
                new Point(2, 4),
                new Point(3, 4),
                new Point(4, 4),
        };
        FastCollinearPoints collinearPoints = new FastCollinearPoints(points);

        LineSegment[] segments = collinearPoints.segments();
        assertEquals(10, collinearPoints.numberOfSegments());
    }

    @Test
    void test9FromCoursera() {
        Point[] points = new Point[] {
                new Point(9000, 9000),
                new Point(8000, 8000),
                new Point(7000, 7000),
                new Point(6000, 6000),
                new Point(5000, 5000),
                new Point(4000, 4000),
                new Point(3000, 3000),
                new Point(2000, 2000),
                new Point(1000, 1000),
        };
        FastCollinearPoints collinearPoints = new FastCollinearPoints(points);

        LineSegment[] segments = collinearPoints.segments();
        assertEquals(1, collinearPoints.numberOfSegments());
        assertEquals("(1000, 1000) -> (9000, 9000)", segments[0].toString());
    }

    @Test
    void testSequenceOfOpsFromCoursera() {
        Point[] points = new Point[] {
                new Point(9000, 9000),
                new Point(8000, 8000),
                new Point(7000, 7000),
                new Point(6000, 6000),
                new Point(5000, 5000),
                new Point(4000, 4000),
                new Point(3000, 3000),
                new Point(2000, 2000),
                new Point(1000, 1000),
        };
        FastCollinearPoints collinearPoints = new FastCollinearPoints(points);

        LineSegment[] segments = collinearPoints.segments();
        assertEquals(1, collinearPoints.numberOfSegments());
        assertEquals("(1000, 1000) -> (9000, 9000)", segments[0].toString());
    }

    @Test
    void test10PointsFromCoursera() {
        Point[] points = new Point[] {
                new Point(4000, 30000),
                new Point(3500, 28000),
                new Point(3000, 26000),
                new Point(2000, 22000),
                new Point(1000, 18000),
                new Point(13000, 21000),
                new Point(23000, 16000),
                new Point(28000, 13500),
                new Point(28000,  5000),
                new Point(28000,  1000),
        };
        FastCollinearPoints collinearPoints = new FastCollinearPoints(points);

        LineSegment[] segments = collinearPoints.segments();
        assertEquals(2, collinearPoints.numberOfSegments());
        assertEquals("(1000, 18000) -> (4000, 30000)", segments[0].toString());
        assertEquals("(28000, 13500) -> (3000, 26000)", segments[1].toString());
    }

    @Test
    void test48PointsFromCoursera() {
        Point[] points = new Point[] {
                new Point(26000, 27000),
                new Point(24000,  23000),
                new Point(18000,  23000),
                new Point(22000,   9000),
                new Point(25000,  25000),
                new Point(1000 ,  2000),
                new Point(12000,  10000),
                new Point(22000,  17000),
                new Point(25000,   1000),
                new Point(15000,   1000),
                new Point(19000,  28000),
                new Point(12000,   3000),
                new Point(4000 , 15000),
                new Point(2000 ,  7000),
                new Point(18000,  27000),
                new Point(9000 , 26000),
                new Point(11000,  26000),
                new Point(6000 , 16000),
                new Point(18000,  26000),
                new Point(24000,  30000),
                new Point(10000,  25000),
                new Point(7000 , 10000),
                new Point(19000,  24000),
                new Point(6000 ,     0),
                new Point(26000,  15000),
                new Point(1000 , 23000),
                new Point(23000,  29000),
                new Point(15000,   7000),
                new Point(15000,  19000),
                new Point(17000,  31000),
                new Point(6000 ,  2000),
                new Point(17000,  16000),
                new Point(1000 , 26000),
                new Point(11000,  19000),
                new Point(25000,      0),
                new Point(17000,  30000),
                new Point(16000,  22000),
                new Point(18000,  13000),
                new Point(3000 , 23000),
                new Point(10000,  13000),
                new Point(1000 ,  9000),
                new Point(11000,  21000),
                new Point(29000,  19000),
                new Point(9000 , 29000),
                new Point(30000,   3000),
                new Point(9000 ,  1000),
                new Point(5000 , 29000),
                new Point(26000,   6000),
        };
        System.out.println(points.length);
        FastCollinearPoints collinearPoints = new FastCollinearPoints(points);

        LineSegment[] segments = collinearPoints.segments();
        assertEquals(6, collinearPoints.numberOfSegments());
    }
}
