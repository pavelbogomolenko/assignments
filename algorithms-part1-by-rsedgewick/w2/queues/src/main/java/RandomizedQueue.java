package main.java;

import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdRandom;

import java.util.Iterator;
import java.util.NoSuchElementException;

public class RandomizedQueue<Item> implements Iterable<Item> {

    private int head = -1;
    private int tail = -1;
    private int capacity = 2;
    private Item[] q;
    private int n = 0;

    public RandomizedQueue() {
        q = (Item[])new Object[capacity];
    }

    public void enqueue(Item item) {
        if(item == null) {
            throw new IllegalArgumentException("item must not be null");
        }
        if(tail == capacity - 1) {
            resize(capacity * 2);
        }
        q[++tail] = item;
        if(tail == 0) {
            head = tail;
        }
        n++;
    }

    public Item dequeue() {
        if(head == -1) {
            throw new IndexOutOfBoundsException("queue is empty");
        }
        int randomIndex = StdRandom.uniform(head, tail + 1);
        Item randomItem = q[randomIndex];
        while (randomItem == null) {
            randomIndex = StdRandom.uniform(head, tail + 1);
            randomItem = q[randomIndex];
        }
        Item item = q[randomIndex];
        if(randomIndex == head) {
            Item headItem = null;
            while(headItem == null && head <= tail) {
                headItem = q[++head];
            }
        }
        if(randomIndex == tail) {
            Item tailItem = null;
            while(tailItem == null && tail >= head) {
                tailItem = q[--tail];
            }
        }
        q[randomIndex] = null;
        n--;
        if(n == q.length / 4) {
            resize(q.length / 2);
        }

        return item;
    }

    public Item sample() {
        if(isEmpty()) {
            throw new NoSuchElementException();
        }
        Item item = null;
        while(item == null) {
            item = q[StdRandom.uniform(head, tail + 1)];
        }
        return item;
    }

    public boolean isEmpty() {
        return n == 0;
    }

    public int size() {
        return n;
    }

    private void resize(int newCapacity) {
        Item[] qCopy = (Item[])new Object[newCapacity];
        int qCopyHead = 0;
        for(int i = head; i <= tail; i++) {
            Item itemToCopy = q[i];
            if(itemToCopy != null) {
                qCopy[qCopyHead++] = itemToCopy;
            }
        }
        head = 0;
        tail = qCopyHead - 1;
        q = qCopy;
        capacity = newCapacity;
    }

    @Override
    public Iterator<Item> iterator() {
        return new RandomizedQueueIterator();
    }

    private class RandomizedQueueIterator implements Iterator<Item> {
        private int current = 0;
        private final Item[] randomQ = (Item[])new Object[n];

        public RandomizedQueueIterator() {
            if(isEmpty()) {
                return;
            }
            int randomQCount = 0;
            for(int i = head; i <= tail; i++) {
                Item itemToCopy = q[i];
                if(itemToCopy != null) {
                    randomQ[randomQCount++] = itemToCopy;
                }
            }
            StdRandom.shuffle(randomQ);
        }

        @Override
        public boolean hasNext() {
            return current < randomQ.length;
        }

        @Override
        public Item next() {
            if(!hasNext()) {
                throw new NoSuchElementException();
            }
            return randomQ[current++];
        }
    }

    // unit testing (required)
    public static void main(String[] args) {
        RandomizedQueue<Integer> queue = new RandomizedQueue<>();

        queue.enqueue(10);
        queue.enqueue(20);
        queue.enqueue(30);
        StdOut.println(queue.size());
        StdOut.println(queue.dequeue());
        StdOut.println(queue.dequeue());
        StdOut.println(queue.size());
        StdOut.println(queue.dequeue());
        StdOut.println(queue.isEmpty());
    }
}
