package wildfires.dataStructures;

public class MyArrayList<E> implements List<E> {
    private static final int DEFAULT_CAPACITY = 10;
    private E[] data;
    private int size;

    @SuppressWarnings("unchecked")
    public MyArrayList() {
        data = (E[]) new Object[DEFAULT_CAPACITY];
        size = 0;
    }

    @SuppressWarnings("unchecked")
    public MyArrayList(int capacity) {
        if (capacity < 0) throw new IllegalArgumentException("Capacity cannot be negative");
        data = (E[]) new Object[capacity];
        size = 0;
    }

    @Override
    public int size() { return size; }

    @Override
    public boolean isEmpty() { return size == 0; }

    @Override
    public E get(int i) {
        checkIndex(i, size);
        return data[i];
    }

    @Override
    public void set(int i, E e) {
        checkIndex(i, size);
        data[i] = e;
    }

    @Override
    public void add(int i, E e) {
        checkIndex(i, size + 1);
        if (size == data.length) resize(2 * data.length);
        for (int k = size - 1; k >= i; k--) {
            data[k + 1] = data[k];
        }
        data[i] = e;
        size++;
    }

    public void add(E e) {
        add(size, e);
    }

    @Override
    public E remove(int i) {
        checkIndex(i, size);
        E removed = data[i];
        for (int k = i; k < size - 1; k++) {
            data[k] = data[k + 1];
        }
        data[size - 1] = null;
        size--;
        return removed;
    }

    private void checkIndex(int i, int n) {
        if (i < 0 || i >= n) throw new IndexOutOfBoundsException("Index: " + i + ", Size: " + size);
    }

    @SuppressWarnings("unchecked")
    private void resize(int capacity) {
        E[] temp = (E[]) new Object[capacity];
        for (int i = 0; i < size; i++) {
            temp[i] = data[i];
        }
        data = temp;
    }
}
