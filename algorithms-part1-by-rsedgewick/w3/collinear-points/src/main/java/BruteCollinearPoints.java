package main.java;

import java.util.Arrays;

public class BruteCollinearPoints {
    private Point[] p;
    private LineSegment[] lineSegments;

    // finds all line segments containing 4 points
    // For simplicity, we will not supply any input to BruteCollinearPoints that has 5 or more collinear points.
    public BruteCollinearPoints(Point[] points) {
        if(points == null) {
            throw new IllegalArgumentException();
        }

        for (Point point : points) {
            if(point == null) {
                throw new IllegalArgumentException("points should not contain null values");
            }
        }

        for(int i = 0; i < points.length; i++) {
            for (int j = i + 1;  j < points.length; j++) {
                if(points[i].compareTo(points[j]) == 0) {
                    throw new IllegalArgumentException("points should not contain duplicates");
                }
            }
        }
        p = points;
    }

    public LineSegment[] segments() {
        int n = p.length;
        LineSegment[] tempLineSegment = new LineSegment[n];
        int lineSegmentsCount = 0;
        for(int i = 0; i < n; i++) {
            for (int j = i + 1; j < n; j++) {
                for(int k = j + 1; k < n; k++) {
                    for (int m = k + 1; m < n; m++) {
                        if(p[i].slopeTo(p[j]) == p[i].slopeTo(p[k]) && p[i].slopeTo(p[j]) == p[i].slopeTo(p[m])) {
                            Point[] tmpCollinearPoints = new Point[] {p[i], p[j], p[k], p[m]};
                            Arrays.sort(tmpCollinearPoints);
                            tempLineSegment[lineSegmentsCount++] = new LineSegment(tmpCollinearPoints[0], tmpCollinearPoints[3]);
                        }
                    }
                }
            }
        }

        lineSegments = new LineSegment[lineSegmentsCount];
        for(int i = 0; i < lineSegmentsCount; i++) {
            lineSegments[i] = tempLineSegment[i];
        }
        return lineSegments;
    }

    public int numberOfSegments() {
        return lineSegments.length;
    }
}
