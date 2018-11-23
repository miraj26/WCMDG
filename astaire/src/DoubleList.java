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
}
