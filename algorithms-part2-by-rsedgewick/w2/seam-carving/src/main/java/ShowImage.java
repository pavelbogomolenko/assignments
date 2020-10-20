package main.java;

import edu.princeton.cs.algs4.Picture;
import edu.princeton.cs.algs4.StdOut;

public class ShowImage {

    public ShowImage() {

    }

    public static void main(String[] args) {
        Picture picture = new Picture(args[0]);
        StdOut.printf("%s (%d-by-%d image)\n", args[0], picture.width(), picture.height());

        SeamCarver carver = new SeamCarver(picture);

//        carver.removeVerticalSeam(carver.findVerticalSeam());
        carver.removeHorizontalSeam(carver.findHorizontalSeam());
        Picture outputImg = carver.picture();
        outputImg.show();
    }
}
