package main.java;

public class IntArrayQueue {
    private int head = -1;
    private int tail = -1;
    private int capacity;
    private static int q[];
    private int n = 0;

    public IntArrayQueue(int cap) {
        capacity = cap;
        q = new int[capacity];
    }

    public void enqueue(int item) {
        if(tail == capacity - 1) {
            throw new IndexOutOfBoundsException("queue is full");
        }
        q[++tail] = item;
        if(tail == 0) {
            head = tail;
        }
        n++;
    }

    public int dequeue() {
        if(head == -1) {
            throw new IndexOutOfBoundsException("queue is empty");
        }
        n--;
        return q[++head];
    }

    public int size() {
        return n;
    }

    public void displayQueue() {
        for(int i = head; i <= tail; i++) {
            System.out.println(q[i]);
        }
    }
}
