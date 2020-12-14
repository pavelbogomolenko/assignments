package main.java;

public class RotateIntMatrixClockwise {

    private static int[][] initIntMatrix(int n, int m) {
        int[][] matrix = new int[n][m];
        int counter = 0;
        for(int i = 0; i < n; i++) {
            for(int j = 0; j < m; j++) {
                matrix[i][j] = ++counter;
            }
        }
        return matrix;
    }

    private static void inplaceMatrixTranspose(int[][] matrix) {
        for(int i = 0; i < matrix.length; i++) {
            for(int j = i; j < matrix[0].length; j++) {
                int temp = matrix[i][j];
                matrix[i][j] = matrix[j][i];
                matrix[j][i] = temp;
            }
        }
    }

    private static void reverseRows(int[][] matrix) {
        int matrixLen = matrix.length;
        int rowMid = matrixLen / 2;
        for(int row = 0; row < matrixLen; row++) {
            for(int lo = 0; lo < rowMid; lo++) {
                int hi = matrixLen - lo - 1;
                if(lo == hi) {
                    continue;
                }
                int tmp = matrix[row][lo];
                matrix[row][lo] = matrix[row][hi];
                matrix[row][hi] = tmp;
            }
        }
    }

    private static void printIntMatrix(int[][] matrix) {
        for(int i = 0; i < matrix.length; i++) {
            for(int j = 0; j < matrix[0].length; j++) {
                System.out.printf(" %d ", matrix[i][j]);
            }
            System.out.print("\n");
        }
    }

    public static void main(String args[]) {
        int[][] matrix = RotateIntMatrixClockwise.initIntMatrix(3, 3);

        System.out.println("Original matrix");
        RotateIntMatrixClockwise.printIntMatrix(matrix);

        System.out.println("Transposed Matrix");
        inplaceMatrixTranspose(matrix);
        RotateIntMatrixClockwise.printIntMatrix(matrix);

        System.out.println("Matrix with reversed columns");
        reverseRows(matrix);
        printIntMatrix(matrix);
    }
}