
public interface ListADT<T> extends Iterable<T> {
	T removeFirst();
	T removeLast();
	T first();
	T last();
	boolean isEmpty();
	int size();
	boolean remove(T element);
	boolean contains(T target);
}
