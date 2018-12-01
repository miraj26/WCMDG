/**
 * The class creates each LinearNode, holding elements of a certain type
 * to be put into a linked list.
 * @author Miraj Shah, Jacob Williams, Devin Shingadia
 *
 * @param <T>
 */
public class LinearNode<T> {
	/**
	 * Holds the element to be placed into the linked list
	 */
	private T element;
	/**
	 * Holds the node that will follow this node
	 */
	private LinearNode<T> next;
	
	public LinearNode() {
		next = null;
		element = null;
	}
	
	public LinearNode(T elem) {
		next = null;
		element = elem;
	}
	/**
	 * Get the node next to this one.
	 * @return <code>LinearNode<T></code> next, the node that follows this node.
	 */
	public LinearNode<T> getNext(){
		return next;
	}
	/**
	 * Sets the next node that will follow the node.
	 * @param <code>LinearNode<T></code> node, node that will follow this node.
	 */
	public void setNext(LinearNode<T> node) {
		next = node;
	}
	/**
	 * Gets the elements that is being held in this node.
	 * @return <code>T</code> element, the element held in the LinearNode
	 */
	public T getElement() {
		return element;
	}
	/**
	 * Sets element that will be held in the LinearNode
	 * @param <code>T</code>elem, the element being stored in the LinearNode
	 */
	public void setElement(T elem) {
		element = elem;
	}
}
