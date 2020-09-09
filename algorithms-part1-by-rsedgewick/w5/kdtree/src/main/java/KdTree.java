package main.java;

import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.RectHV;
import edu.princeton.cs.algs4.StdDraw;

import java.util.ArrayList;

public class KdTree {
    private int n = 0;
    private Node root;

    public boolean isEmpty() {
        return n == 0;
    }

    public int size() {
        return n;
    }

    public void draw() {
        StdDraw.clear();
        draw(root);
        StdDraw.show();
    }

    private void draw(Node node) {
        if(node == null) {
            return;
        }
        StdDraw.setPenColor(StdDraw.BLACK);
        StdDraw.setPenRadius(0.01);
        node.p.draw();

        if(node.isVertical) {
            StdDraw.setPenColor(StdDraw.RED);
        } else {
            StdDraw.setPenColor(StdDraw.BLUE);
        }
        StdDraw.setPenRadius(0.002);
        if(node.rect != null) {
            node.rect.draw();
        }

        draw(node.lb);
        draw(node.rt);
    }

    public void insert(Point2D p) {
        if(p == null) {
            throw new IllegalArgumentException();
        }

        if(root == null) {
            root = new Node(p, true);
            root.rect = new RectHV(p.x(), 0, p.x(), 1);
            n++;
            return;
        }

        insertNode(root, p, 0, 0, 1, 1);
        return;
    }

    public boolean contains(Point2D p) {
        if(p == null) {
            throw new IllegalArgumentException();
        }
        Node foundPoint = search(root, p);
        if(foundPoint != null) {
            return true;
        }
        return false;
    }

    private Node search(Node node, Point2D p) {
        if(node == null) {
            return null;
        }
        if(node.p.equals(p)) {
            return node;
        }
        int cmp;
        if(node.isVertical) {
            cmp = Point2D.X_ORDER.compare(p, node.p);
        } else {
            cmp = Point2D.Y_ORDER.compare(p, node.p);
        }
        if(cmp < 0) {
            node = search(node.lb, p);
        } else {
            node = search(node.rt, p);
        }

        return node;
    }

    private Node insertNode(Node node, Point2D p, double minX, double minY, double maxX, double maxY) {
        if(node.p.equals(p)) {
            return node;
        }
        int cmp;
        if(node.isVertical) {
            cmp = Point2D.X_ORDER.compare(p, node.p);
        } else {
            cmp = Point2D.Y_ORDER.compare(p, node.p);
        }

        if(cmp < 0 && node.lb == null) {
            node.lb = new Node(p, !node.isVertical);
            if(node.lb.isVertical) {
                node.lb.rect = new RectHV(p.x(), minY, p.x(), maxY);
            } else {
                node.lb.rect = new RectHV(minX, p.y(), node.equals(root) ? node.p.x() : maxX, p.y());
            }
            n++;
            return node;
        } else if(cmp >= 0 && node.rt == null) {
            node.rt = new Node(p, !node.isVertical);
            if(node.rt.isVertical) {
                node.rt.rect = new RectHV(node.equals(root) ? node.p.x() : p.x(), minY, p.x(), maxY);
            } else {
                node.rt.rect = new RectHV(node.equals(root) ? node.p.x() : minX, p.y(), maxX, p.y());
            }
            n++;
            return node;
        }

        if(cmp < 0) {
            if(!node.lb.isVertical) {
                double mny = node.lb.p.y() < p.y() ? node.lb.p.y() : minY;
                double mxy = node.lb.p.y() < p.y() ? maxY : node.lb.p.y();
                node = insertNode(node.lb, p, minX, mny, node.p.x(), mxy);
            } else {
                double mxm = node.lb.p.x() > p.x() ? node.lb.p.x() : maxX;
                double mxy = node.p.y() < p.y() ? maxY : node.p.y();
                node = insertNode(node.lb, p, node.lb.p.x() <= p.x() ? node.lb.p.x() : minX, minY, mxm, mxy);
            }
        } else {
            if(!node.rt.isVertical) {
                double mnx = node.p.x() < p.x() ? node.p.x() : minX;
                double mny = node.rt.p.y() < p.y() ? node.rt.p.y() : minY;
                double mxy = node.rt.p.y() < p.y() ? maxY : node.rt.p.y();
                node = insertNode(node.rt, p, mnx, mny, maxX, mxy);
            } else {
                double mxm = node.rt.p.x() > p.x() ? node.rt.p.x() : maxX;
                node = insertNode(node.rt, p, node.rt.p.x() <= p.x() ? node.rt.p.x() : minX, minY, mxm, maxY);
            }
        }

        return node;
    }

    // all points that are inside the rectangle (or on the boundary)
    public Iterable<Point2D> range(RectHV rect) {
        if (rect == null) throw new IllegalArgumentException();
        ArrayList<Point2D> pointsInRange = new ArrayList<>();
        if (size() == 0) {
            return pointsInRange;
        }
        findInRect(root, pointsInRange, rect);
        return pointsInRange;
    }

    private void findInRect(Node node, ArrayList<Point2D> pointsInRange,
                            RectHV rect) {
        if (node == null) {
            return;
        }
        if (rect.contains(node.p)) {
            pointsInRange.add(node.p);
        }
        boolean goLeft = false;
        boolean goRight = false;
        if (rect.intersects(node.rect)) {
            goLeft = true;
            goRight = true;
        } else if (node.isVertical) {
            if (rect.xmax() < node.p.x()) {
                goLeft = true;
            }
            else {
                goRight = true;
            }
        }
        else {
            if (rect.ymax() < node.p.y()) {
                goLeft = true;
            }
            else {
                goRight = true;
            }
        }
        if (goLeft) {
            findInRect(node.lb, pointsInRange, rect);
        }
        if (goRight) {
            findInRect(node.rt, pointsInRange, rect);
        }
    }

    // a nearest neighbor in the set to point p; null if the set is empty
    public Point2D nearest(Point2D point) {
        if (point == null) {
            throw new IllegalArgumentException();
        }
        if (size() == 0) {
            return null;
        }

        RectHV boundaryRect = new RectHV(0.0, 0.0, 1.0, 1.0);
        return findNearest(root, boundaryRect, Double.POSITIVE_INFINITY, point, null);
    }

    private Point2D findNearest(Node node, RectHV boundaryRect, double nearestDistance, Point2D p, Point2D nearestPoint) {
        if (node == null)  {
            return nearestPoint;
        }
        double distanceToP = node.p.distanceSquaredTo(p);
        if (distanceToP < nearestDistance) {
            nearestPoint = node.p;
            nearestDistance = distanceToP;
        }
        RectHV lbRect;
        RectHV rtRect;
        if (node.isVertical) {
            lbRect = new RectHV(boundaryRect.xmin(), boundaryRect.ymin(), node.p.x(), boundaryRect.ymax());
            rtRect = new RectHV(node.p.x(), boundaryRect.ymin(), boundaryRect.xmax(), boundaryRect.ymax());
        } else {
            lbRect = new RectHV(boundaryRect.xmin(), boundaryRect.ymin(), boundaryRect.xmax(), node.p.y());
            rtRect = new RectHV(boundaryRect.xmin(), node.p.y(), boundaryRect.xmax(), boundaryRect.ymax());
        }

        Point2D lbNearest = null;
        Point2D rtNearest = null;
        if (p.distanceSquaredTo(closestPoint(lbRect, p)) < nearestDistance) {
            lbNearest = findNearest(node.lb, lbRect, nearestDistance, p, nearestPoint);
        }
        if (p.distanceSquaredTo(closestPoint(rtRect, p)) < nearestDistance) {
            rtNearest = findNearest(node.rt, rtRect, nearestDistance, p, nearestPoint);
        }

        if(lbNearest != null && rtNearest != null) {
            double lbNearestDist = lbNearest.distanceSquaredTo(p);
            double rtNearestDist = rtNearest.distanceSquaredTo(p);
            return lbNearestDist < rtNearestDist ? lbNearest : rtNearest;
        }

        return lbNearest == null ? rtNearest : lbNearest;
    }

    private Point2D closestPoint(RectHV rect, Point2D p) {
        if (rect == null || p == null || rect.contains(p)) {
            return p;
        }
        return new Point2D(closestCoord(rect.xmin(), rect.xmax(), p.x()), closestCoord(rect.ymin(), rect.ymax(), p.y()));
    }

    private double closestCoord(double minRect, double maxRect, double pointCoord) {
        // point between min and max - closest is point
        if (minRect <= pointCoord && maxRect >= pointCoord) {
            return pointCoord;
        }
        // point is more that rect, return max of rect
        if (minRect <= pointCoord) {
            return Math.max(minRect, maxRect);
        }
        // point is less than rect, return min of rect
        return Math.min(minRect, maxRect);
    }

    private static class Node {
        private Point2D p;      // the point
        private Node lb;        // the left/bottom subtree
        private Node rt;        // the right/top subtree
        private boolean isVertical; // true when x, false when y
        private RectHV rect;    // the axis-aligned rectangle corresponding to this node

        public Node(Point2D point2D, boolean orientation) {
            p = point2D;
            isVertical = orientation;
        }
    }

    public static void main(String[] args) {
        KdTree kdTree = new KdTree();

//        kdTree.insert(new Point2D(0.7, 0.2));
//        kdTree.insert(new Point2D(0.5, 0.4));
//        kdTree.insert(new Point2D(0.2, 0.3));
//        kdTree.insert(new Point2D(0.4, 0.7));
//        kdTree.insert(new Point2D(0.9, 0.6));
//        kdTree.insert(new Point2D(0.6, 0.1));
////        kdTree.insert(new Point2D(0.95, 0.4));
//        kdTree.insert(new Point2D(0.1, 0.1));
//        kdTree.insert(new Point2D(0.8, 0.2));

//        kdTree.insert(new Point2D(0.206107, 0.095492));
//        kdTree.insert(new Point2D(0.975528, 0.654508));
//        kdTree.insert(new Point2D(0.024472, 0.345492));
//        kdTree.insert(new Point2D(0.793893, 0.095492));
//        kdTree.insert(new Point2D(0.793893, 0.904508));
//        kdTree.insert(new Point2D(0.975528, 0.345492));
//        kdTree.insert(new Point2D(0.206107, 0.904508));
//        kdTree.insert(new Point2D(0.500000, 0.000000));
//        kdTree.insert(new Point2D(0.024472, 0.654508));
//        kdTree.insert(new Point2D(0.500000, 1.000000));

//        kdTree.insert(new Point2D(0.7, 0.2));
//        kdTree.insert(new Point2D(0.5, 0.4));
//        kdTree.insert(new Point2D(0.8, 0.6));
//        kdTree.insert(new Point2D(0.9, 0.6));

//        kdTree.insert(new Point2D(0.7, 0.2));
//        kdTree.insert(new Point2D(0.5, 0.4));
//        kdTree.insert(new Point2D(0.5, 0.3));
//        kdTree.insert(new Point2D(0.5, 0.5));
//        kdTree.insert(new Point2D(0.2, 0.3));
//        kdTree.insert(new Point2D(0.9, 0.6));

//        kdTree.insert(new Point2D(0.7, 0.2));
//        kdTree.insert(new Point2D(0.7, 0.3));
//        kdTree.insert(new Point2D(0.8, 0.6));
//        kdTree.insert(new Point2D(0.7, 0.1));
//        kdTree.insert(new Point2D(0.6, 0.1));

//        kdTree.insert(new Point2D(0.7, 0.2));
//        kdTree.insert(new Point2D(0.7, 0.3));
//        kdTree.insert(new Point2D(0.6, 0.2));
//        kdTree.insert(new Point2D(0.8, 0.2));
//        kdTree.insert(new Point2D(0.9, 0.5));


//        kdTree.insert(new Point2D(0.9, 0.5));
//        kdTree.insert(new Point2D(0.2, 0.5));
//        kdTree.insert(new Point2D(0.3, 0.5));
//        kdTree.insert(new Point2D(0.4, 0.5));
//        kdTree.insert(new Point2D(0.1, 0.5));
//        kdTree.insert(new Point2D(0.6, 0.5));
//        kdTree.insert(new Point2D(0.5, 0.5));
//        kdTree.insert(new Point2D(0.7, 0.5));

//        kdTree.insert(new Point2D(0.7, 0.2));
//        kdTree.insert(new Point2D(0.7, 0.3));
//        kdTree.insert(new Point2D(0.8, 0.2));
//        kdTree.insert(new Point2D(0.7, 0.1));
//        kdTree.insert(new Point2D(0.5, 0.5));
//        kdTree.insert(new Point2D(0.5, 0.6));
//        kdTree.insert(new Point2D(0.5, 0.7));
//        kdTree.insert(new Point2D(0.5, 0.8));

//        kdTree.insert(new Point2D(0.9, 0.1));
//        kdTree.insert(new Point2D(0.1, 0.1));
//        kdTree.insert(new Point2D(0.2, 0.1));
//        kdTree.insert(new Point2D(0.5, 0.5));

//        kdTree.insert(new Point2D(0.1, 0.1));
//        kdTree.insert(new Point2D(0.2, 0.2));
//        kdTree.insert(new Point2D(0.3, 0.3));
//        kdTree.insert(new Point2D(0.4, 0.4));
//        kdTree.insert(new Point2D(0.5, 0.5));
//        kdTree.insert(new Point2D(0.45, 0.3));
//        kdTree.insert(new Point2D(0.5, 0.3));
//        kdTree.insert(new Point2D(0.33, 0.35));
//        kdTree.insert(new Point2D(0.6, 0.7));
//        kdTree.insert(new Point2D(0.35, 0.6));
//        kdTree.insert(new Point2D(0.05, 0.05));
//        kdTree.insert(new Point2D(0.36, 0.55));

//        kdTree.insert(new Point2D(0.4, 0.6));
//        kdTree.insert(new Point2D(0.8, 0.25));
//        kdTree.insert(new Point2D(0.9, 0.4));
//        kdTree.insert(new Point2D(0.6, 0.35));

//        kdTree.insert(new Point2D(0,0));
//        kdTree.insert(new Point2D(1.0,1.0));
//        kdTree.insert(new Point2D(0.5,0.5));
//        kdTree.insert(new Point2D(0.75,0.75));
//        kdTree.insert(new Point2D(0.40,0));
//        kdTree.insert(new Point2D(0.2,0.2));
//        kdTree.insert(new Point2D(0.25,0.2));

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
        kdTree.insert(new Point2D(0.11, 0.11));

        kdTree.draw();
    }
}
