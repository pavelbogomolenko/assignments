package main.java;

public class CollinearPointsInputValidator {
    public static void validateInput(Point[] points) {
        if(points == null) {
            throw new IllegalArgumentException();
        }

        if(points.length < 4) {
            throw new IllegalArgumentException("there should be at least 4 points");
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
    }
}
