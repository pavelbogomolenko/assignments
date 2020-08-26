package main.java;

import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.MinPQ;
import edu.princeton.cs.algs4.StdOut;

import java.util.ArrayList;

public class Solver {

    private SearchNode solutionNode;
    private boolean solvable = true;

    public Solver(Board initialBoard) {
        if(initialBoard == null) {
            throw new IllegalArgumentException();
        }

        MinPQ<SearchNode> gameTree = new MinPQ<>();
        gameTree.insert(new SearchNode(initialBoard, null, 0));

        MinPQ<SearchNode> twinGameTree = new MinPQ<>();
        twinGameTree.insert(new SearchNode(initialBoard.twin(), null, 0));

        while (solutionNode == null) {
            SearchNode currentSearchNode = gameTree.delMin();
            if(currentSearchNode.board.isGoal()) {
                solutionNode = currentSearchNode;
                break;
            }
            for(Board neighbor: currentSearchNode.board.neighbors()) {
                if(currentSearchNode.previous != null && neighbor.equals(currentSearchNode.previous.board)) {
                    continue;
                }
                gameTree.insert(new SearchNode(neighbor, currentSearchNode, currentSearchNode.moves + 1));
            }

            SearchNode currentTwinSearchNode = twinGameTree.delMin();
            if(currentTwinSearchNode.board.isGoal()) {
                solvable = false;
                break;
            }
            for(Board neighbor: currentTwinSearchNode.board.neighbors()) {
                if(currentTwinSearchNode.previous != null && neighbor.equals(currentTwinSearchNode.previous.board)) {
                    continue;
                }
                twinGameTree.insert(new SearchNode(neighbor, currentTwinSearchNode, currentTwinSearchNode.moves + 1));
            }
        }
    }

    // is the initial board solvable? (see below)
    public boolean isSolvable() {
        return solvable;
    }

    // min number of moves to solve initial board; -1 if unsolvable
    public int moves() {
        if(!solvable) {
            return -1;
        }
        return solutionNode.moves;
    }

    // sequence of boards in a shortest solution; null if unsolvable
    public Iterable<Board> solution() {
        if(!solvable) {
            return null;
        }
        ArrayList<Board> boardMoves = new ArrayList<>();

        SearchNode previousBoard = solutionNode.previous;
        while (previousBoard != null) {
            boardMoves.add(previousBoard.board);
            previousBoard = previousBoard.previous;
        }

        return boardMoves;
    }

    private class SearchNode implements Comparable<SearchNode> {
        public Board board;
        public SearchNode previous;
        public int moves;
        public Integer cachedPriority;

        public SearchNode(Board board, SearchNode previous, int moves) {
            this.board = board;
            this.previous = previous;
            this.moves = moves;
        }

        public int hammingPriority() {
            if (cachedPriority == null) {
                cachedPriority = moves + board.hamming();
            }

            return cachedPriority;
        }

        public int manhattanPriority() {
            if (cachedPriority == null) {
                cachedPriority = moves + board.manhattan();
            }

            return cachedPriority;
        }

        public int compareTo(SearchNode other) {
            return this.hammingPriority() - other.hammingPriority();
        }
    }

    public static void main(String[] args) {

        // create initial board from file
        In in = new In(args[0]);
        int n = in.readInt();
        int[][] tiles = new int[n][n];
        for (int i = 0; i < n; i++)
            for (int j = 0; j < n; j++)
                tiles[i][j] = in.readInt();
        Board initial = new Board(tiles);

        // solve the puzzle
        Solver solver = new Solver(initial);

        // print solution to standard output
        if (!solver.isSolvable())
            StdOut.println("No solution possible");
        else {
            StdOut.println("Minimum number of moves = " + solver.moves());
            for (Board board : solver.solution())
                StdOut.println(board);
        }
    }
}
