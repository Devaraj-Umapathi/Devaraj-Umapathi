import java.util.ArrayList;
import java.util.List;

public class MyHashMap<K, V> {

    private static final int DEFAULT_CAPACITY = 16;
    private static final float DEFAULT_LOAD_FACTOR = 0.75f;

    private Node<K, V>[] buckets;
    private int size;
    private final float loadFactor;

    public MyHashMap() {
        this(DEFAULT_CAPACITY, DEFAULT_LOAD_FACTOR);
    }

    public MyHashMap(int capacity, float loadFactor) {
        this.buckets = new Node[capacity];
        this.loadFactor = loadFactor;
        this.size = 0;
    }

    private int hash(K key) {
        // return key.hashCode() % buckets.length
        // ((key % size) + size) % size
        return Math.floorMod(key.hashCode(), buckets.length);
    }

    public void put(K key, V value) {
        int index = hash(key);
        Node<K, V> head = buckets[index];

        for (Node<K, V> node = head; node != null; node = node.next) {
            if (node.key.equals(key)) {
                node.value = value;
                return;
            }
        }

        Node<K, V> newNode = new Node<>(key, value);
        newNode.next = head;
        buckets[index] = newNode;
        size++;

        if ((float) size / buckets.length > loadFactor) {
            resize();
        }
    }

    public V get(K key) {
        int index = hash(key);
        for (Node<K, V> node = buckets[index]; node != null; node = node.next) {
            if (node.key.equals(key)) {
                return node.value;
            }
        }
        return null;
    }

    public V remove(K key) {
        int index = hash(key);
        Node<K, V> prev = null;
        Node<K, V> curr = buckets[index];

        while (curr != null) {
            if (curr.key.equals(key)) {
                if (prev == null) {
                    buckets[index] = curr.next;  // [1] -> 3 -> 4 -> 5
                } else {
                    prev.next = curr.next;
                }
                size--;
                return curr.value;
            }
            prev = curr;
            curr = curr.next;
        }
        return null;
    }

    public boolean containsKey(K key) {
        return get(key) != null;
    }

    public int size() {
        return size;
    }

    public boolean isEmpty() {
        return size == 0;
    }

    private void resize() {
        Node<K, V>[] oldBuckets = buckets;
        buckets = new Node[oldBuckets.length * 2];
        size = 0;

        for (Node<K, V> head : oldBuckets) {
            for (Node<K, V> node = head; node != null; node = node.next) {
                put(node.key, node.value);
            }
        }
    }

    public void printBuckets() {
        for (int i = 0; i < buckets.length; i++) {
            System.out.print("Bucket[" + i + "]: ");
            List<String> entries = new ArrayList<>();
            for (Node<K, V> node = buckets[i]; node != null; node = node.next) {
                entries.add(node.key + "=" + node.value);
            }
            System.out.println(String.join(" -> ", entries));
        }
    }

    static class Node<K, V> {
        final K key;
        V value;
        Node<K, V> next;

        Node(K key, V value) {
            this.key = key;
            this.value = value;
        }
    }
}
