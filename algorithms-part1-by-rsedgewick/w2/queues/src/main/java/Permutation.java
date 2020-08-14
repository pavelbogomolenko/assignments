package main.java;

import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdOut;

public class Permutation {
    public static void main(String[] args) {
        int k = Integer.parseInt(args[0]);
        In in = new In(args[1]);

        RandomizedQueue<String> randomizedQueue = new RandomizedQueue<>(k);
        while (!in.isEmpty()) {
            randomizedQueue.enqueue(in.readString());
        }

        int stringCounter = 0;
        for(String s: randomizedQueue) {
            if(stringCounter++ == k) {
                break;
            }
            StdOut.println(s);
        }
    }
}
