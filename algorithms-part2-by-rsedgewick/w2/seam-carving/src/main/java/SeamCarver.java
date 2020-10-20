package main.java;

import edu.princeton.cs.algs4.Picture;
import edu.princeton.cs.algs4.Queue;
import java.awt.Color;

public class SeamCarver {
    private final Picture origPicture;
    private double[][] energyArray;
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
        if (x < 0 || x >= width() || y < 0 || y >= height()) {
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
        energyArray = new double[n][m];
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < m; j++) {
                energyArray[i][j] = needTranspose ? energy(j, i) : energy(i, j);
            }
        }

        Queue<EnergyPoint>[] energyPointQueues = (Queue<EnergyPoint>[]) new Queue[n];
        for(int i = 0; i < n; i++) {
            energyPointQueues[i] = new Queue<>();
        }

        for (int i = 0; i < n; i++) {
            Queue<EnergyPoint> queue = new Queue<>();
            EnergyPoint energyPoint = new EnergyPoint(i, 0, BORDER_ENERGY);
            queue.enqueue(energyPoint);
            while (!queue.isEmpty()) {
                energyPoint = queue.dequeue();
                energyPointQueues[i].enqueue(energyPoint);
                enqueueMinDownwardNeighbor(energyPoint, queue);
            }
        }

        double minEnergy = Double.MAX_VALUE;
        int[] minVerticalSeam = new int[m];
        for(Queue<EnergyPoint> eps: energyPointQueues) {
            double epsEnergy = 0.0;
            int[] verticalSeam = new int[m];
            int i = 0;
            for(EnergyPoint eP: eps) {
                epsEnergy += eP.getWeight();
                verticalSeam[i++] = eP.getX();
            }
            if(epsEnergy < minEnergy) {
                minEnergy = epsEnergy;
                minVerticalSeam = verticalSeam;
            }
        }

        return minVerticalSeam;
    }

    private void enqueueMinDownwardNeighbor(EnergyPoint point, Queue<EnergyPoint> queue) {
        int xNeighborsOffset[] = {-1, 0, 1};
        double minEnerge = BORDER_ENERGY + 1;
        int xMin = -1;
        int yMin = -1;
        int n = width();
        int m = height();
        if(needTranspose) {
            n = height();
            m = width();
        }
        for(int xCoord: xNeighborsOffset) {
            int x = point.getX() + xCoord;
            int y = point.getY() + 1;
            if((x >= 0 && x < n) && (y >= 0 && y < m)) {
                double currentEnergy = energyArray[x][y];
                if(currentEnergy < minEnerge) {
                    minEnerge = currentEnergy;
                    xMin = x;
                    yMin = y;
                }
            }
        }
        if(xMin != -1) {
            queue.enqueue(new EnergyPoint(xMin, yMin, energyArray[xMin][yMin]));
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
        for (int i = 0; i < seam.length - 1; ++i) {
            if (Math.abs(seam[i] - seam[i + 1]) > 1) {
                throw new IllegalArgumentException();
            }
        }
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

    private class EnergyPoint implements Comparable<EnergyPoint> {
        private final int x;
        private final int y;
        private final double weight;

        public EnergyPoint(int x, int y, double weight) {
            this.x = x;
            this.y = y;
            this.weight = weight;
        }

        @Override
        public int compareTo(EnergyPoint that) {
            if(this.weight < that.weight) {
                return -1;
            } else if(that.weight > that.weight) {
                return 1;
            } else {
                return 0;
            }
        }

        public int getX() {
            return x;
        }

        public int getY() {
            return y;
        }

        public double getWeight() {
            return weight;
        }

        @Override
        public String toString() {
            return "EnergyPoint{" +
                    "x=" + x +
                    ", y=" + y +
                    ", weight=" + weight +
                    '}';
        }
    }
}
