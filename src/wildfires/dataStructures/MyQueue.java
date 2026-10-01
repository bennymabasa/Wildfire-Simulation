package wildfires.dataStructures;

public class MyQueue<E> implements Queue<E> {
    private MyArrayList<E> list;

    public MyQueue() {
        list = new MyArrayList<>();
    }

    @Override
    public int size() { return list.size(); }

    @Override
    public boolean isEmpty() { return list.isEmpty(); }

    @Override
    public void enqueue(E e) {
        list.add(e);
    }

    @Override
    public E first() {
        if (isEmpty()) throw new IllegalStateException("Queue is empty");
        return list.get(0);
    }

    @Override
    public E dequeue() {
        if (isEmpty()) throw new IllegalStateException("Queue is empty");
        return list.remove(0);
    }
}
