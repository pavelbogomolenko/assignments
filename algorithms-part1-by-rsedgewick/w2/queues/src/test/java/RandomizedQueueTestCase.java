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

    @Test
    void testCourseraSequence() {
        RandomizedQueue<Integer> rq = new RandomizedQueue<Integer>();
        rq.enqueue(422);
        assertEquals(422, rq.dequeue());
    }

    @Test
    void testRandomizedQueuesOfDifferentType() {
        RandomizedQueue<Integer> intRq = new RandomizedQueue<Integer>();
        RandomizedQueue<String> stringRq = new RandomizedQueue<String>();
    }

    @Test
    void testCourseraSequenceOfOps1() {
        RandomizedQueue<Integer> rq = new RandomizedQueue<Integer>();
        rq.enqueue(45);
        assertEquals(45, rq.dequeue());
        rq.enqueue(43);
        assertEquals(43, rq.dequeue());
        rq.enqueue(48);
        assertEquals(48, rq.dequeue());
        assertEquals(0, rq.size());
        rq.enqueue(23);
        assertEquals(23, rq.dequeue());
        rq.enqueue(55);
        assertEquals(1, rq.size());
        assertEquals(55, rq.dequeue());
    }

    @Test
    void testCourseraSequenceOfOps2() {
        RandomizedQueue<Integer> rq = new RandomizedQueue<Integer>();
        rq.enqueue(45);
        assertEquals(45, rq.dequeue());
        rq.enqueue(48);
        rq.enqueue(43);;
        rq.enqueue(44);;
        System.out.println("dequeue: " + rq.dequeue());
        System.out.println("sample: " + rq.sample());
        rq.enqueue(45);;
        rq.enqueue(46);;
        rq.enqueue(47);;
        System.out.println("dequeue: " + rq.dequeue());
        System.out.println("dequeue: " + rq.dequeue());
        System.out.println("dequeue: " + rq.dequeue());

        for (int i: rq) {
            System.out.println("for:" + i);
        }
        System.out.println("sample: " + rq.sample());
        assertEquals(2, rq.size());
    }
}
