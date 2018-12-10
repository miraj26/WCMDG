import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Scanner;

/**
 * This class reads the .csv file and stores it into an array.
 * 
 * @author Miraj Shah
 * @author Devin Shingadia
 * @author Jacob Williams
 *
 */
public class Reader {

	/**
	 * Holds an instance of the scanner object.
	 */
	private Scanner scanner;
	/**
	 * Holds the file that needs to be read.
	 */
	private File file;
	/**
	 * Holds a HashMap of the dances, where the Key is the dance group and the Value
	 * is an ArrayList of string values containing the names of the dancers.
	 */
	private HashMap<String, ArrayList<String>> dances;
	/**
	 * Holds an ArrayList of Strings, containing the names of all the dance groups.
	 */
	private ArrayList<String> keyList;
	/**
	 * Holds an ArrayList of Strings, containing the names of all the dancers of
	 * each group.
	 */
	private ArrayList<ArrayList<String>> valueList;
	/**
	 * Holds a LinkedList, where each LinkedNode holds a performance.
	 */
	private LinkedList<LinearNode<Performance>> linkedList;
	/**
	 * Holds an ArrayList of Performance.
	 */
	private ArrayList<Performance> performances;

	/**
	 * Create a <code>Reader</code> and automatically assign values.
	 * @param pathname a <code>String</code> of the pathname for the intended file to be read.
	 */
	public Reader(String pathname) {
		file = new File(pathname);
		dances = new HashMap<String, ArrayList<String>>();
		keyList = new ArrayList<>();
		valueList = new ArrayList<>();
		linkedList = new LinkedList<>();
		performances = new ArrayList<>();
	}

	/**
	 * Stores the dances and dance names in a HashMap, read from the file.
	 * @throws FileNotFoundException
	 */
	public void readFileIntoHashMap() throws FileNotFoundException {
		scanner = new Scanner(file);
		while (scanner.hasNextLine()) {
			String line = scanner.nextLine();
			String[] temp = line.split("\t");
			for (int i = 1; i < temp.length; i++) {
				ArrayList<String> names = new ArrayList<>();
				String[] name = temp[1].split(",");
				for (int j = 0; j < name.length; j++) {
					names.add(name[j].trim());
				}
				dances.put(temp[0], names);
				keyList.add(temp[0]);
				valueList.add(names);
			}
		}
	}

	/**
	 *  Stores the dances and dance names in a LinkedList, read from the file.
	 * @throws FileNotFoundException
	 */
	public void readFileIntoLinkedList() throws FileNotFoundException {
		scanner = new Scanner(file);
		LinearNode<Performance> previous = null;
		scanner.nextLine();
		while (scanner.hasNextLine()) {
			String line = scanner.nextLine();
			String[] temp = line.split("\t");
			Performance performance = new Performance(temp[0].trim());
			String[] dancerName = temp[1].split(",");
			// ArrayList<String> names = new ArrayList<>();
			for (int i = 0; i < dancerName.length; i++) {
				// names.add(dancerName[i]);
				performance.addDancer(dancerName[i].trim());
			}
			performances.add(performance);
			LinearNode<Performance> current = new LinearNode<>(performance);
			if (linkedList.isEmpty()) {
				linkedList.addFirst(current);
			} else if (previous != null) {
				linkedList.add(current);
				previous.setNext(current);
			}
			previous = current;
		}
	}

	/**
	 * Gets the LinkedList containing LinkedNodes holding each Performance.
	 * 
	 * @return <code>LinkedList<LinearNode<Performance>></code>
	 */
	public LinkedList<LinearNode<Performance>> getLinkedList() {
		return linkedList;
	}

	/**
	 * Gets the HashMap containing the dances, where the key is a String containing
	 * the Dance Group Name and the value is the ArrayList of Strings which are the
	 * names of each dancer.
	 * 
	 * @return <code>HashMap<String, ArrayList<String>></code> dances.
	 */
	public HashMap<String, ArrayList<String>> getData() {
		return dances;
	}

	/**
	 * Gets the ArrayList of String values containing the names of all the Dance
	 * Groups
	 * 
	 * @return <code>ArrayList<String></code> keyList
	 */
	public ArrayList<String> getKeyList() {
		return keyList;
	}

	/**
	 * Gets the ArrayList of ArrayLists of String values, which contains the names
	 * of the dancers in each dancer
	 * 
	 * @return <code>ArrayList<ArrayList<String>></code> valueList
	 */
	public ArrayList<ArrayList<String>> getValueList() {
		return valueList;
	}

	/**
	 * Gets the ArrayList of Performance, containing details of each performance.
	 * 
	 * @return <code>ArrayList<Performance></code> performances.
	 */
	public ArrayList<Performance> getPerformances() {
		return performances;
	}
}