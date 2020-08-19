package main.java;

import java.util.ArrayList;
import java.util.Arrays;

public class FastCollinearPoints {
    private Point[] p;
    private LineSegment[] lineSegments;
    private ArrayList<LineSegmentCandidate> lineSegmentCandidates;

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
        lineSegmentCandidates = new ArrayList<>();
        int sortedPointsCounter = 0;
        for(int i = 0; i < n; i++) {
            for(int j = i + 1; j < n; j++) {
                if(p[i] == pPoint) {
                    pSortedPoints[sortedPointsCounter++] = p[j];
                    continue;
                }
                if(sortedPointsCounter == n - i) {
                    Arrays.sort(pSortedPoints, pPoint.slopeOrder());
                    double prevSlope = 999;
                    Point[] tmpCollinearPoints = new Point[n];
                    int tmpCollinearPointsCount = 0;
                    tmpCollinearPoints[tmpCollinearPointsCount++] = pPoint;
                    for(int sp = 0; sp < n - i; sp++) {
                        Point currentSortedPoint = pSortedPoints[sp];
                        double pSlope = pPoint.slopeTo(currentSortedPoint);
                        if(pSlope != prevSlope) {
                            if(tmpCollinearPointsCount >= 4) {
                                addLineSegmentCandidate(tmpCollinearPoints, tmpCollinearPointsCount);
                            }
                            prevSlope = pSlope;
                            tmpCollinearPoints = new Point[n];
                            tmpCollinearPointsCount = 0;
                            tmpCollinearPoints[tmpCollinearPointsCount++] = pPoint;
                            tmpCollinearPoints[tmpCollinearPointsCount++] = currentSortedPoint;
                        } else {
                            tmpCollinearPoints[tmpCollinearPointsCount++] = currentSortedPoint;

                            if(sp == n - i - 1) {
                                if(tmpCollinearPointsCount >= 4) {
                                    addLineSegmentCandidate(tmpCollinearPoints, tmpCollinearPointsCount);
                                }
                            }
                        }
                    }
                }
                sortedPointsCounter = 0;
                pSortedPoints = new Point[n - i - 1];
                pSortedPoints[sortedPointsCounter++] = p[j];
                pPoint = p[i];
            }
        }
        lineSegments = new LineSegment[lineSegmentCandidates.size()];
        for (int i = 0; i < lineSegmentCandidates.size(); i++) {
            LineSegment lineSegment = new LineSegment(lineSegmentCandidates.get(i).getP(), lineSegmentCandidates.get(i).getQ());
            lineSegments[i] = lineSegment;
        }
        return lineSegments;
    }

    private void addLineSegmentCandidate(Point[] collinearPoints, int collinearPointsCount) {
        Point[] collinearPointsCandidates = new Point[collinearPointsCount];
        int collinearPointsCandidatesCounter = 0;
        for(int i = 0; i < collinearPointsCount; i++) {
            if(collinearPoints[i] != null) {
                collinearPointsCandidates[collinearPointsCandidatesCounter++] = collinearPoints[i];
            }
        }
        Arrays.sort(collinearPointsCandidates);
        LineSegmentCandidate lineSegmentCandidate = new LineSegmentCandidate(collinearPointsCandidates[0], collinearPointsCandidates[collinearPointsCandidatesCounter - 1]);
        if(!lineSegmentCandidateExists(lineSegmentCandidate)) {
            lineSegmentCandidates.add(lineSegmentCandidate);
        }
    }

    private boolean lineSegmentCandidateExists(LineSegmentCandidate lineSegmentCandidate) {
        for(LineSegmentCandidate lsCandidate: lineSegmentCandidates) {
            if(lsCandidate == null) {
                continue;
            }

            if(lsCandidate.getQ().compareTo(lineSegmentCandidate.getQ()) == 0) {
                if(lsCandidate.getQ().slopeTo(lineSegmentCandidate.getP()) == lsCandidate.getQ().slopeTo(lsCandidate.getP())) {
                    return true;
                }
            }

            if(lsCandidate.getP().compareTo(lineSegmentCandidate.getP()) == 0) {
                if(lsCandidate.getQ().compareTo(lineSegmentCandidate.getQ()) == 0) {
                    return true;
                }

                if(lsCandidate.getP().slopeTo(lineSegmentCandidate.getQ()) == lsCandidate.getP().slopeTo(lsCandidate.getQ())) {
                    return true;
                }
            }

            if(lsCandidate.getP().slopeTo(lineSegmentCandidate.getP()) == lsCandidate.getP().slopeTo(lineSegmentCandidate.getQ()) &&
                    lsCandidate.getQ().slopeTo(lineSegmentCandidate.getQ()) == lsCandidate.getQ().slopeTo(lineSegmentCandidate.getP())) {
                return true;
            }
        }
        return false;
    }

    public int numberOfSegments() {
        return lineSegments.length;
    }


    private class LineSegmentCandidate {
        private final Point p;
        private final Point q;

        public LineSegmentCandidate(Point p, Point q) {
            if (p == null || q == null) {
                throw new NullPointerException("argument is null");
            }
            this.p = p;
            this.q = q;
        }

        public Point getP() {
            return p;
        }

        public Point getQ() {
            return q;
        }
    }
}
