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
	
	protected T removeLast() {
		if (isEmpty()) {
			throw new IllegalStateException("List is empty");
		}
		T result = rear.getElement();
		rear = rear.getPrevious();
		count--;
		if(rear = null) {
			front = null;
		}
		else {
			rear.setNext(null);
		}
		return result;
	}
}
