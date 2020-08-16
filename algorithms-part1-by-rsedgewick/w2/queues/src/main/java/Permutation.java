package main.java;

import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;

public class Permutation {
    public static void main(String[] args) {
        int k = Integer.parseInt(args[0]);

        RandomizedQueue<String> randomizedQueue = new RandomizedQueue<>();
        while (!StdIn.isEmpty()) {
            randomizedQueue.enqueue(StdIn.readString());
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
