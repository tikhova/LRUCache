package main.java;

import java.util.HashMap;
import java.util.LinkedList;

public class LRUCache<E> {
    LinkedList<Integer> queue;
    HashMap<Integer, E> hash;
    int capacity;

    public LRUCache(int capacity) {
        this.capacity = capacity;
        this.queue = new LinkedList<>();
        this.hash = new HashMap<>();
    }

    public void put(E val) {
        int size = queue.size();
        assert size <= capacity;

        int key = val.hashCode();
        queue.remove(Integer.valueOf(key));
        if (queue.size() == capacity) {
            int displacedKey = queue.removeFirst();
            hash.remove(displacedKey);
        }
        queue.add(key);
        hash.put(key, val);
        int newSize = queue.size();

        assert newSize > 0;
        assert (newSize == size) || (newSize == size + 1);
        assert size <= capacity;
        assert queue.peekLast() == key;
    }

    public E get(int k) {
        Integer key = k;
        int size = queue.size();
        assert size > 0;
        assert hash.containsKey(key);

        queue.remove(key);
        queue.add(key);

        assert queue.size() == size;
        assert queue.peekLast().equals(key);

        return hash.get(key);
    }

    public int size() {
        return queue.size();
    }
}
