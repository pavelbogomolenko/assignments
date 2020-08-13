import main.java.ArrayQueue;
import main.java.IntArrayQueue;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class IntArrayQueueTestCase {

    @Test
    void testIntArrayQueue() {
        IntArrayQueue intArrayQueue = new IntArrayQueue(10);

        intArrayQueue.enqueue(5);
        intArrayQueue.enqueue(10);
        intArrayQueue.enqueue(20);
        intArrayQueue.enqueue(30);

        intArrayQueue.dequeue();
        intArrayQueue.dequeue();

        assertEquals(2, intArrayQueue.size());
    }

    @Test
    void testArrayQueue() {
        ArrayQueue<Integer> arrayQueue = new ArrayQueue(10);

        arrayQueue.enqueue(5);
        arrayQueue.enqueue(10);
        arrayQueue.enqueue(20);
        arrayQueue.enqueue(30);
        arrayQueue.enqueue(40);

        arrayQueue.dequeue();
        arrayQueue.dequeue();

        assertEquals(3, arrayQueue.size());
    }

    @Test
    void testArrayQueueIterator() {
        ArrayQueue<Integer> arrayQueue = new ArrayQueue(4);

        arrayQueue.enqueue(10);
        arrayQueue.enqueue(20);
        arrayQueue.enqueue(30);
        arrayQueue.enqueue(40);

        int[] expectedArray = new int[] {10, 20, 30, 40};

        int[] actualArray = new int[4];
        int iter = 0;
        for(int i: arrayQueue) {
            actualArray[iter++] = i;
        }
        assertArrayEquals(expectedArray, actualArray);
    }

    @Test
    void testArrayQueueIteratorAfterDequeue() {
        ArrayQueue<Integer> arrayQueue = new ArrayQueue(5);

        arrayQueue.enqueue(10);
        arrayQueue.enqueue(20);
        arrayQueue.enqueue(30);
        arrayQueue.enqueue(40);
        arrayQueue.enqueue(50);

        arrayQueue.dequeue();
        arrayQueue.dequeue();
        arrayQueue.dequeue();

        int[] expectedArray = new int[] {40, 50};
        int[] actualArray = new int[arrayQueue.size()];
        int iter = 0;
        for(int i: arrayQueue) {
            actualArray[iter++] = i;
        }
        assertArrayEquals(expectedArray, actualArray);
    }

    @Test
    void testDoubleCapacityOfArrayQueueWhenFull() {
        int initialCapacity = 2;
        ArrayQueue<Integer> arrayQueue = new ArrayQueue(initialCapacity);

        arrayQueue.enqueue(10);
        arrayQueue.enqueue(20);
        arrayQueue.enqueue(30);
        arrayQueue.enqueue(40);
        arrayQueue.enqueue(50);

        assertEquals(1 + initialCapacity * 2, arrayQueue.size());
    }

    @Test
    void testShrinkCapacityOfArrayQueue() {
        int initialCapacity = 4;
        ArrayQueue<Integer> arrayQueue = new ArrayQueue(initialCapacity);

        arrayQueue.enqueue(10);
        arrayQueue.enqueue(20);
        arrayQueue.enqueue(30);
        arrayQueue.enqueue(40);
        arrayQueue.dequeue();
        arrayQueue.dequeue();
        arrayQueue.dequeue();

        assertEquals(1, arrayQueue.size());
    }

    @Test
    void testShrinkCapacityIteratorOfArrayQueue() {
        int initialCapacity = 5;
        ArrayQueue<Integer> arrayQueue = new ArrayQueue(initialCapacity);

        arrayQueue.enqueue(10);
        arrayQueue.enqueue(20);
        arrayQueue.enqueue(30);
        arrayQueue.enqueue(40);
        arrayQueue.enqueue(50);
        arrayQueue.dequeue();
        arrayQueue.dequeue();
        arrayQueue.dequeue();
        arrayQueue.dequeue();
        arrayQueue.enqueue(60);

        int[] expectedArray = new int[] {50, 60};
        int[] actualArray = new int[arrayQueue.size()];
        int iter = 0;
        for(int i: arrayQueue) {
            actualArray[iter++] = i;
        }
        assertArrayEquals(expectedArray, actualArray);
    }

    @Test
    void testDepleteAndAddItemArrayQueue() {
        int initialCapacity = 2;
        ArrayQueue<Integer> arrayQueue = new ArrayQueue(initialCapacity);

        arrayQueue.enqueue(10);
        arrayQueue.enqueue(20);
        arrayQueue.dequeue();
        arrayQueue.dequeue();
        arrayQueue.enqueue(30);

        int[] expectedArray = new int[] {30};
        int[] actualArray = new int[arrayQueue.size()];
        int iter = 0;
        for(int i: arrayQueue) {
            actualArray[iter++] = i;
        }
        assertArrayEquals(expectedArray, actualArray);
        assertEquals(1, arrayQueue.size());
    }
}
