/* *****************************************************************************
 *  Name:              Alan Turing
 *  Coursera User ID:  123456
 *  Last modified:     1/1/2019
 **************************************************************************** */


import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;

public class PercolationStats {

    private static final Double PERCENTILE = 1.96;
    final private double[] results;
    final private int trials;

    // perform independent trials on an n-by-n grid
    public PercolationStats(int n, int t) {
        if (n <= 0) {
            throw new IllegalArgumentException("`n` should be greated than 0");
        }

        if (t <= 0) {
            throw new IllegalArgumentException("`t` should be greated than 0");
        }

        trials = t;
        results = new double[trials];
        int gridSize = n * n;
        for (t = 0; t < trials; t++) {
            Percolation percolation = new Percolation(n);

            int sitesOpenedNumber = 0;
            while (!percolation.percolates()) {
                int randomRow = StdRandom.uniform(1, n + 1);
                int randomCol = StdRandom.uniform(1, n + 1);
                if (!percolation.isOpen(randomRow, randomCol)) {
                    percolation.open(randomRow, randomCol);
                    sitesOpenedNumber++;
                }
            }
            results[t] = (double) sitesOpenedNumber / (double) gridSize;
        }
    }

    // sample mean of percolation threshold
    public double mean() {
        return StdStats.mean(results);
    }

    // sample standard deviation of percolation threshold
    public double stddev() {
        return StdStats.stddev(results);
    }

    // low endpoint of 95% confidence interval
    public double confidenceLo() {
        return (mean() - PERCENTILE * stddev()) / Math.sqrt(trials);
    }

    // high endpoint of 95% confidence interval
    public double confidenceHi() {
        return (mean() + PERCENTILE * stddev()) / Math.sqrt(trials);
    }

    // test client (see below)
    public static void main(String[] args) {
        int n = 4;
        int trials = 10;

        PercolationStats percolationStats = new PercolationStats(n, trials);

        System.out.println("mean = " + percolationStats.mean());
        System.out.println("stddev = " + percolationStats.stddev());
        System.out.println("95% confidence interval = [" + percolationStats.confidenceLo() + ", " +  percolationStats.confidenceHi() + "]");
    }
}
