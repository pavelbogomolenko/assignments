package main.java;

import java.util.Arrays;

public class FastCollinearPoints {
    private Point[] p;
    private LineSegment[] lineSegments;

    public FastCollinearPoints(Point[] points) {
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
        Point[] pSortedPoints = new Point[n - 1];
        Point pPoint = null;
        int sortedPointsCounter = 0;
        LineSegment[] tempLineSegment = new LineSegment[n];
        int lineSegmentsCount = 0;
        String prevLineSegment = "";
        for(int i = 0; i < n; i++) {
            for(int j = i + 1; j < n; j++) {
                if(p[i] != pPoint) {
                    if(sortedPointsCounter == n - i) {
                        Arrays.sort(pSortedPoints, pPoint.slopeOrder());
                        double prevSlope = 999;
                        Point[] tmpCollinearPoints = new Point[n];
                        int tmpCollinearPointsCount = 0;
                        for(int sp = 0; sp < n - i; sp++) {
                            Point currentSortedPoint = pSortedPoints[sp];
                            double pSlope = pPoint.slopeTo(currentSortedPoint);
                            if(pSlope != prevSlope) {
                                if(tmpCollinearPointsCount >= 3) {
                                    Point[] tmpCollinearPoints2 = new Point[tmpCollinearPointsCount + 1];
                                    int tmpCollinearPoints2Counter = 0;
                                    for(int tmpi = 0; tmpi < tmpCollinearPointsCount; tmpi++) {
                                        if(tmpCollinearPoints[tmpi] != null) {
                                            tmpCollinearPoints2[tmpCollinearPoints2Counter++] = tmpCollinearPoints[tmpi];
                                        }
                                    }
                                    tmpCollinearPoints2[tmpCollinearPoints2Counter++] = pPoint;
                                    Arrays.sort(tmpCollinearPoints2);
                                    LineSegment lineSegment = new LineSegment(tmpCollinearPoints2[0], tmpCollinearPoints2[tmpCollinearPoints2Counter - 1]);
                                    if(!prevLineSegment.equals(lineSegment.toString())) {
                                        tempLineSegment[lineSegmentsCount++] = lineSegment;
                                        prevLineSegment = lineSegment.toString();
                                    }
                                    break;
                                }
                                prevSlope = pSlope;
                                tmpCollinearPoints = new Point[n];
                                tmpCollinearPointsCount = 0;
                                tmpCollinearPoints[tmpCollinearPointsCount++] = currentSortedPoint;
                            } else {
                                tmpCollinearPoints[tmpCollinearPointsCount++] = currentSortedPoint;

                                if(sp == n - i - 1) {
                                    if(tmpCollinearPointsCount >= 3) {
                                        Point[] tmpCollinearPoints2 = new Point[tmpCollinearPointsCount + 1];
                                        int tmpCollinearPoints2Counter = 0;
                                        for(int tmpi = 0; tmpi < tmpCollinearPointsCount; tmpi++) {
                                            if(tmpCollinearPoints[tmpi] != null) {
                                                tmpCollinearPoints2[tmpCollinearPoints2Counter++] = tmpCollinearPoints[tmpi];
                                            }
                                        }
                                        tmpCollinearPoints2[tmpCollinearPoints2Counter++] = pPoint;
                                        Arrays.sort(tmpCollinearPoints2);
                                        LineSegment lineSegment = new LineSegment(tmpCollinearPoints2[0], tmpCollinearPoints2[tmpCollinearPoints2Counter - 1]);
                                        if(!prevLineSegment.equals(lineSegment.toString())) {
                                            tempLineSegment[lineSegmentsCount++] = lineSegment;
                                            prevLineSegment = lineSegment.toString();
                                        }
                                        break;
                                    }
                                }
                            }
                        }
                    }
                    sortedPointsCounter = 0;
                    pSortedPoints = new Point[n - i - 1];
                    pSortedPoints[sortedPointsCounter++] = p[j];
                    pPoint = p[i];
                } else {
                    pSortedPoints[sortedPointsCounter++] = p[j];
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
