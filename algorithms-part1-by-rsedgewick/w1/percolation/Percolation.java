/* *****************************************************************************
 *  Name:              Alan Turing
 *  Coursera User ID:  123456
 *  Last modified:     1/1/2019
 **************************************************************************** */

import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation {

    private int[][] openCloseGrid;
    final private int[][] grid;
    final private int gridSize;
    final private int wquufSize;
    private int openSitesCount = 0;
    final private WeightedQuickUnionUF weightedQuickUnionUF;

    // creates n-by-n grid, with all sites initially blocked
    public Percolation(int n) {
        if (n <= 0) {
            throw new IllegalArgumentException("`n` must be greater or equal 0");
        }
        // created WQUUF with virtual top and bottom sites
        wquufSize = 2 + n * n;
        weightedQuickUnionUF = new WeightedQuickUnionUF(wquufSize);

        gridSize = n;
        openCloseGrid = new int[n][n];
        grid = new int[n][n];
        int initialGridCounter = 1;
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                openCloseGrid[i][j] = 0;
                grid[i][j] = initialGridCounter++;
            }
        }
    }

    // opens the site (row, col) if it is not open already
    public void open(int row, int col) {
        validate(row);
        validate(col);

        int gridRow = row - 1;
        int gridCol = col - 1;
        int gridCellValue = grid[gridRow][gridCol];

        // open site iff it is not opened
        if (!isOpen(row, col)) {
            openCloseGrid[gridRow][gridCol] = 1;
            openSitesCount++;
        }

        // connect to virtual top site iff not connected
        if (gridRow == 0) {
            if(weightedQuickUnionUF.find(0) != weightedQuickUnionUF.find(gridCellValue)) {
                weightedQuickUnionUF.union(gridCellValue, 0);
            }
        }

        // connect to virtual bottom site iff not connected
        if (gridRow == gridSize - 1) {
            if (weightedQuickUnionUF.find(wquufSize - 1) != weightedQuickUnionUF.find(gridCellValue)) {
                weightedQuickUnionUF.union(gridCellValue, wquufSize - 1);
            }
        }

        // connect to right adjacent if it is not connected and if it opened (if exists)
        try {
            int rightAdjacentCellValue = grid[gridRow][gridCol + 1];
            if(weightedQuickUnionUF.find(gridCellValue) != weightedQuickUnionUF.find(rightAdjacentCellValue) && isOpen(row, col + 1)) {
                weightedQuickUnionUF.union(gridCellValue, rightAdjacentCellValue);
            }
        } catch (IndexOutOfBoundsException | IllegalArgumentException e) {
            // do nothing
        }

        // connect to left adjacent if it is not connected and if it opened (if exists)
        try {
            int leftAdjacentCellValue = grid[gridRow][gridCol - 1];
            if (weightedQuickUnionUF.find(gridCellValue) != weightedQuickUnionUF.find(leftAdjacentCellValue) && isOpen(row, col - 1)) {
                weightedQuickUnionUF.union(gridCellValue, leftAdjacentCellValue);
            }
        } catch (IndexOutOfBoundsException | IllegalArgumentException e) {
            // do nothing
        }

        // connect to top adjacent if it is not connected and if it opened (if exists)
        try {
            int topAdjacentCellValue = grid[gridRow + 1][gridCol];
            if(weightedQuickUnionUF.find(gridCellValue) != weightedQuickUnionUF.find(topAdjacentCellValue) && isOpen(row + 1, col)) {
                weightedQuickUnionUF.union(gridCellValue, topAdjacentCellValue);
            }
        } catch (IndexOutOfBoundsException | IllegalArgumentException e) {
            // do nothing
        }

        // connect to bottom adjacent if it is not connected and if it opened (if exists)
        try {
            int bottomAdjacentCellValue = grid[gridRow - 1][gridCol];
            if(weightedQuickUnionUF.find(gridCellValue) != weightedQuickUnionUF.find(bottomAdjacentCellValue) && isOpen(row - 1, col)) {
                weightedQuickUnionUF.union(gridCellValue, bottomAdjacentCellValue);
            }
        } catch (IndexOutOfBoundsException | IllegalArgumentException e) {
            // do nothing
        }
    }

    // is the site (row, col) open?
    public boolean isOpen(int row, int col) {
        validate(row);
        validate(col);

        int gridRow = row - 1;
        int gridCol = col - 1;

        return openCloseGrid[gridRow][gridCol] == 1;
    }

    // is the site (row, col) full?
    public boolean isFull(int row, int col) {
        validate(row);
        validate(col);

        int gridRow = row - 1;
        int gridCol = col - 1;
        int gridCellValue = grid[gridRow][gridCol];

        return weightedQuickUnionUF.find(0) == weightedQuickUnionUF.find(gridCellValue);
    }

    // returns the number of open sites
    public int numberOfOpenSites() {
        return openSitesCount;
    }

    // does the system percolate?
    public boolean percolates() {
        return weightedQuickUnionUF.find(0) == weightedQuickUnionUF.find(wquufSize - 1);
    }

    private void validate(int p) {
        if (p < 1 || p > gridSize) {
            throw new IllegalArgumentException("index " + p + " is not between 1 and " + gridSize);
        }
    }

    public static void main(String[] args) {
        Percolation percolation = new Percolation(2);
        percolation.open(1, 1);
        percolation.open(2, 2);
        percolation.open(1, 2);
        System.out.println("Open sites count: " + percolation.numberOfOpenSites());

        System.out.println("pecolates: " + percolation.percolates());
        // System.out.println(percolation.weightedQuickUnionUF.find(16));
    }
}