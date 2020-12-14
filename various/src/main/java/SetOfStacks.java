package main.java;

public class SetOfStacks<Item> {
    private final static int STACK_CAPACITY = 3;
    private int stackIndex;
    private Stack<Item>[] stackArray;

    public SetOfStacks() {
        this.stackIndex = 0;

        stackArray = (Stack<Item>[]) new Stack[1];
        stackArray[this.stackIndex] = new Stack();
    }

    public void push(Item item) {
        if (stackArray[this.stackIndex].size() >= STACK_CAPACITY) {
            resize(stackArray.length * 2);
            this.stackIndex++;
        }
        stackArray[this.stackIndex].push(item);
    }

    public Item pop() {
        Item item = null;
        try {
            item  = stackArray[this.stackIndex].pop();
        } catch (IndexOutOfBoundsException e) {
            if(this.stackIndex > 0) {
                item  = stackArray[--this.stackIndex].pop();
            } else {
                throw new IndexOutOfBoundsException("SetOfStacks is depleted");
            }
        }
        return item;
    }

    public int size() {
        return stackIndex * STACK_CAPACITY + stackArray[this.stackIndex].size();
    }

    private void resize(int capacity) {
        Stack<Item>[] newStackArray = (Stack<Item>[]) new Stack[capacity];
        for(int i = 0; i < capacity; i++) {
            if(i < stackArray.length) {
                newStackArray[i] = stackArray[i];
            } else {
                newStackArray[i] = new Stack();
            }
        }
        stackArray = newStackArray;
    }

    private class Stack<Item> {
        private Node<Item> head;
        private int size;

        public Stack() {
            this.size = 0;
        }

        public void push(Item item) {
            if(head == null) {
                head = new Node<>(item);
            }

            Node node = new Node<>(item);
            node.next = head;
            head = node;

            size++;
        }

        public Item pop() {
            if(size == 0) {
                throw new IndexOutOfBoundsException("Stack is empty");
            }

            Item item = head.value;
            head = head.next;
            size--;

            return item;
        }

        public int size() {
            return size;
        }
    }

    private class Node<Item> {
        private Item value;
        private Node<Item> next;

        public Node(Item value) {
            this.value = value;
        }

        public Node(Item value, Node<Item> next) {
            this.value = value;
            this.next = next;
        }
    }

    public static void main(String args[]) {
        SetOfStacks<Integer> setOfStacks = new SetOfStacks<>();

        System.out.println("size: " + setOfStacks.size());
        setOfStacks.push(1);
        setOfStacks.push(2);
        System.out.println("size: " + setOfStacks.size());
        setOfStacks.push(3);
        setOfStacks.push(4);
        setOfStacks.push(5);
        System.out.println("size: " + setOfStacks.size());
        System.out.println("pop: " + setOfStacks.pop());
        System.out.println("pop: " + setOfStacks.pop());
        System.out.println("size: " + setOfStacks.size());
        setOfStacks.push(6);
        System.out.println("size: " + setOfStacks.size());
        System.out.println("pop: " + setOfStacks.pop());
        System.out.println("pop: " + setOfStacks.pop());
        System.out.println("pop: " + setOfStacks.pop());
        System.out.println("pop: " + setOfStacks.pop());
        System.out.println("size: " + setOfStacks.size());
        System.out.println("pop: " + setOfStacks.pop());
    }
}
