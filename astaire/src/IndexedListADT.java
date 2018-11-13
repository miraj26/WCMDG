
public interface IndexedListADT<T> extends ListADT<T>{

	public T get(int i);
	
	public T set(int i, T element);
	
	public T remove(int i);
	
	public void add(int i, T element);
	
	public int indexOf(T element);
}