
public class DoubleNode<T> {
	
	private DoubleNode<T> previous;
	private DoubleNode<T> next;
	private T element;
	
	public DoubleNode(T element) {
		next = null;
		previous = null;
		this.element = element;
	}
	
	public DoubleNode<T> getPrevious(){
		return previous;
	}
	
	public void setPrevious(DoubleNode<T> dnode){
		previous = dnode;
	}
}