package main.java;

import edu.princeton.cs.algs4.BinaryStdIn;
import edu.princeton.cs.algs4.BinaryStdOut;

import java.util.Arrays;
import java.util.HashMap;

public class BurrowsWheeler {
    // apply Burrows-Wheeler transform,
    // reading from standard input and writing to standard output
    public static void transform() {
        String s = BinaryStdIn.readString();

        CircularSuffixArray csa = new CircularSuffixArray(s);
        for(int i = 0; i < csa.length(); i++) {
            int index = csa.index(i);
            if(index == 0) {
                BinaryStdOut.write(i);
                BinaryStdOut.write(s.charAt(csa.length() - 1));
            } else {
                BinaryStdOut.write(s.charAt(index - 1));
            }
        }
        BinaryStdOut.close();
    }

    // apply Burrows-Wheeler inverse transform,
    // reading from standard input and writing to standard output
    public static void inverseTransform() {
        int first = BinaryStdIn.readInt();
        char[] transformedChar = BinaryStdIn.readString().toCharArray();
        int N = transformedChar.length;
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

        for(int n = first, i = 0; n != last; n = next[n], i++) {
            BinaryStdOut.write(sortedTransformedChar[n]);
        }
        BinaryStdOut.write(transformedChar[first]);
        BinaryStdOut.close();
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
