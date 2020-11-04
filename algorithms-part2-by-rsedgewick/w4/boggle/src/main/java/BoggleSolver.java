package main.java;

import edu.princeton.cs.algs4.StdOut;

import java.util.HashSet;
import java.util.Set;

public class BoggleSolver {

    private final HashSet<String> wordDict = new HashSet<>();

    // Initializes the data structure using the given array of strings as the dictionary.
    // (You can assume each word in the dictionary contains only the uppercase letters A through Z.)
    public BoggleSolver(String[] dictionary) {
        for(String word: dictionary) {
            wordDict.add(word);
        }
    }

    // Returns the set of all valid words in the given Boggle board, as an Iterable.
    public Iterable<String> getAllValidWords(BoggleBoard board) {
        Set<String> wordCandidates = new HashSet<>();
        for(int i = 0; i < board.cols(); i++) {
            for (int j = 0; j < board.rows(); j++) {
                boolean[][] marked = new boolean[board.rows()][board.cols()];
                traverseBoardDfs(board, i, j, marked, "", wordCandidates);
            }
        }
        return wordCandidates;
    }

    private void traverseBoardDfs(BoggleBoard board, int i, int j, boolean[][] m, String prefix, Set<String> wordCandidates) {
        if((i < 0 || i > board.cols() - 1) || (j < 0 || j > board.rows() - 1) || m[i][j]) {
            return;
        }
        prefix += board.getLetter(i, j);
        if(prefixExists(prefix)) {
            if(prefix.length() > 2 && wordDict.contains(prefix)) {
                wordCandidates.add(prefix);
            }
            // each recur call should have each own copy of marked array
            m = copyArray(m);
            m[i][j] = true;
            // sequentially traverse neighbors of i,j of the board
            for(int ii = -1; ii <= 1; ii++) {
                for(int jj = -1; jj <= 1; jj++) {
                    if(ii == 0 && jj == 0) {
                        continue;
                    }
                    traverseBoardDfs(board, i + ii, j + jj, m, prefix, wordCandidates);
                }
            }
        }
    }

    private boolean prefixExists(String prefix) {
        for(String w: wordDict) {
            if(w.contains(prefix)) {
                return true;
            }
        }
        return false;
    }

    private boolean[][] copyArray(boolean[][] m) {
        boolean[][] newM = new boolean[m.length][m[0].length];
        for (int i = 0; i < m.length; i++) {
            for (int j = 0; j < m[0].length; j++) {
                newM[i][j] = m[i][j];
            }
        }
        return newM;
    }

    public static void main(String[] args) {
//        String[] d = {"DOG", "DOLL", "DOUGH", "LOGO", "HOG", "GOD"};
        String[] d = {"DOG", "DOLL", "DOUGH", "LOGO", "HOG", "GOD", "NEXT", "DGH"};
        BoggleSolver bs = new BoggleSolver(d);
        char[][] board = {
                {'D', 'O', 'L'},
                {'O', 'G', 'L'},
                {'U', 'O', 'H'}
        };
//        char[][] board = {
//                {'N', 'X', 'X', 'X'},
//                {'X', 'E', 'X', 'X'},
//                {'X', 'X', 'X', 'X'},
//                {'X', 'X', 'X', 'T'},
//        };
        BoggleBoard bb = new BoggleBoard(board);
        for (String word : bs.getAllValidWords(bb)) {
            StdOut.println(word);
        }
    }
}
