package main.java;

import edu.princeton.cs.algs4.StdOut;

import java.util.Iterator;
import java.util.NoSuchElementException;

public class Deque<Item> implements Iterable<Item> {
    private Node<Item> first = null;
    private Node<Item> last = null;
    private Node<Item> oldlast = null;
    private int n = 0;

    private static class Node<Item> {
        Item item;
        Node<Item> next;
    }

    // construct an empty deque
    public Deque() {

    }

    // is the deque empty?
    public boolean isEmpty() {
        return n == 0;
    }

    // return the number of items on the deque
    public int size() {
        return n;
    }

    // add the item to the front
    public void addFirst(Item item) {
        if(item == null) {
            throw new IllegalArgumentException("item must not be null");
        }
        Node<Item> oldfist = first;
        first = new Node<>();
        first.item = item;
        first.next = oldfist;
        if(isEmpty()) {
            last = first;
        }
        n++;
    }

    // add the item to the back
    public void addLast(Item item) {
        if(item == null) {
            throw new IllegalArgumentException("item must not be null");
        }
        oldlast = last;
        last = new Node<>();
        last.item = item;
        if(isEmpty()) {
            first = last;
        } else {
            oldlast.next = last;
        }
        n++;
    }

    // remove and return the item from the front
    public Item removeFirst() {
        if(isEmpty()) {
            throw new NoSuchElementException();
        }

        Item firstitem = first.item;
        first = first.next;
        n--;
        if(isEmpty()) {
            last = null;
        }
        return firstitem;
    }

    // remove and return the item from the back
    public Item removeLast() {
        if(isEmpty()) {
            throw new NoSuchElementException();
        }

        Item lastitem = last.item;
        if(isEmpty()) {
            oldlast.next = null;
        }
        last = oldlast;
        n--;

        return lastitem;
    }

    // return an iterator over items in order from front to back
    public Iterator<Item> iterator() {
        return new QueueIterator();
    }

    private class QueueIterator implements Iterator<Item> {
        private Node<Item> current = first;

        public boolean hasNext() {
            return current != null;
        }

        public Item next() {
            if(!hasNext()) {
                throw new NoSuchElementException();
            }
            Item item = current.item;
            current = current.next;
            return item;
        }
    }

    // unit testing (required)
    public static void main(String[] args) {
        Deque<String> deque = new Deque<String>();
        deque.addFirst("not");
        deque.addFirst("or");
        deque.addLast("to");
        deque.addLast("be");
        deque.addFirst("be");
        deque.addFirst("to");

        StdOut.println(deque.isEmpty());
        StdOut.println(deque.removeFirst());
        StdOut.println(deque.removeLast());
        StdOut.println(deque.size());
    }
}
