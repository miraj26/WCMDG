import java.util.Iterator;

public abstract class DoubleList<T> {
	protected DoubleNode<T> front;
	protected DoubleNode<T> rear;
	protected int count;
	
	protected DoubleList() {
		rear = null;
		front = null;
		count = 0;
	}
	
	protected boolean isEmpty() {
		if(count == 0) {
			return true;
		}
		return false;
	}
	
	public int size() {
		return count;
	}
	
	public void clear() {
		front = null;
		rear = null;
		count = 0;
	}
	
	protected T removeLast() {
		if (isEmpty()) {
			throw new IllegalStateException("List is empty");
		}
		T result = rear.getElement();
		rear = rear.getPrevious();
		count--;
		if(rear == null) {
			front = null;
		}
		else {
			rear.setNext(null);
		}
		return result;
	}
	
	protected T removeFirst() {
		if(isEmpty()) {
			throw new IllegalStateException("List is empty");
		}
		T result = front.getElement();
		front = front.getNext();
		count--;
		if(front == null) {
			rear = null;
		}
		else {
			front.setPrevious(null);
		}
		return result;
	}
	
	protected void remove(DoubleNode<T> node) {
		if(node == null) {
			throw new IllegalArgumentException("Null node");
		}
		if(isEmpty()) {
			throw new IllegalStateException("List is empty");
		}
		if(node == front) {
			this.removeFirst();
		}
		else if(node == rear) {
			this.removeLast();
		}
		else {
			node.getNext().setPrevious(node.getPrevious());
			node.getPrevious().setNext(node.getNext());
			count--;
		}
	}
	
	protected void addFirst(T element) {
		DoubleNode<T> node = new DoubleNode<T>(element);
		node.setNext(front);
		front = node;
		if(count == 0) {
			rear = node;
		}
		else {
			front.getNext().setPrevious(node);
		}
		count++;
	}
	
	protected void addAfter(DoubleNode<T> current, T element) {
		if(current == null) {
			throw new IllegalArgumentException("Null Node");
		}
		if(current == rear) {
			DoubleNode<T> node = new DoubleNode<T>(element);
			node.setNext(current.getNext());
			node.setPrevious(current);
			current.getNext().setPrevious(node);
			current.setNext(node);
			count++;
		}
	}
	
	protected DoubleNode<T> find(T target){
		DoubleNode<T> cursor = front;
		while(cursor != null) {
			if(target.equals(cursor.getElement())){
				return cursor;
			} else {
				cursor = cursor.getNext();
			}
		}
		return null;
	}
	
	public boolean contains(T element) {
		return (find(element) != null);
	}
	
	private class DoubleIterator implements Iterator<T>{

		private DoubleNode<T> cursor;
		
		private DoubleNode<T> node;
		
		public DoubleIterator() {
			cursor = front;
			node = null;
		}
		
		@Override
		public boolean hasNext() {
			return (cursor != null);
		}

		@Override
		public T next() {
			if(!hasNext()) {
				throw new IllegalStateException("Do next element");
			}
			node = cursor;
			T result = cursor.getElement();
			cursor = cursor.getNext();
			return result;
		}
		
	}
}
