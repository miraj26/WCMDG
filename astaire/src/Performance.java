import java.util.ArrayList;
import java.util.HashSet;
/**
 * This class stores the information about each performance,
 * storing the names of each dancer into an ArrayList.
 * 
 * @author Miraj Shah
 * @author Devin Shingadia
 * @author Jacob Williams
 *
 */
public class Performance {
	/**
	 * Holds the name of the dance group.
	 */
	private String danceName;
	/**
	 * ArrayList of type String which holds the names of the each dancer. 
	 */
	private ArrayList<String> dancers;
	
	private HashSet<String> dancerNames;
	
	public Performance(String danceName) {
		this.danceName = danceName;
		dancers = new ArrayList<String>();
	}
	/**
	 * Gets the name of the dance group.
	 * @return <code>String</code> danceName
	 */
	public String getDanceName() {
		return danceName;
	}
	/**
	 * Adds the name of the dancer to the ArrayList of dancers
	 * @param <code>String</code> dancer, the name of the dancer
	 */
	public void addDancer(String dancer) {
		dancers.add(dancer);
	}
	/**
	 * Returns the ArrayList of the names of dancers
	 * @return <code>ArrayList</code> dancers, the ArrayList of type String containing the names of the dancers.
	 */
	public ArrayList<String> getDancers(){
		return dancers;
	}
	/**
	 * Replaces {@link #dancers} with dancerNames.
	 * @param dancerNames <code>ArrayList</code> of type String containing the names of the dancers.
	 */
	public void replaceDancerNames(ArrayList<String> dancerNames) {
		dancers = dancerNames;
	}
	
	public HashSet<String> getDancerNamesHashSet() {
		return dancerNames;
	}
	

	public void addDancerToHashSet(String dancerName) {
		dancerNames.add(dancerName);
	}
}
