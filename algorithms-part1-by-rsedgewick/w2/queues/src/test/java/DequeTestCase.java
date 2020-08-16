import main.java.Deque;

import org.junit.jupiter.api.Test;

import java.util.Iterator;
import java.util.NoSuchElementException;

import static org.junit.jupiter.api.Assertions.*;


public class DequeTestCase {

    @Test
    void shouldCreateEmptyDequeOfStrings() {
        Deque<String> deque = new Deque<String>();
        assertEquals(true, deque.isEmpty());
    }

    @Test
    void shouldCreateDequeOfStringsWithOneFirstElement() {
        Deque<String> deque = new Deque<String>();
        deque.addFirst("hello");
        assertEquals(1, deque.size());
    }

    @Test
    void shouldCreateDequeOfStringsWithOneLastElement() {
        Deque<String> deque = new Deque<String>();
        deque.addLast("world");
        assertEquals(1, deque.size());
    }

    @Test
    void shouldCreateDequeOfStringsWithTwoLastElement() {
        Deque<String> deque = new Deque<String>();
        deque.addLast("hello");
        deque.addLast("world");
        assertEquals(2, deque.size());
    }

    @Test
    void shouldCreateAndDeleteLastElementOFDequeOfStrings() {
        Deque<String> deque = new Deque<String>();
        deque.addLast("hello");
        deque.removeLast();
        assertEquals(0, deque.size());
    }

    @Test
    void shouldAddFirstAndRemoveLastElementInDequeOfInts() {
        Deque<Integer> deque = new Deque<Integer>();
        deque.addFirst(1);
        deque.removeLast();
        assertEquals(0, deque.size());
    }

    @Test
    void shouldAddLastAndRemoveFirstElementInDequeOfInts() {
        Deque<Integer> deque = new Deque<Integer>();
        deque.addLast(1);
        deque.removeFirst();
        assertEquals(0, deque.size());
    }

    @Test
    void shouldBeAbleToIterateDequeFromFrontToBack() {
        Deque<String> deque = new Deque<String>();
        deque.addFirst("not");
        deque.addFirst("or");
        deque.addLast("to");
        deque.addLast("be");
        deque.addFirst("be");
        deque.addFirst("to");

        String[] expectedResult = new String[]{"to", "be", "or", "not", "to", "be"};
        String[] actualResult = new String[6];
        int i = 0;
        for(String s: deque) {
            actualResult[i++] = s;
        }
        assertArrayEquals(expectedResult, actualResult);
    }

    @Test
    void shouldDepleteDequeFromFront() {
        Deque<Integer> deque = new Deque<>();

        deque.addFirst(20);
        deque.addFirst(10);
        deque.addLast(30);
        deque.addLast(40);

        deque.removeFirst();
        deque.removeFirst();
        deque.removeFirst();
        deque.removeFirst();

        assertEquals(0, deque.size());
    }

    @Test
    void shouldDepleteDequeFromBack() {
        Deque<Integer> deque = new Deque<>();

        deque.addFirst(20);
        deque.addFirst(10);
        deque.addLast(30);
        deque.addLast(40);

        deque.removeLast();
        deque.removeLast();
        deque.removeLast();
        deque.removeLast();

        assertEquals(0, deque.size());
    }

    @Test
    void iteratorShouldThrowAnExceptionWhenNoMoreItems() {
        Deque<String> queue = new Deque<>();

        assertThrows(NoSuchElementException.class, ()-> {
            Iterator<String> queueIterator = queue.iterator();
            queueIterator.next();
        });
    }

    @Test
    void iteratorShouldThrowAnExceptionWhenTruingToRemoveAnItem() {
        Deque<String> queue = new Deque<>();

        assertThrows(UnsupportedOperationException.class, ()-> {
            Iterator<String> queueIterator = queue.iterator();
            queueIterator.remove();
        });
    }

    @Test
    void shouldDepleteDequeFromBackInCorrectOrder() {
        Deque<Integer> deque = new Deque<>();

        deque.addLast(30);
        deque.addFirst(20);
        deque.addLast(40);
        deque.addFirst(10);

        assertEquals(40, deque.removeLast());
        assertEquals(30, deque.removeLast());
        assertEquals(20, deque.removeLast());
        assertEquals(10, deque.removeLast());

        assertEquals(0, deque.size());
    }

    @Test
    void shouldDepleteDequeInCorrectOrder() {
        Deque<Integer> deque = new Deque<>();

        deque.addLast(30);
        deque.addFirst(20);
        deque.addLast(40);
        deque.addFirst(10);

        assertEquals(40, deque.removeLast());
        assertEquals(10, deque.removeFirst());
        assertEquals(30, deque.removeLast());
        assertEquals(20, deque.removeFirst());

        assertEquals(0, deque.size());
    }

    @Test
    void shouldDepleteDequeInCorrectOrderAndAddNewItem() {
        Deque<Integer> deque = new Deque<>();

        deque.addLast(30);
        deque.addFirst(20);
        deque.addLast(40);
        deque.addFirst(10);

        assertEquals(40, deque.removeLast());
        assertEquals(10, deque.removeFirst());
        assertEquals(30, deque.removeLast());
        assertEquals(20, deque.removeFirst());

        deque.addFirst(70);
        deque.addLast(80);
        deque.addFirst(90);

        assertEquals(90, deque.removeFirst());

        assertEquals(2, deque.size());
    }
}
