package wildfires.dataStructures;

public interface List<E> {
	int size();
	boolean isEmpty();
	E get(int i);
	void set(int i, E e);
	void add(int i, E e);
	E remove(int i);
}
