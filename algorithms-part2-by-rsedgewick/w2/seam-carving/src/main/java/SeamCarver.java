package main.java;

import edu.princeton.cs.algs4.IndexMinPQ;
import edu.princeton.cs.algs4.Picture;
import edu.princeton.cs.algs4.Stack;

import java.awt.Color;

public class SeamCarver {
    private final Picture origPicture;
    private double[] energyArray;
    private Color[][] pictureColorArray;
    private static final double BORDER_ENERGY = 1000.0;
    private boolean needTranspose = false;

    public SeamCarver(Picture picture) {
        if(picture == null) {
            throw new IllegalArgumentException("picture should be null");
        }
        origPicture = picture;
        pictureColorArray = new Color[origPicture.width()][origPicture.height()];
        for (int i = 0; i < origPicture.width(); i++) {
            for (int j = 0; j < origPicture.height(); j++) {
                pictureColorArray[i][j] = origPicture.get(i, j);
            }
        }
    }

    // current picture
    public Picture picture() {
        Picture picture = new Picture(width(), height());

        for (int col = 0; col < width(); col++) {
            for (int row = 0; row < height(); row++) {
                picture.set(col, row, pictureColorArray[col][row]);
            }
        }

        return picture;
    }

    // width of current picture
    public int width() {
        return pictureColorArray.length;
    }

    // height of current picture
    public int height() {
        return pictureColorArray[0].length;
    }

    // energy of pixel at column x and row y
    public double energy(int x, int y) {
        if(x < 0 || x >= width() || y < 0 || y >= height()) {
            throw new IllegalArgumentException();
        }
        if(x == 0 || y == 0 || x == width() - 1 || y == height() - 1) {
            return BORDER_ENERGY;
        }

        return Math.sqrt(xRGBDiff(x, y) + yRGBDiff(x, y));
    }

    private double xRGBDiff(int x, int y) {
        Color rightCoordColor = pictureColorArray[x + 1][y];
        Color leftCoordColor = pictureColorArray[x - 1][y];
        return Math.pow(rightCoordColor.getRed() - leftCoordColor.getRed(), 2)
                + Math.pow(rightCoordColor.getGreen() - leftCoordColor.getGreen(), 2)
                + Math.pow(rightCoordColor.getBlue() - leftCoordColor.getBlue(), 2);
    }

    private double yRGBDiff(int x, int y) {
        Color bottomCoordColor = pictureColorArray[x][y + 1];
        Color topCoordColor = pictureColorArray[x][y - 1];
        return Math.pow(bottomCoordColor.getRed() - topCoordColor.getRed(), 2)
                + Math.pow(bottomCoordColor.getGreen() - topCoordColor.getGreen(), 2)
                + Math.pow(bottomCoordColor.getBlue() - topCoordColor.getBlue(), 2);
    }

    // sequence of indices for vertical seam
    public int[] findVerticalSeam() {
        int n = width();
        int m = height();
        if(needTranspose) {
            n = height();
            m = width();
        }
        energyArray = new double[n * m];
        for (int i = 0; i < n  * m; i++) {
            int x = getXCoord(i, n);
            int y = getYCoord(i, n);
            energyArray[i] = needTranspose ? energy(y, x) : energy(x, y);
        }

        int[] pathTo = new int[n * m];
        double[] distTo = new double[n * m];
        IndexMinPQ<Double> pq = new IndexMinPQ<>(n * m);

        for(int i = 0; i < n * m; i++) {
            distTo[i] = Double.POSITIVE_INFINITY;
        }

        for (int i = 0; i < n; i++) {
            distTo[i] = BORDER_ENERGY;
            pq.insert(i, BORDER_ENERGY);
            while (!pq.isEmpty()) {
                int point = pq.delMin();
                enqueueMinDownwardNeighbor(point, pathTo, distTo, pq);
            }
        }

        double minEnergy = Double.MAX_VALUE;
        int minIndex = 0;
        for(int i = (n * m) - n; i < n * m; i++) {
            if(distTo[i] < minEnergy) {
                minEnergy = distTo[i];
                minIndex = i;
            }
        }

        Stack<Integer> path = new Stack<>();
        path.push(getXCoord(minIndex, n));
        for(int p = pathTo[minIndex]; p != 0; p = pathTo[p]) {
            path.push(getXCoord(p, n));
        }

        int[] minVerticalSeam = new int[m];
        int iter = 0;
        for (int p: path) {
            minVerticalSeam[iter++] = p;
        }
        return minVerticalSeam;
    }

    private void enqueueMinDownwardNeighbor(int point, int[] pathTo, double[] distTo, IndexMinPQ<Double> pq) {
        int xNeighborsOffset[] = {-1, 0, 1};
        int n = needTranspose ? height() : width();
        for(int xCoord: xNeighborsOffset) {
            int nextPoint = point + n + xCoord;
            if (point % n == 0 && xCoord < 0
                    || point % n == n - 1 && xCoord > 0 || nextPoint >= height() * width()) {
                continue;
            }
            double currentEnergy = distTo[point] + energyArray[nextPoint];
            if(distTo[nextPoint] > currentEnergy) {
                distTo[nextPoint] = currentEnergy;
                pathTo[nextPoint] = point;
                if(pq.contains(nextPoint)) {
                    pq.decreaseKey(nextPoint, currentEnergy);
                } else {
                    pq.insert(nextPoint, currentEnergy);
                }
            }
        }
    }

    // sequence of indices for horizontal seam
    public int[] findHorizontalSeam() {
        needTranspose = true;
        int[] result = findVerticalSeam();
        needTranspose = false;
        return result;
    }

    // remove vertical seam from current picture
    public void removeVerticalSeam(int[] seam) {
        validateSeam(seam, false);
        Color[][] newPictureColorArray = new Color[width() - 1][height()];
        for(int i = 0; i < height(); i++) {
            int newWidthIter = 0;
            for (int j = 0; j < width(); j++) {
                if(seam[i] == j) {
                    continue;
                }
                newPictureColorArray[newWidthIter++][i] = pictureColorArray[j][i];
            }
        }

        pictureColorArray = new Color[width() - 1][height()];
        pictureColorArray = newPictureColorArray;
    }

    // remove horizontal seam from current picture
    public void removeHorizontalSeam(int[] seam) {
        validateSeam(seam, true);
        Color[][] newPictureColorArray = new Color[width()][height() - 1];
        for(int i = 0; i < width(); i++) {
            int newWidthIter = 0;
            for (int j = 0; j < height(); j++) {
                if(seam[i] == j) {
                    continue;
                }
                newPictureColorArray[i][newWidthIter++] = pictureColorArray[i][j];
            }
        }

        pictureColorArray = new Color[width() - 1][height()];
        pictureColorArray = newPictureColorArray;
    }

    private void validateSeam(int[] seam, boolean isHorizontal) {
        int expectedLength = !isHorizontal ? height() : width();
        if (seam == null || seam.length != expectedLength) {
            throw new IllegalArgumentException();
        }
//        for (int i = 0; i < seam.length - 1; ++i) {
//            if (Math.abs(seam[i] - seam[i + 1]) > 1) {
//                throw new IllegalArgumentException();
//            }
//        }
    }

    private int getXCoord(int index, int n) {
        return index % n;
    }

    private int getYCoord(int index, int n) {
        return index / n;
    }

    private int toFlatCoordIndex(int x, int y) {
        int n = needTranspose ? height() : width();
        return (y * n) + x;
    }

    public static void main(String[] args) {
//        Picture picture = new Picture("3x4.png");
//        Picture picture = new Picture("7x3.png");
//        Picture picture = new Picture("12x10.png");
//        Picture picture = new Picture("10x12.png");
//        Picture picture = new Picture("5x6.png");
        Picture picture = new Picture("6x5.png");
        SeamCarver sc = new SeamCarver(picture);

        sc.findVerticalSeam();
        sc.findHorizontalSeam();
    }
}
