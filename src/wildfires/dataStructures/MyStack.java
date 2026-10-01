package wildfires.dataStructures;

public class MyStack<E> implements Stack<E> {
    private MyArrayList<E> list;

    public MyStack() {
        list = new MyArrayList<>();
    }

    @Override
    public int size() { return list.size(); }

    @Override
    public boolean isEmpty() { return list.isEmpty(); }

    @Override
    public void push(E e) {
        list.add(e);
    }

    @Override
    public E top() {
        if (isEmpty()) throw new IllegalStateException("Stack is empty");
        return list.get(list.size() - 1);
    }

    @Override
    public E pop() {
        if (isEmpty()) throw new IllegalStateException("Stack is empty");
        return list.remove(list.size() - 1);
    }
}
