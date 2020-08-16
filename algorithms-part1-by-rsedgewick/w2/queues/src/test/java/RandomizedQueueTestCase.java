import main.java.RandomizedQueue;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.NoSuchElementException;

import static org.junit.jupiter.api.Assertions.*;

public class RandomizedQueueTestCase {

    @Test
    void testRandomizedQueue() {
        RandomizedQueue<Integer> randomizedQueue = new RandomizedQueue();

        randomizedQueue.enqueue(5);
        randomizedQueue.enqueue(10);
        randomizedQueue.enqueue(20);
        randomizedQueue.enqueue(30);
        randomizedQueue.enqueue(40);

        randomizedQueue.dequeue();
        randomizedQueue.dequeue();

        assertEquals(3, randomizedQueue.size());
    }

    @Test
    void testRandomizedQueueIterator() {
        RandomizedQueue<Integer> randomizedQueue = new RandomizedQueue();

        randomizedQueue.enqueue(10);
        randomizedQueue.enqueue(20);
        randomizedQueue.enqueue(30);
        randomizedQueue.enqueue(40);

        ArrayList<Integer> expectedArray = new ArrayList<>(Arrays.asList(10, 20, 30, 40));
        for(int i: randomizedQueue) {
            assertTrue(expectedArray.contains(i));
        }
    }

    @Test
    void testRandomizedQueueIteratorAfterDequeue() {
        RandomizedQueue<Integer> randomizedQueue = new RandomizedQueue();

        randomizedQueue.enqueue(10);
        randomizedQueue.enqueue(20);
        randomizedQueue.enqueue(30);
        randomizedQueue.enqueue(40);
        randomizedQueue.enqueue(50);

        System.out.println(randomizedQueue.dequeue());
        System.out.println(randomizedQueue.dequeue());
        System.out.println(randomizedQueue.dequeue());

        ArrayList<Integer> expected = new ArrayList<Integer>(Arrays.asList(10, 20, 30, 40, 50));
        int[] actualArray = new int[randomizedQueue.size()];
        int iter = 0;
        for(int i: randomizedQueue) {
            actualArray[iter++] = i;
        }
        System.out.println(Arrays.toString(actualArray));
        assertEquals(2, randomizedQueue.size());
        assertTrue(expected.contains(actualArray[0]));
    }

    @Test
    void testDoubleCapacityOfRandomizedQueueWhenFull() {
        int initialCapacity = 2;
        RandomizedQueue<Integer> randomizedQueue = new RandomizedQueue();

        randomizedQueue.enqueue(10);
        randomizedQueue.enqueue(20);
        randomizedQueue.enqueue(30);
        randomizedQueue.enqueue(40);
        randomizedQueue.enqueue(50);

        assertEquals(1 + initialCapacity * 2, randomizedQueue.size());
    }

    @Test
    void testShrinkCapacityOfRandomizedQueue() {
        int initialCapacity = 4;
        RandomizedQueue<Integer> randomizedQueue = new RandomizedQueue();

        randomizedQueue.enqueue(10);
        randomizedQueue.enqueue(20);
        randomizedQueue.enqueue(30);
        randomizedQueue.enqueue(40);
        randomizedQueue.dequeue();
        randomizedQueue.dequeue();
        randomizedQueue.dequeue();

        assertEquals(1, randomizedQueue.size());
    }

    @Test
    void testShrinkCapacityIteratorOfRandomizedQueue() {
        int initialCapacity = 5;
        RandomizedQueue<Integer> randomizedQueue = new RandomizedQueue();

        randomizedQueue.enqueue(10);
        randomizedQueue.enqueue(20);
        randomizedQueue.enqueue(30);
        randomizedQueue.enqueue(40);
        randomizedQueue.enqueue(50);
        randomizedQueue.dequeue();
        randomizedQueue.dequeue();
        randomizedQueue.dequeue();
        randomizedQueue.dequeue();
        randomizedQueue.dequeue();
        randomizedQueue.enqueue(60);

        int[] expectedArray = new int[] {60};
        int[] actualArray = new int[randomizedQueue.size()];
        int iter = 0;
        for(int i: randomizedQueue) {
            actualArray[iter++] = i;
        }
        assertArrayEquals(expectedArray, actualArray);
    }

    @Test
    void testDepleteAndAddItemRandomizedQueue() {
        RandomizedQueue<Integer> randomizedQueue = new RandomizedQueue();

        randomizedQueue.enqueue(10);
        randomizedQueue.enqueue(20);
        randomizedQueue.enqueue(40);
        randomizedQueue.enqueue(50);
        randomizedQueue.enqueue(60);
        System.out.println(randomizedQueue.dequeue());
        System.out.println(randomizedQueue.dequeue());
        System.out.println(randomizedQueue.dequeue());
        System.out.println(randomizedQueue.dequeue());
        System.out.println(randomizedQueue.dequeue());
        assertTrue(randomizedQueue.isEmpty());
        randomizedQueue.enqueue(30);

        int[] expectedArray = new int[] {30};
        int[] actualArray = new int[randomizedQueue.size()];
        int iter = 0;
        for(int i: randomizedQueue) {
            actualArray[iter++] = i;
        }
        assertArrayEquals(expectedArray, actualArray);
        assertEquals(1, randomizedQueue.size());
    }

    @Test
    void shouldReturnRandomItem() {
        RandomizedQueue<String> queue = new RandomizedQueue<>();
        queue.enqueue("a");
        queue.enqueue("b");
        queue.enqueue("c");

        ArrayList<String> expected = new ArrayList<String>(Arrays.asList("a", "b", "c"));

        String resultItem = queue.sample();
        assertTrue(expected.contains(resultItem));
    }

    @Test
    void shouldIterateInUniformlyRandomOrder() {
        RandomizedQueue<String> queue = new RandomizedQueue<>();
        queue.enqueue("a");
        queue.enqueue("b");
        queue.enqueue("c");
        queue.enqueue("d");
        queue.enqueue("e");
        queue.enqueue("f");

        ArrayList<String> expectedArray = new ArrayList<>(Arrays.asList("b", "a", "c", "d", "e", "f"));
        for (String s: queue) {
            assertTrue(expectedArray.contains(s));
        }
    }

    @Test
    void iteratorShouldThrowAnExceptionWhenNoMoreItems() {
        RandomizedQueue<String> queue = new RandomizedQueue<>();

        assertThrows(NoSuchElementException.class, ()-> {
            Iterator<String> queueIterator = queue.iterator();
            queueIterator.next();
        });
    }
}
