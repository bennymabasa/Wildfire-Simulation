package wildfires.dataStructures;

import java.util.Comparator;

public class MyPriorityQueue<K, V> implements PriorityQueue<K, V> {
    private MyArrayList<Entry<K, V>> heap;
    private Comparator<K> comp;

    public MyPriorityQueue() {
        this(new DefaultComparator<>());
    }

    public MyPriorityQueue(Comparator<K> c) {
        heap = new MyArrayList<>();
        comp = c;
    }

    private static class DefaultComparator<K> implements Comparator<K> {
        @SuppressWarnings("unchecked")
        public int compare(K a, K b) {
            return ((Comparable<K>) a).compareTo(b);
        }
    }

    protected int compare(Entry<K, V> a, Entry<K, V> b) {
        return comp.compare(a.getKey(), b.getKey());
    }

    @Override
    public int size() { return heap.size(); }

    @Override
    public boolean isEmpty() { return heap.isEmpty(); }

    @Override
    public Entry<K, V> insert(K key, V value) {
        Entry<K, V> newest = new Entry<>(key, value);
        heap.add(newest);
        upheap(heap.size() - 1);
        return newest;
    }

    @Override
    public Entry<K, V> min() {
        if (isEmpty()) return null;
        return heap.get(0);
    }

    @Override
    public Entry<K, V> removeMin() {
        if (isEmpty()) return null;
        Entry<K, V> answer = heap.get(0);
        swap(0, heap.size() - 1);
        heap.remove(heap.size() - 1);
        downheap(0);
        return answer;
    }

    private void upheap(int j) {
        while (j > 0) {
            int p = (j - 1) / 2;
            if (compare(heap.get(j), heap.get(p)) >= 0) break;
            swap(j, p);
            j = p;
        }
    }

    private void downheap(int j) {
        while (true) {
            int left = 2 * j + 1;
            if (left >= heap.size()) break;
            int smallChild = left;
            int right = left + 1;
            if (right < heap.size() && compare(heap.get(right), heap.get(left)) < 0) {
                smallChild = right;
            }
            if (compare(heap.get(smallChild), heap.get(j)) >= 0) break;
            swap(j, smallChild);
            j = smallChild;
        }
    }

    private void swap(int i, int j) {
        Entry<K, V> temp = heap.get(i);
        heap.set(i, heap.get(j));
        heap.set(j, temp);
    }
}
