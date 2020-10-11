package main.java;

import edu.princeton.cs.algs4.BreadthFirstDirectedPaths;
import edu.princeton.cs.algs4.Digraph;
import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdOut;

import java.util.ArrayList;
import java.util.Arrays;

public class SAP {
    private final Digraph g;

    // constructor takes a digraph (not necessarily a DAG)
    public SAP(Digraph G) {
        if(G == null) {
            throw new IllegalArgumentException();
        }
        g = G;
    }

    // length of shortest ancestral path between v and w; -1 if no such path
    // @TODO implement cache
    public int length(int v, int w) {
        if(v < 0 && v > g.V()) {
            throw new IllegalArgumentException();
        }
        if(w < 0 && w > g.V()) {
            throw new IllegalArgumentException();
        }
        int commonAncestor = ancestor(v, w);
        if(commonAncestor == -1) {
            return -1;
        }
        return findLengthToV(commonAncestor, new BreadthFirstDirectedPaths(g, v), new BreadthFirstDirectedPaths(g, w));
    }


    // length of shortest ancestral path between any vertex in v and any vertex in w; -1 if no such path
    // @TODO implement cache
    public int length(Iterable<Integer> v, Iterable<Integer> w) {
        validateVertices(v, w);
        if(isZeroLengthVertices(v, w)) {
            return -1;
        }
        int commonAncestor = ancestor(v, w);
        if(commonAncestor == -1) {
            return -1;
        }
        return findLengthToV(commonAncestor, new BreadthFirstDirectedPaths(g, v), new BreadthFirstDirectedPaths(g, w));
    }

    // a common ancestor of v and w that participates in a shortest ancestral path; -1 if no such path
    // @TODO implement cache
    public int ancestor(int v, int w) {
        if(v < 0 && v > g.V()) {
            throw new IllegalArgumentException();
        }
        if(w < 0 && w > g.V()) {
            throw new IllegalArgumentException();
        }
        return findCommonAncestor(new BreadthFirstDirectedPaths(g, v), new BreadthFirstDirectedPaths(g, w));
    }

    // a common ancestor that participates in shortest ancestral path; -1 if no such path
    public int ancestor(Iterable<Integer> v, Iterable<Integer> w) {
        validateVertices(v, w);
        if(isZeroLengthVertices(v, w)) {
            return -1;
        }
        return findCommonAncestor(new BreadthFirstDirectedPaths(g, v), new BreadthFirstDirectedPaths(g, w));
    }

    private int findCommonAncestor(BreadthFirstDirectedPaths pathsV, BreadthFirstDirectedPaths pathsW) {
        int minDist = Integer.MAX_VALUE;
        int commonAncestor = -1;
        for(int iVertex = 0; iVertex < g.V(); iVertex++) {
            if(pathsV.hasPathTo(iVertex) && pathsW.hasPathTo(iVertex)) {
                int dist = pathsV.distTo(iVertex) + pathsW.distTo(iVertex);
                if(dist < minDist) {
                    minDist = dist;
                    commonAncestor = iVertex;
                }
            }
        }
        return commonAncestor;
    }

    private void validateVertices(Iterable<Integer> v, Iterable<Integer> w) {
        if(v == null || w == null) {
            throw new IllegalArgumentException();
        }
    }

    private boolean isZeroLengthVertices(Iterable<Integer> v, Iterable<Integer> w) {
        ArrayList<Integer> resultV = new ArrayList<>();
        v.forEach(resultV::add);
        if(resultV.size() == 0) {
            return true;
        }

        ArrayList<Integer> resultW = new ArrayList<>();
        w.forEach(resultW::add);
        if(resultW.size() == 0) {
            return true;
        }

        return false;
    }

    private int findLengthToV(int v, BreadthFirstDirectedPaths pathsV, BreadthFirstDirectedPaths pathsW) {
        return pathsV.distTo(v) + pathsW.distTo(v);
    }

    public static void main(String[] args) {
        In in = new In(args[0]);
        Digraph G = new Digraph(in);
        SAP sap = new SAP(G);
        StdOut.println(G.toString());

        Integer[] v = {13, 23, 24};
        Integer[] w = {6, 16, 17};

        int length   = sap.length(Arrays.asList(v), Arrays.asList(w));
        int ancestor = sap.ancestor(Arrays.asList(v), Arrays.asList(w));
        StdOut.printf("length = %d, ancestor = %d\n", length, ancestor);
    }
}
