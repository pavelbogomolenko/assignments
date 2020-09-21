package main.java;

public class FindMaxSubArray {

    private static double[] findMaxCrossingSubArray(double[] a, int lo, int mid, int hi) {
        double left_index = 0;
        double sum = 0;
        double sum_left = Double.NEGATIVE_INFINITY;
        for(int k = mid; k >= lo; k--) {
            sum += a[k];
            if(sum > sum_left) {
                sum_left = sum;
                left_index = k;
            }
        }

        sum = 0;
        double right_index = 0;
        double sum_right = Double.NEGATIVE_INFINITY;
        for(int k = mid + 1; k <= hi; k++) {
            sum += a[k];
            if(sum > sum_right) {
                sum_right = sum;
                right_index = k;
            }
        }

        return new double[] {left_index, right_index, sum_left + sum_right};
    }

    private static double[] find(double[] a, int lo, int hi) {
        if(lo == hi) {
            return new double[] {lo, hi, a[lo]};
        }

        int mid = lo + (hi - lo) / 2;
        double left[] = find(a, lo, mid);
        double right[] = find(a, mid + 1, hi);
        double[] crossing = findMaxCrossingSubArray(a, lo, mid, hi);
        if(left[2] >= right[2] && left[2] >= crossing[2]) {
            return left;
        } else if(right[2] >= left[2] && right[2] >= crossing[2]) {
            return right;
        } else {
            return crossing;
        }
    }

    public static double[] find(double[] a) {
        return find(a, 0, a.length - 1);
    }
}
