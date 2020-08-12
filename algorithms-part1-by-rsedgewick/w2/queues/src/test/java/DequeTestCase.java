import main.java.Deque;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;


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
}
