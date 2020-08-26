import main.java.Board;

import static org.junit.jupiter.api.Assertions.*;

import main.java.Utils;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.util.Iterator;

public class BoardTestCase {

    @Test
    void testInitBoard() {
        int[][] tiles = new int[][] {{1, 2, 3}, {4, 5, 0}};
        Board b = new Board(tiles);
    }

    @Test
    void testToStringRepresentation() {
        int[][] tiles = new int[][] {{1, 2}, {3, 0}};
        Board b = new Board(tiles);
        StringBuilder expectedOutput = new StringBuilder();
        expectedOutput.append(tiles.length + "\n");
        expectedOutput.append(String.format("%2d ", tiles[0][0]));
        expectedOutput.append(String.format("%2d ", tiles[0][1]));
        expectedOutput.append("\n");
        expectedOutput.append(String.format("%2d ", tiles[1][0]));
        expectedOutput.append(String.format("%2d ", tiles[1][1]));
        expectedOutput.append("\n");
        assertEquals(expectedOutput.toString(), b.toString());
    }

    @Test
    void testShouldThrowExeptionIfTilesSizeGreater128() {
        assertThrows(IndexOutOfBoundsException.class, ()-> {
            int[][] tiles = new int[][]{
                    {1, 2, 3, 4, 5, 6, 7, 8},
                    {1, 2, 3, 4, 5, 6, 7, 8},
                    {1, 2, 3, 4, 5, 6, 7, 8},
                    {1, 2, 3, 4, 5, 6, 7, 8},
                    {1, 2, 3, 4, 5, 6, 7, 8},
                    {1, 2, 3, 4, 5, 6, 7, 8},
                    {1, 2, 3, 4, 5, 6, 7, 8},
                    {1, 2, 3, 4, 5, 6, 7, 8},
                    {1, 2, 3, 4, 5, 6, 7, 8},
                    {1, 2, 3, 4, 5, 6, 7, 8},
                    {1, 2, 3, 4, 5, 6, 7, 8},
                    {1, 2, 3, 4, 5, 6, 7, 8},
                    {1, 2, 3, 4, 5, 6, 7, 8},
                    {1, 2, 3, 4, 5, 6, 7, 8},
                    {1, 2, 3, 4, 5, 6, 7, 8},
                    {1, 2, 3, 4, 5, 6, 7, 0},
            };
            Board b = new Board(tiles);
        });
    }

    @Test
    void testShouldThrowExeptionIfTilesSizeLessThan2() {
        assertThrows(IndexOutOfBoundsException.class, () -> {
            int[][] tiles = new int[][]{{1}};
            Board b = new Board(tiles);
        });
    }

    @ParameterizedTest
    @CsvSource({
            "5, '8 1 3', '4 0 2', '7 6 5'",
            "4, '0 1 3', '4 2 5', '7 8 6'"
    })
    void testHammingDistance(int expectedDistance, String rowOne, String rowTwo, String rowThree) {
        int[][] tiles = new int[][] {
                Utils.csvStrToInt(rowOne),
                Utils.csvStrToInt(rowTwo),
                Utils.csvStrToInt(rowThree),
        };
        Board b = new Board(tiles);
        System.out.println(b.toString());

        assertEquals(expectedDistance, b.hamming());
    }

    @ParameterizedTest
    @CsvSource({
            "10, '8 1 3', '4 0 2', '7 6 5'",
            "4,  '0 1 3', '4 2 5', '7 8 6'",
            "3,  '1 2 3', '4 8 5', '7 0 6'",
    })
    void testManhattanDistance(int expectedDistance, String rowOne, String rowTwo, String rowThree) {
        int[][] tiles = new int[][] {
                Utils.csvStrToInt(rowOne),
                Utils.csvStrToInt(rowTwo),
                Utils.csvStrToInt(rowThree),
        };
        Board b = new Board(tiles);
        System.out.println(b.toString());

        assertEquals(expectedDistance, b.manhattan());
    }

    @Test
    void testBoardIsEqualToAnother() {
        int[][] tilesForBoardOne = new int[][] {
                {1,  2,  3,  4},
                {5,  0,  7,  8},
                {9,  10, 11, 12},
                {3,  14, 15,  6}
        };

        assertTrue(new Board(tilesForBoardOne).equals(new Board(tilesForBoardOne)));
    }

    @Test
    void testBoardIsNotEqualToAnother() {
        int[][] tilesForBoardOne = new int[][] {
                {1,  2,  3,  4},
                {5,  0,  7,  8},
                {9,  10, 11, 12},
                {3,  14, 15,  6}
        };

        int[][] tilesForBoardTwo = new int[][] {
                {1,  2,  3,  0},
                {5,  4,  7,  8},
                {9,  10, 11, 12},
                {3,  14, 15,  6}
        };

        assertFalse(new Board(tilesForBoardOne).equals(new Board(tilesForBoardTwo)));
    }

    @Test
    void testBoardIsEqualToItself() {
        int[][] tilesForBoardOne = new int[][] {
                {1,  2,  3,  4},
                {5,  0,  7,  8},
                {9,  10, 11, 12},
                {3,  14, 15,  6}
        };
        Board b = new Board(tilesForBoardOne);
        assertTrue(b.equals(b));
    }

    @Test
    void testBoardIsNotEqualToNull() {
        int[][] tilesForBoardOne = new int[][] {
                {1,  2},
                {3,  0},
        };
        Board b = new Board(tilesForBoardOne);
        assertFalse(b.equals(null));
    }

    @Test
    void testBoardIsNotEqualToSomeOtherObject() {
        int[][] tilesForBoardOne = new int[][] {
                {1,  2},
                {3,  0},
        };
        String s = new String("hello");
        Board b = new Board(tilesForBoardOne);
        assertFalse(b.equals(s));
    }

    @Test
    void testNeighboringBoardsTwoNeighborsFirst() {
        int[][] tiles = new int[][] {
                {0, 1, 3},
                {4, 2, 5},
                {7, 8, 6},
        };
        int[][] neighborTilesOne = new int[][] {
                {1, 0, 3},
                {4, 2, 5},
                {7, 8, 6},
        };
        int[][] neighborTilesTwo = new int[][] {
                {4, 1, 3},
                {0, 2, 5},
                {7, 8, 6},
        };
        Board boardOne = new Board(tiles);
        Board boardTwo = new Board(neighborTilesOne);
        Board boardThree = new Board(neighborTilesTwo);

        Iterable<Board> neighbors = boardOne.neighbors();
        Iterator<Board> boardIter = neighbors.iterator();
        assertTrue(boardTwo.equals(boardIter.next()));
        assertTrue(boardThree.equals(boardIter.next()));
    }

    @Test
    void testNeighboringBoardsFourNeighbors() {
        int[][] tiles = new int[][] {
                {5, 4, 1},
                {3, 0, 2},
                {7, 8, 6},
        };
        int[][] neighborTilesOne = new int[][] {
                {5, 0, 1},
                {3, 4, 2},
                {7, 8, 6},
        };
        int[][] neighborTilesTwo = new int[][] {
                {5, 4, 1},
                {0, 3, 2},
                {7, 8, 6},
        };
        int[][] neighborTilesThree = new int[][] {
                {5, 4, 1},
                {3, 8, 2},
                {7, 0, 6},
        };
        int[][] neighborTilesFour = new int[][] {
                {5, 4, 1},
                {3, 2, 0},
                {7, 8, 6},
        };
        Board board = new Board(tiles);
        Board neighborOne = new Board(neighborTilesOne);
        Board neighborTwo = new Board(neighborTilesTwo);
        Board neighborThree = new Board(neighborTilesThree);
        Board neighborFour = new Board(neighborTilesFour);

        Iterable<Board> neighbors = board.neighbors();
        Iterator<Board> boardIter = neighbors.iterator();
        assertTrue(neighborOne.equals(boardIter.next()));
        assertTrue(neighborTwo.equals(boardIter.next()));
        assertTrue(neighborFour.equals(boardIter.next()));
        assertTrue(neighborThree.equals(boardIter.next()));
    }

    @Test
    void testIsGoalBoardTrue() {
        int[][] tilesForBoardOne = new int[][] {
                {1,  2},
                {3,  0},
        };
        Board b = new Board(tilesForBoardOne);
        assertTrue(b.isGoal());
    }

    @Test
    void testIsGoalBoardFalse() {
        int[][] tilesForBoardOne = new int[][] {
                {1,  3},
                {2,  0},
        };
        Board b = new Board(tilesForBoardOne);
        assertFalse(b.isGoal());
    }
}
