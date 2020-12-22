package main.java;

import java.util.Iterator;

public class DiGraph {

    private final int V;
    private final IntBag[] adj;

    public DiGraph(int v) {
        this.V = v;
        adj = new IntBag[v];
        for (int i = 0; i < v; i++) {
            adj[i] = new IntBag();
        }
    }

    public void addEdge(int v, int w) {
        adj[v].add(w);
    }

    public int vertices() {
        return this.V;
    }

    public Iterable<Integer> adj(int v) {
        return this.adj[v];
    }

    private class IntBag implements Iterable<Integer> {
        private Node root;
        private int size;

        public IntBag() {
            this.size = 0;
        }

        public int size() {
            return size;
        }

        private void add(int item) {
            Node newRoot = new Node(item);
            newRoot.next = root;
            root = newRoot;
            this.size++;
        }

        @Override
        public Iterator<Integer> iterator() {
            return new IntBagIterator(root);
        }

        private class IntBagIterator implements Iterator<Integer> {
            private Node current;

            public IntBagIterator(Node first) {
                current = first;
            }

            @Override
            public boolean hasNext() {
                return current != null;
            }

            @Override
            public Integer next() {
                Integer data = current.data;
                current = current.next;
                return data;
            }
        }
    }

    private class Node {
        private Node next;
        private int data;

        public Node(int data) {
            this.data = data;
        }
    }

    public static void main(String[] args) {
        DiGraph diGraph = new DiGraph(7);
        diGraph.addEdge(0,1);
        diGraph.addEdge(0,2);
        diGraph.addEdge(0,3);
        diGraph.addEdge(1,3);
        diGraph.addEdge(1,4);

        for(int v = 0; v < diGraph.V; v++) {
            System.out.printf("v %d: ", v);
            for(int adj: diGraph.adj(v)) {
                System.out.printf("%d -> ", adj);
            }
            System.out.print("\n");
        }
    }
}
