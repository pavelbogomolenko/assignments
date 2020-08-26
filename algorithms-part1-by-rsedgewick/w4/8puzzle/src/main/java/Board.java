package main.java;

import java.util.Arrays;
import java.util.ArrayList;

import edu.princeton.cs.algs4.StdRandom;

public class Board {
    private final int n;
    private final int[][] tiles;
    private static final int BOARD_SIZE_MAX = 128;
    private static final int BOARD_SIZE_MIN = 2;

    // create a board from an n-by-n array of tiles,
    // where tiles[row][col] = tile at (row, col)
    public Board(int[][] boardTiles) {
        n = boardTiles.length;
        int dimention = n * n;
        if(dimention >= BOARD_SIZE_MAX || dimention < BOARD_SIZE_MIN) {
            throw new IndexOutOfBoundsException();
        }

        tiles = copyTiles(boardTiles);
    }

    // board dimension n
    public int dimension() {
        return n;
    }

    // number of tiles out of place
    public int hamming() {
        int result = 0;
        for (int row = 0; row < n; row++) {
            for (int col = 0; col < n; col++) {
                int goalValue = goalTileValue(row, col);
                if (goalValue != 0 && goalValue != tiles[row][col]) {
                    result++;
                }
            }
        }
        return result;
    }

    // sum of Manhattan distances between tiles and goal
    public int manhattan() {
        int result = 0;
        for (int row = 0; row < n; row++) {
            for (int col = 0; col < n; col++) {
                int value = tiles[row][col];
                if (value == 0) {
                    continue;
                }
                int goalRow = (value - 1) / n;
                int goalCol = (value - 1) % n;
                result += Math.abs(row - goalRow) + Math.abs(col - goalCol);
            }
        }
        return result;
    }

    // is this board the goal board?
    public boolean isGoal() {
        for (int row = 0; row < n; row++) {
            for (int col = 0; col < n; col++) {
                if (tiles[row][col] != goalTileValue(row, col)) {
                    return false;
                }
            }
        }
        return true;
    }

    // does this board equal y?
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if (other == null) {
            return false;
        }
        if (other.getClass() != this.getClass()) {
            return false;
        }

        Board otherBoard = (Board) other;
        return Arrays.deepEquals(this.tiles, otherBoard.tiles);
    }

    // all neighboring boards
    public Iterable<Board> neighbors() {
        int blankRow;
        int blankCol = 0;
        boolean blankFound = false;
        ArrayList<Board> neighbors = new ArrayList<Board>();

        for (blankRow = 0; blankRow < n; blankRow++) {
            for (blankCol = 0; blankCol < n; blankCol++)
                if (tiles[blankRow][blankCol] == 0) {
                    blankFound = true;
                    break;
                }

            if (blankFound) break;
        }

        // Top
        if (blankRow > 0) {
            neighbors.add(copyAndSwapTiles(blankRow, blankCol, blankRow - 1, blankCol));
        }

        // Left
        if (blankCol > 0) {
            neighbors.add(copyAndSwapTiles(blankRow, blankCol, blankRow, blankCol - 1));
        }

        // Right
        if (blankCol < n - 1) {
            neighbors.add(copyAndSwapTiles(blankRow, blankCol, blankRow, blankCol + 1));
        }

        // Bottom
        if (blankRow < n - 1) {
            neighbors.add(copyAndSwapTiles(blankRow, blankCol, blankRow + 1, blankCol));
        }

        return neighbors;
    }

    // a board that is obtained by exchanging any pair of tiles
    public Board twin() {
        int firstRow = StdRandom.uniform(n);
        int firstCol = StdRandom.uniform(n);
        while (tiles[firstRow][firstCol] == 0) {
            firstRow = StdRandom.uniform(n);
            firstCol = StdRandom.uniform(n);
        }
        int secondRow = StdRandom.uniform(n);
        int secondCol = StdRandom.uniform(n);
        while (tiles[secondRow][secondCol] == 0 || (firstRow == secondRow && firstCol == secondCol)) {
            secondRow = StdRandom.uniform(n);
            secondCol = StdRandom.uniform(n);
        }
        return copyAndSwapTiles(firstRow, firstCol, secondRow, secondCol);
    }

    // string representation of this board
    public String toString() {
        StringBuilder s = new StringBuilder();

        s.append(n + "\n");
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++)
                s.append(String.format("%2d ", tiles[i][j]));

            s.append("\n");
        }
        return s.toString();
    }

    private int goalTileValue(int row, int col) {
        if (row == n - 1 && col == n - 1) {
            return 0;
        }
        return row * n + col + 1;
    }

    private int[][] copyTiles(int[][] origin) {
        int[][] destination = new int[n][n];

        for (int row = 0; row < n; row++) {
            for (int col = 0; col < n; col++) {
                destination[row][col] = origin[row][col];
            }
        }

        return destination;
    }

    private Board copyAndSwapTiles(int originalRow, int originalCol, int newRow, int newCol) {
        int[][] boardCopy = copyTiles(tiles);

        boardCopy[originalRow][originalCol] = tiles[newRow][newCol];
        boardCopy[newRow][newCol] = tiles[originalRow][originalCol];

        return new Board(boardCopy);
    }
}
