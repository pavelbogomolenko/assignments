package main.java;

import java.util.Iterator;

public class ArrayQueue<Item> implements Iterable<Item> {
    private int head = -1;
    private int tail = -1;
    private int capacity;
    private Item[] q;
    private int n = 0;

    public ArrayQueue(int cap) {
        capacity = cap;
        q = (Item[])new Object[capacity];
    }

    public void enqueue(Item item) {
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
        n--;
        Item item = q[head++];
        if(n == q.length / 4) {
            resize(q.length / 2);
        }

        return item;
    }

    public int size() {
        return n;
    }

    private void resize(int newCapacity) {
        Item[] qCopy = (Item[])new Object[newCapacity];
        int qCopyHead = 0;
        for(int i = head; i <= tail; i++) {
            qCopy[qCopyHead++] = q[i];
        }
        head = 0;
        tail = qCopyHead - 1;
        q = qCopy;
        capacity = newCapacity;
    }

    @Override
    public Iterator<Item> iterator() {
        return new ArrayQueueIterator();
    }

    private class ArrayQueueIterator<Item> implements Iterator<Item> {
        private int current = head;

        @Override
        public boolean hasNext() {
            return current > -1 && current < capacity;
        }

        @Override
        public Item next() {
            return (Item) q[current++];
        }
    }
}
