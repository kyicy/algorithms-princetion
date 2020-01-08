import java.util.Iterator;
import java.util.NoSuchElementException;

import edu.princeton.cs.algs4.StdRandom;

public class RandomizedQueue<Item> implements Iterable<Item> {
    private Item[] array;
    private int lastIndex;

    public RandomizedQueue() {
        // Type item is only known at runtime
        Item[] a = (Item[]) new Object[1];
        array = a;
        lastIndex = -1;
    }

    public boolean isEmpty() {
        return size() == 0;
    }

    public int size() {
        return lastIndex + 1;
    }

    private void resize(int newCapacity) {
        Item[] newArray = (Item[]) new Object[newCapacity];
        int i = 0, j = 0;
        while (i <= lastIndex) {
            newArray[i++] = array[j++];
        }
        array = newArray;
    }

    public void enqueue(Item item) {
        if (item == null) {
            throw new IllegalArgumentException();
        }
        // array is full
        if (array.length == lastIndex + 1) {
            resize(array.length * 2);
        }
        array[++lastIndex] = item;
    }

    public Item dequeue() {
        if (isEmpty()) {
            throw new NoSuchElementException();
        }
        int i = StdRandom.uniform(lastIndex + 1);
        Item removed = array[i];
        array[i] = array[lastIndex];
        array[lastIndex--] = null;

        // resize array if it is only 25% full
        if (size() > 0 && size() <= array.length / 4) {
            resize(array.length / 2);
        }

        return removed;
    }

    public Item sample() {
        if (isEmpty()) {
            throw new NoSuchElementException();
        }

        int i = StdRandom.uniform(lastIndex + 1);
        Item sample = array[i];
        return sample;
    }

    @Override
    public Iterator<Item> iterator() {
        return new RandomizedIterator();
    }

    private class RandomizedIterator implements Iterator<Item> {
        private Item[] localArray;
        private int localLastIndex;

        RandomizedIterator() {
            Item[] a = (Item[]) new Object[lastIndex + 1];
            for (int i = 0; i <= lastIndex; i++) {
                a[i] = array[i];
            }
            localArray = a;
            localLastIndex = lastIndex;
        }

        @Override
        public boolean hasNext() {
            return localLastIndex >= 0;
        }

        @Override
        public Item next() {
            if (!hasNext()) {
                throw new NoSuchElementException();
            }
            int i = StdRandom.uniform(localLastIndex + 1);
            Item item = localArray[i];
            localArray[i] = localArray[localLastIndex];
            localArray[localLastIndex--] = null;
            return item;
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
