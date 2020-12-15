package main.java;

public class LinkedList {

    private Node left;

    public void partition(Node node, int partitionValue) {
        Node currentLeft = null;
        Node currentRight = null;
        Node right = null;

        for(Node current = node; current != null; current = current.next) {
            if(current.value < partitionValue) {
                if(currentLeft != null) {
                    currentLeft.next = new Node(current.value);
                    currentLeft = currentLeft.next;
                } else {
                    left = new Node(current.value);
                    currentLeft = left;
                }
            } else {
                if(currentRight != null) {
                    currentRight.next = new Node(current.value);
                    currentRight = currentRight.next;
                } else {
                    right = new Node(current.value);
                    currentRight = right;
                }
            }
        }
        currentLeft.next = right;
    }

    public Node reverse(Node node) {
        Node prev = null;
        Node next = null;
        Node current = node;
        while (current != null) {
            next = current.next;
            current.next = prev;
            prev = current;
            current = next;
        }
        return prev;
    }

    public boolean detectLoop(Node node) {
        Node slow = node;
        Node fast = node;
        while (slow != null && fast != null && fast.next != null) {
            slow = slow.next;
            fast = fast.next.next;
            if(slow == fast) {
                return true;
            }
        }
        return false;
    }

    public Node findMid(Node node) {
        Node slow = node;
        Node fast = node;
        while (slow != null && fast != null && fast.next != null) {
            slow = slow.next;
            fast = fast.next.next;
        }
        return slow;
    }

    public void printLL() {
        printLL(left);
    }

    private void printLL(Node node) {
        for(Node x = node; x != null; x = x.next) {
            System.out.printf("%d -> ", x.value);
        }
        System.out.print("\n");
    }

    private Node initLinkedList() {
        return new Node(3, new Node(5, new Node(8, new Node(5, new Node(10, new Node(2, new Node(1)))))));
    }

    private Node initLinkedListWithLoop() {
        Node loopNode = new Node(1, new Node(2, new Node(3)));
        loopNode.next.next.next = loopNode;
        return loopNode;
    }

    private class Node {
        private int value;
        private Node next;
        public Node(int value) {
            this.value = value;
        }

        public Node(int value, Node next) {
            this.value = value;
            this.next = next;
        }
    }

    public static void main(String args[]) {
        LinkedList llPart = new LinkedList();
        Node node = llPart.initLinkedList();
        llPart.printLL(node);
        System.out.println("partition");
        llPart.partition(node, 5);
        llPart.printLL();

        System.out.println("reverse");
        Node reversedNode = llPart.reverse(node);
        llPart.printLL(reversedNode);


        Node loopNode = llPart.initLinkedListWithLoop();
//        Node loopNode = llPart.initLinkedList();
        System.out.println("loop detection");
        System.out.println("loop detected: " + llPart.detectLoop(loopNode));

        System.out.println("mid of the list:");
        llPart.printLL(llPart.initLinkedList());
        System.out.println(llPart.findMid(llPart.initLinkedList()).value);
    }
}
