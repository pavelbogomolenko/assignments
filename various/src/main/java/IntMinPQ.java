package main.java;

public class IntMinPQ {
    private final int[] a;
    private int size;

    public IntMinPQ(int n) {
        a = new int[n];
        size = 0;
    }

    public int size() {
        return size;
    }

    public void enqueue(int item) {
        a[size++] = item;
        heapify(size - 1);
    }

    public int pop() {
        int value = a[0];
        swap(0, --size);

        reheapify(0);

        return value;

    }

    private void reheapify(int index) {
        if(index == size) {
            return;
        }

        int left = getLeft(index);
        int right = getRight(index);

        if(right < size && a[index] > a[right]) {
            swap(index, right);
            reheapify(right);
        }

        if(left < size && a[index] > a[left]) {
            swap(index, left);
            reheapify(left);
        }
    }

    private int getParent(int index) {
        return (index - 1) / 2;
    }

    private int getLeft(int index) {
        return (2 * index) + 1;
    }

    private int getRight(int index) {
        return (2 * index) + 2;
    }

    private void heapify(int index) {
        if(index == 0) {
            return;
        }

        int parent = getParent(index);
        if(a[parent] > a[index]) {
            swap(parent, index);
            int left = getLeft(parent);
            int right = getRight(parent);
            if((left < size && right < size) && a[left] > a[right]) {
                swap(left, right);
            }
            heapify(parent);
        } else {
            int left = getLeft(parent);
            int right = getRight(parent);
            if((left < size && right < size) && a[left] > a[right]) {
                swap(left, right);
            }
        }
    }

    private void swap(int first, int second) {
        int tmpFirst = a[first];
        a[first] = a[second];
        a[second] = tmpFirst;
    }

    private void printQueue() {
        for(int i = 0; i < size; i++) {
            System.out.printf("%d -> ", a[i]);
        }
        System.out.printf("\n");
    }

    public static void main(String args[]) {
        IntMinPQ minPQ = new IntMinPQ(10);
        minPQ.enqueue(10);
        minPQ.enqueue(11);
        minPQ.enqueue(12);
        minPQ.enqueue(3);
        minPQ.enqueue(1);
        minPQ.enqueue(2);
        minPQ.printQueue();
        System.out.println(minPQ.pop());
        minPQ.printQueue();
        System.out.println(minPQ.pop());
        minPQ.printQueue();
        System.out.println(minPQ.pop());
        minPQ.printQueue();
        minPQ.enqueue(3);
        minPQ.enqueue(1);
        minPQ.enqueue(2);
        minPQ.printQueue();
    }
}