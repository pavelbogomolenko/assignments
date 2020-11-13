package main.java;

import edu.princeton.cs.algs4.StdOut;

import java.util.Arrays;

public class CircularSuffixArray {
    private final int len;
    private final String origString;
    private final SuffixArray[] suffixes;

    // circular suffix array of s
    public CircularSuffixArray(String s) {
        len = s.length();
        origString = s;
        char[] origStringChars = new char[len];
        suffixes = new SuffixArray[len];

        for (int i = 0; i < len; i++) {
            origStringChars[i] = origString.charAt(i);
        }

        for(int i = 0; i < len; i++) {
            suffixes[i] = new SuffixArray(i, origStringChars);
        }
        Arrays.sort(suffixes);
    }

    // returns index of ith sorted suffix
    public int index(int i) {
        return suffixes[i].getIndex();
    }

    private class SuffixArray implements Comparable<SuffixArray> {
        private int beginIndex;
        private final char[] suffix;

        public SuffixArray(int index, char[] value) {
            this.beginIndex = index;
            suffix = value;
        }

        @Override
        public int compareTo(SuffixArray that) {
            int thisLen = this.suffix.length - this.beginIndex;
            int thatLen = that.suffix.length - that.beginIndex;
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
            return suffix[beginIndex + index];
        }

        public int getIndex() {
            return beginIndex;
        }

        @Override
        public String toString() {
            return "SuffixArray{" +
                    "beginIndex=" + beginIndex +
                    ", suffix=" + Arrays.toString(suffix) +
                    '}';
        }
    }

    public static void main(String[] args) {
        CircularSuffixArray suffixArray = new CircularSuffixArray("ABRACADABRA!");
        StdOut.println(suffixArray.index(11));
    }
}
