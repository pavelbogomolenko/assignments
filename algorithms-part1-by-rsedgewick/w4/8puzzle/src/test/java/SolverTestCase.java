import main.java.Board;
import main.java.Solver;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class SolverTestCase {

    @Test
    void testInitSolverWithNullShouldThrow() {
        assertThrows(IllegalArgumentException.class, ()-> {
            new Solver(null);
        });
    }

    @Test
    void testSolve2x2_01_happy_case() {
        int[][] tiles = new int[][] {
                {1, 2},
                {0, 3},
        };
        Board board = new Board(tiles);
        Solver solver = new Solver(board);
        assertTrue(solver.isSolvable());
        assertEquals(1, solver.moves());
    }

    @Test
    void testSolve3x3_04_happy_case() {
        int[][] tiles = new int[][] {
                {0, 1, 3},
                {4, 2, 5},
                {7, 8, 6},
        };
        Board board = new Board(tiles);
        Solver solver = new Solver(board);
        assertTrue(solver.isSolvable());
    }

    @Test
    void testSolve3x3_10_dublicate_moves() {
        int[][] tiles = new int[][] {
                {0, 4, 1},
                {5, 3, 2},
                {7, 8, 6},
        };
        Board board = new Board(tiles);
        Solver solver = new Solver(board);
        assertTrue(solver.isSolvable());
        assertEquals(10, solver.moves());
    }

    @Test
    void testSolve3x3_9_dublicate_moves() {
        int[][] tiles = new int[][] {
                {1, 3, 6},
                {5, 2, 8},
                {4, 0, 7},
        };
        Board board = new Board(tiles);
        Solver solver = new Solver(board);
        assertTrue(solver.isSolvable());
        assertEquals(9, solver.moves());
    }

    @Test
    void testSolve2x2_no_solution() {
        int[][] tiles = new int[][] {
                {1, 0},
                {2, 3},
        };
        Board board = new Board(tiles);
        Solver solver = new Solver(board);
        assertFalse(solver.isSolvable());
        assertEquals(-1, solver.moves());
    }

    @Test
    void testSolve3x3_no_solution() {
        int[][] tiles = new int[][] {
                {1,  2,  3},
                {4,  6,  5},
                {7,  8,  0},
        };
        Board board = new Board(tiles);
        Solver solver = new Solver(board);
        assertFalse(solver.isSolvable());
        assertEquals(-1, solver.moves());
    }
}
