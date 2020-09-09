package main.java;

import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.RectHV;

import java.util.ArrayList;
import java.util.TreeSet;

public class PointSET {

    private TreeSet<Point2D> points;

    public PointSET() {
        points = new TreeSet<>();
    }

    public boolean isEmpty() {
        return points.isEmpty();
    }

    public int size() {
        return points.size();
    }

    public void insert(Point2D p) {
        if(p == null) {
            throw new IllegalArgumentException();
        }
        points.add(p);
    }

    public boolean contains(Point2D p) {
        if(p == null) {
            throw new IllegalArgumentException();
        }
        return points.contains(p);
    }

    public void draw() {
        for (Point2D p: points) {
            p.draw();
        }
    }

    public Iterable<Point2D> range(RectHV rect) {
        if(rect == null) {
            throw new IllegalArgumentException();
        }
        ArrayList<Point2D> pointInRange = new ArrayList<>();

        for (Point2D p: points) {
            if(rect.contains(p)) {
                pointInRange.add(p);
            }
        }

        return pointInRange;
    }

    public Point2D nearest(Point2D pTo) {
        if(pTo == null) {
            throw new IllegalArgumentException();
        }
        if(isEmpty()) {
            return null;
        }
        Point2D nearestPoint = null;
        double nearestDistance = Double.POSITIVE_INFINITY;
        for(Point2D p: points) {
            double distanceToP = pTo.distanceTo(p);
            if(distanceToP < nearestDistance) {
                nearestDistance = distanceToP;
                nearestPoint = p;
            }
        }

        return nearestPoint;
    }
}
