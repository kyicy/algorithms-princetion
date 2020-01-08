import java.util.Iterator;
import java.util.NoSuchElementException;

public class Deque<Item> implements Iterable<Item> {
    private class Node {
        final private Item item;
        private Node prev;
        private Node next;

        Node(Item item) {
            this.item = item;
        }

        public Item getItem() {
            return item;
        }

        public Node getPrev() {
            return prev;
        }

        public void setPrev(Node prev) {
            this.prev = prev;
        }

        public Node getNext() {
            return next;
        }

        public void setNext(Node next) {
            this.next = next;
        }
    }

    final private Node head;
    final private Node tail;
    private int size;

    // Construct an empty deque
    public Deque() {
        this.head = new Node(null);
        this.tail = new Node(null);
        this.head.setNext(tail);
        this.tail.setPrev(head);
    }

    // Is the deque empty?
    public boolean isEmpty() {
        return this.size == 0;
    }

    // return the number of items on the deque
    public int size() {
        return size;
    }

    // add the item to the front
    public void addFirst(Item item) {
        if (item == null) {
            throw new IllegalArgumentException();
        }
        Node node = new Node(item);
        node.setNext(this.head.getNext());
        node.setPrev(this.head);
        this.head.getNext().setPrev(node);
        this.head.setNext(node);
        this.size++;
    }

    // add the item to the end
    public void addLast(Item item) {
        if (item == null) {
            throw new IllegalArgumentException();
        }
        Node node = new Node(item);
        node.setNext(this.tail);
        node.setPrev(this.tail.getPrev());
        this.tail.getPrev().setNext(node);
        this.tail.setPrev(node);
        this.size++;
    }

    // remove and return the item from the front
    public Item removeFirst() {
        if (this.size == 0) {
            throw new NoSuchElementException();
        }
        Node node = this.head.getNext();
        this.head.setNext(node.getNext());
        this.head.getNext().setPrev(this.head);
        this.size--;
        return node.getItem();

    }

    // remove and return the item from the tail
    public Item removeLast() {
        if (this.size == 0) {
            throw new NoSuchElementException();
        }
        Node node = this.tail.getPrev();
        this.tail.setPrev(node.getPrev());
        this.tail.getPrev().setNext(this.tail);
        this.size--;
        return node.getItem();
    }

    // return an iterator over items in order from front to back
    @Override
    public Iterator<Item> iterator() {
        return new HeadFirstIterator();
    }

    private class HeadFirstIterator implements Iterator<Item> {
        private Node curr = head;

        @Override
        public boolean hasNext() {
            return curr.getNext() != tail;
        }

        @Override
        public Item next() {
            if (!hasNext()) {
                throw new NoSuchElementException();
            }
            curr = curr.getNext();
            return curr.getItem();
        }

        @Override
        public void remove() {
            throw new UnsupportedOperationException();
        }
    }

    public static void main(String[] args) {
        // intentionally left blank
    }

}
