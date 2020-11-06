package main.java;

import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.TST;

import java.util.HashSet;
import java.util.Set;

public class BoggleSolver {

    private final TST<String> wordDict = new TST<>();

    // Initializes the data structure using the given array of strings as the dictionary.
    // (You can assume each word in the dictionary contains only the uppercase letters A through Z.)
    public BoggleSolver(String[] dictionary) {
        for(String word: dictionary) {
            wordDict.put(word, word);
        }
    }

    // Returns the set of all valid words in the given Boggle board, as an Iterable.
    public Iterable<String> getAllValidWords(BoggleBoard board) {
        Set<String> wordCandidates = new HashSet<>();
        for(int i = 0; i < board.rows(); i++) {
            for (int j = 0; j < board.cols(); j++) {
                boolean[][] marked = new boolean[board.rows()][board.cols()];
                traverseBoardDfs(board, i, j, marked, "", wordCandidates);
            }
        }
        return wordCandidates;
    }

    // Returns the score of the given word if it is in the dictionary, zero otherwise.
    // (You can assume the word contains only the uppercase letters A through Z.)
    public int scoreOf(String word) {
        int wordLength = word.length();
        if(!wordDict.contains(word) || wordLength < 3) {
            return 0;
        }
        if (wordLength >= 3 && wordLength < 5) {
            return 1;
        }
        else if (wordLength == 5) {
            return 2;
        }
        else if (wordLength == 6) {
            return 3;
        }
        else if (wordLength == 7) {
            return 5;
        }
        return 11;
    }

    private void traverseBoardDfs(BoggleBoard board, int i, int j, boolean[][] m, String prefix, Set<String> wordCandidates) {
        if((i < 0 || i > board.rows() - 1) || (j < 0 || j > board.cols() - 1) || m[i][j]) {
            return;
        }
        prefix += getLetter(board, i, j);
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

    private String getLetter(BoggleBoard board, int i , int j) {
        String s = "";
        char letter = board.getLetter(i, j);
        if (letter == 'Q') {
            s += "QU";
        } else {
            s += letter;
        }
        return s;
    }

    private boolean prefixExists(String prefix) {
        Iterable<String> keys = wordDict.keysWithPrefix(prefix);
        return keys.iterator().hasNext();
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
//        String[] d = {"DOG", "DOLL", "DOUGH", "LOGO", "HOG", "GOD", "NEXT", "DGH"};
//        BoggleSolver bs = new BoggleSolver(d);
//        char[][] board = {
//                {'D', 'O', 'L'},
//                {'O', 'G', 'L'},
//                {'U', 'O', 'H'}
//        };
////        char[][] board = {
////                {'N', 'X', 'X', 'X'},
////                {'X', 'E', 'X', 'X'},
////                {'X', 'X', 'X', 'X'},
////                {'X', 'X', 'X', 'T'},
////        };
//        BoggleBoard bb = new BoggleBoard(board);
//        for (String word : bs.getAllValidWords(bb)) {
//            StdOut.println(word);
//        }

        In in = new In(args[0]);
        String[] dictionary = in.readAllStrings();
        BoggleSolver solver = new BoggleSolver(dictionary);
        BoggleBoard board = new BoggleBoard(args[1]);
        int score = 0;
        for (String word : solver.getAllValidWords(board)) {
            StdOut.println(word);
            score += solver.scoreOf(word);
        }
        StdOut.println("Score = " + score);
    }
}
