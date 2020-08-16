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

    /**
     * Randomly insert new item, so we dont need to shuffle later
     *
     * @param item
     */
    public void enqueue(Item item) {
        if(item == null) {
            throw new IllegalArgumentException("item must not be null");
        }
        if(tail == capacity - 1) {
            resize(capacity <= 0 ? 2 : capacity * 2);
        }
        if(tail++ > 0) {
            int randomIndex = StdRandom.uniform(head, tail + 1);
            Item randomItem = q[randomIndex];
            q[tail] = randomItem;
            q[randomIndex] = item;
        } else {
            q[tail] = item;
        }
        if(tail == 0) {
            head = tail;
        }
        n++;
    }

    public Item dequeue() {
        if(head == -1) {
            throw new NoSuchElementException("queue is empty");
        }
        Item itemToRemove = q[head++];
        n--;
        if(n == q.length / 4) {
            resize(q.length / 2);
        }

        return itemToRemove;
    }

    public Item sample() {
        if(isEmpty()) {
            throw new NoSuchElementException();
        }
        return q[StdRandom.uniform(head, tail + 1)];
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
            qCopy[qCopyHead++] = itemToCopy;
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
        private int current = head;

        public RandomizedQueueIterator() {
            if(isEmpty()) {
                return;
            }
            for(int i = head; i <= tail; i++) {
                int r = StdRandom.uniform(i, tail + 1);
                Item randomItem = q[r];
                q[r] = q[i];
                q[i] = randomItem;
            }
        }

        @Override
        public boolean hasNext() {
            return current > - 1 && current <= tail;
        }

        @Override
        public Item next() {
            if(!hasNext()) {
                throw new NoSuchElementException();
            }
            return q[current++];
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
