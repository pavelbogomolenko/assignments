package main.java;

public class BinaryTreeAdjList {

    private final int V;
    private final BinNode[] adj;

    public BinaryTreeAdjList(int v) {
        this.V = v;
        adj = new BinNode[v];
        for(int i = 0; i < this.V; i++) {
            adj[i] = new BinNode();
        }
    }

    public int vertices() {
        return this.V;
    }

    public int[] adj(int v) {
        if(v >= V) {
            return null;
        }
        return new int[]{ adj[v].left, adj[v].right };
    }

    public int getLeftNode(int v) {
        if(v >= V || v == -1) {
            return -1;
        }
//        if(adj[v].left == 0) {
//            return -1;
//        }
        return adj[v].left;
    }

    public int getRightNode(int v) {
        if(v >= V || v == -1) {
            return -1;
        }
//        if(adj[v].right == 0) {
//            return -1;
//        }
        return adj[v].right;
    }

    public void addLeftEdge(int v, int w) {
        this.adj[v].left = w;
    }

    public void addRightEdge(int v, int w) {
        this.adj[v].right = w;
    }

    public int depth() {
        return (int)(Math.log(this.V) / Math.log(2));
    }

    private class BinNode {
        private int left = -1;
        private int right = -1;
    }

    public static void main(String[] args) {
        BinaryTreeAdjList binaryTree = new BinaryTreeAdjList(7);
        binaryTree.addLeftEdge(0, 1);
        binaryTree.addRightEdge(0, 2);
        System.out.println("depth: " + binaryTree.depth());
    }
}
