import java.util.Iterator;

public class LinkedIterator<T> implements Iterator<T>{

	private LinearNode<T> cursor;

	public LinkedIterator(LinearNode<T> contents) {
		cursor = contents;
	}
	
	@Override
	public boolean hasNext() {
		return (cursor != null);
	}

	@Override
	public T next() {
		if(!hasNext()) {
			throw new IllegalStateException("No next element");
		}
		T result = cursor.getElement();
		cursor = cursor.getNext();
		return result;
	}
}
