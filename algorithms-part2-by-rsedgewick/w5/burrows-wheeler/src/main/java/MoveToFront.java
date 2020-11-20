package main.java;

import edu.princeton.cs.algs4.BinaryStdIn;
import edu.princeton.cs.algs4.BinaryStdOut;

public class MoveToFront {
    // 256 extended ASCII characters
    private final static int R = 256;

    // apply move-to-front encoding, reading from standard input and writing to standard output
    public static void encode() {
//      char[] input = {(char) 2, (char) 0, (char) 0, (char) 0, (char) 1, (char) 2, (char) 2, (char) 2, (char) 0, (char) 2, (char) 2, (char) 5};
        char[] input = BinaryStdIn.readString().toCharArray();

        Node root = getLinkedAsciiNode();

        for(int i = 0; i < input.length; i++) {
            char c = input[i];
            int counter = 0;
            Node node = root;
            Node prev = root;
            while (node != null) {
                if(node.value == c) {
                    BinaryStdOut.write(counter);
                    if(node == root) {
                        break;
                    }
                    prev.next = node.next;
                    node.next = root;
                    root = node;
                    break;
                }
                prev = node;
                node = node.next;
                counter++;
            }
        }
        BinaryStdOut.close();
    }

    // apply move-to-front decoding, reading from standard input and writing to standard output
    public static void decode() {
//        char[] input = {(char) 2, (char) 1, (char) 0, (char) 0, (char) 2, (char) 2, (char) 0, (char) 0, (char) 2, (char) 1, (char) 0, (char) 5};
        char[] input = BinaryStdIn.readString().toCharArray();

        Node root = getLinkedAsciiNode();

        for(int i = 0; i < input.length; i++) {
            char c = input[i];
            int counter = 0;
            Node node = root;
            Node prev = root;
            while (node != null) {
                if(counter == c) {
                    BinaryStdOut.write(node.value);
                    if(node == root) {
                        break;
                    }
                    prev.next = node.next;
                    node.next = root;
                    root = node;
                    break;
                }
                prev = node;
                node = node.next;
                counter++;
            }
        }
        BinaryStdOut.close();
    }

    private static class Node {
        private char value;
        private Node next;

        public Node(char v) {
            this.value = v;
        }
    }

    private static Node getLinkedAsciiNode() {
        Node root = new Node('\0');
        Node current = root;
        for (int i = 1; i < R; i++) {
            current.next = new Node((char) i);
            current = current.next;
        }
        return root;
    }

    // if args[0] is "-", apply move-to-front encoding
    // if args[0] is "+", apply move-to-front decoding
    public static void main(String[] args) {
        if(args[0].equals("-")) {
            MoveToFront.encode();
        } else if(args[0].equals("+")) {
            MoveToFront.decode();
        }
    }
}
