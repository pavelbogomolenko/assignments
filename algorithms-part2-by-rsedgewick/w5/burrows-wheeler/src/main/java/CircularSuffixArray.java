package main.java;

import edu.princeton.cs.algs4.StdOut;

import java.util.Arrays;

public class CircularSuffixArray {
    private final int len;
    private final String s;
    private final SuffixArray[] suffixes;

    // circular suffix array of s
    public CircularSuffixArray(String s) {
        if (s == null) {
            throw new IllegalArgumentException("String must not be null.");
        }

        len = s.length();
        this.s = s;

        suffixes = new SuffixArray[len];
        for(int i = 0; i < len; i++) {
            suffixes[i] = new SuffixArray(i);
        }
        Arrays.sort(suffixes);
    }

    // returns index of ith sorted suffix
    public int index(int i) {
        if (i < 0 || i >= len) {
            throw new IllegalArgumentException("provided index doesnt exists");
        }
        return suffixes[i].getIndex();
    }

    // length of s
    public int length() {
        return len;
    }

    private class SuffixArray implements Comparable<SuffixArray> {
        private int beginIndex;

        public SuffixArray(int index) {
            this.beginIndex = index;
        }

        @Override
        public int compareTo(SuffixArray that) {
            int thisLen = len - this.beginIndex;
            int thatLen = len - that.beginIndex;
            int lim = Math.min(thisLen, thatLen);
            for (int i = 0; i < lim; i++) {
                char c1 = this.getChar(i);
                char c2 = that.getChar(i);
                if (c1 != c2) {
                    return c1 - c2;
                }
            }
            return thisLen - thatLen;
        }

        public char getChar(int index) {
            return s.charAt(beginIndex + index);
        }

        public int getIndex() {
            return beginIndex;
        }

        @Override
        public String toString() {
            return "SuffixArray{" +
                    "beginIndex=" + beginIndex +
                    ", suffix=" + s +
                    '}';
        }
    }

    public static void main(String[] args) {
        CircularSuffixArray suffixArray = new CircularSuffixArray("ABRACADABRA!");
        StdOut.println(suffixArray.index(11));
    }
}
