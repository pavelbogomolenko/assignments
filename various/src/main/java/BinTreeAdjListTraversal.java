package main.java;

public class BinTreeAdjListTraversal {

    private final BinaryTreeAdjList binaryTreeAdjList;
    private final LinkedList[] linkedLists;

    public BinTreeAdjListTraversal(BinaryTreeAdjList binaryTreeAdjList) {
        this.binaryTreeAdjList = binaryTreeAdjList;
        linkedLists = new LinkedList[this.binaryTreeAdjList.depth()];
        for (int i = 0; i < this.binaryTreeAdjList.depth(); i++) {
            linkedLists[i] = new LinkedList();
        }
    }

    public boolean isBinTreeBalanced() {
        System.out.println(maxSubtreeDepth(1));
        System.out.println(maxSubtreeDepth(2));
        return false;
    }

    public int maxSubtreeDepth(int v) {
        int left = this.binaryTreeAdjList.getLeftNode(v);
        int right = this.binaryTreeAdjList.getRightNode(v);

        if(left == -1 && right == -1) {
            return 1;
        }

        int leftDepth = maxSubtreeDepth(left);
        int rightDepth = maxSubtreeDepth(right);

        if(leftDepth > rightDepth) {
            return 1 + leftDepth;
        }
        return 1 + rightDepth;
    }

    public void traverseAsLL() {
        int[] v = this.binaryTreeAdjList.adj(0);
        traverseAsLL(0, v);
        System.out.println("traversed");
    }

    private void traverseAsLL(int d, int[] v) {
        LinkedList ll = new LinkedList();
        int[] newV = new int[v.length * 2];
        for(int i = 0; i < v.length * 2; i++) {
            newV[i] = -1;
        }
        int counter = 0;
        boolean nasNewLevel = false;
        for(int i = 0; i < v.length; i++) {
            if(v[i] == -1) {
                continue;
            }
            ll.addNode(v[i]);
            for(int adj: this.binaryTreeAdjList.adj(v[i])) {
                newV[counter++] = adj;
                nasNewLevel = true;
            }
        }
        if(!nasNewLevel) {
            return;
        }

        linkedLists[d] = ll;
        traverseAsLL(d + 1, newV);
    }

    private static class LinkedList {
        private Node last;

        public void addNode(int value) {
            Node newNode = new Node(value);
            newNode.next = last;
            last = newNode;
        }
    }

    private static class Node {
        private int value;
        private Node next;

        public Node(int value) {
            this.value = value;
        }
    }

    public static void main(String[] args) {
        BinaryTreeAdjList binaryTree = new BinaryTreeAdjList(9);
        binaryTree.addLeftEdge(0, 1);
        binaryTree.addRightEdge(0, 2);
        binaryTree.addLeftEdge(1, 3);
        binaryTree.addRightEdge(1, 4);
//        binaryTree.addLeftEdge(2, 5);
//        binaryTree.addLeftEdge(2, 6);
        binaryTree.addLeftEdge(3, 7);
//        binaryTree.addLeftEdge(3, 8);
        System.out.println("depth: " + binaryTree.depth());
        BinTreeAdjListTraversal binTreeAdjListTraversal = new BinTreeAdjListTraversal(binaryTree);
        binTreeAdjListTraversal.traverseAsLL();


        System.out.println("tree is balanced: " + binTreeAdjListTraversal.isBinTreeBalanced());

        System.out.println("end");
    }
}
