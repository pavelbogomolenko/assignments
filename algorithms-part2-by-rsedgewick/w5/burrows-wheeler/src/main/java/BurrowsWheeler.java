package main.java;

import edu.princeton.cs.algs4.BinaryStdIn;
import edu.princeton.cs.algs4.BinaryStdOut;
import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdOut;

import java.util.Arrays;
import java.util.HashMap;

public class BurrowsWheeler {
    // apply Burrows-Wheeler transform,
    // reading from standard input and writing to standard output
    public static void transform() {
        StringBuilder origStringBuilder = new StringBuilder();

        boolean nobin = true;
        while (!BinaryStdIn.isEmpty()) {
            origStringBuilder.append(BinaryStdIn.readChar());
            nobin = false;
        }

        String s;
        if(!nobin) {
            s = origStringBuilder.toString();
        } else {
            In in = new In("abra.txt");
            s = in.readAll();
        }
        CircularSuffixArray csa = new CircularSuffixArray(s);
        StringBuilder sortedSuffixBuilder = new StringBuilder();
        int first = -1;
        for(int i = 0; i < csa.length(); i++) {
            int index = csa.index(i);
            if(index == 0) {
                first = i;
                sortedSuffixBuilder.append(s.charAt(csa.length() - 1));
            } else {
                sortedSuffixBuilder.append(s.charAt(index - 1));
            }
        }
        BinaryStdOut.write(first);
        BinaryStdOut.write(sortedSuffixBuilder.toString());
        BinaryStdOut.close();
    }

    // apply Burrows-Wheeler inverse transform,
    // reading from standard input and writing to standard output
    public static void inverseTransform() {
        char[] transformedChar = {'A', 'R', 'D', '!', 'R', 'C', 'A', 'A', 'A', 'A', 'B', 'B'};
        int N = transformedChar.length;
        int first = 3;
        int last = -1;
        char lastChar = '\0';

        HashMap<String, Integer> charHashMap = new HashMap<>();
        int iter = 0;
        for(char c: transformedChar) {
            String key = "" + c;
            if(iter == first) {
                lastChar = c;
            }
            if(!charHashMap.containsKey(key)) {
                charHashMap.put(key, 0);
                charHashMap.put(key + "0", iter);
            } else {
                int val = charHashMap.get(key) + 1;
                charHashMap.put(key, val);
                charHashMap.put(c + Integer.toString(val), iter);
            }
            iter++;
        }

        // use key-indexed counting sorting
        char[] sortedTransformedChar = Arrays.copyOf(transformedChar, N);
        Arrays.sort(sortedTransformedChar);

        HashMap<Character, Integer> usedChars = new HashMap<>();
        int[] next = new int[N];
        iter = 0;
        for(char c: sortedTransformedChar) {
            if(c == lastChar) {
                last = iter;
            }
            int nextCharCount = charHashMap.get("" + c);
            if(nextCharCount == 0) {
                next[iter] = charHashMap.get(c + "0");
            } else {
                if(!usedChars.containsKey(c)) {
                    next[iter] = charHashMap.get(c + "0");
                    usedChars.put(c, 0);
                } else {
                    int nextAvailableIndex = usedChars.get(c) + 1;
                    next[iter] = charHashMap.get(c + Integer.toString(nextAvailableIndex));
                    usedChars.put(c, nextAvailableIndex);
                }
            }
            iter++;
        }

        char[] reconstructedChar = new char[N];
        for(int n = 3, i = 0; n != last; n = next[n], i++) {
            reconstructedChar[i] = sortedTransformedChar[n];
        }
        reconstructedChar[reconstructedChar.length - 1] = transformedChar[first];
        StdOut.println(Arrays.toString(reconstructedChar));
    }

    // if args[0] is "-", apply Burrows-Wheeler transform
    // if args[0] is "+", apply Burrows-Wheeler inverse transform
    public static void main(String[] args) {
        if(args[0].equals("-")) {
            BurrowsWheeler.transform();
        } else if(args[0].equals("+")) {
            BurrowsWheeler.inverseTransform();
        }
    }
}
