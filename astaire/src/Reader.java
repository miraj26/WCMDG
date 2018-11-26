import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Scanner;

/**
 * This class reads the .csv file and stores it into an array.
 * 
 * @author Miraj Shah, Jacob Williams, Devin Shingadia
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
	 * Holds a 2D ArrayList of Strings.
	 */
	// private ArrayList<ArrayList<String>> dancers;

	private HashMap<String, ArrayList<String>> dances;

	private ArrayList<String> keyList;

	private ArrayList<ArrayList<String>> valueList;

	private LinkedList<LinearNode<ArrayList<String>>> linkedList;

	public Reader(String pathname) {
		file = new File(pathname);
		dances = new HashMap<String, ArrayList<String>>();
		keyList = new ArrayList<>();
		valueList = new ArrayList<>();
		linkedList = new LinkedList<>();
	}

	/**
	 * @throws FileNotFoundException
	 * 
	 */
	public void readFile() throws FileNotFoundException {
		scanner = new Scanner(file);
		// scanner.useDelimiter("\n");
		while (scanner.hasNextLine()) {
			String line = scanner.nextLine();
			String[] temp = line.split("\t");
			for (int i = 1; i < temp.length; i++) {
				ArrayList<String> names = new ArrayList<>();
				String[] name = temp[1].split(", ");
				for (int j = 0; j < name.length; j++) {
					names.add(name[j]);
				}
				dances.put(temp[0], names);
				keyList.add(temp[0]);
				valueList.add(names);
			}
		}
	}

	public void newReadFile() throws FileNotFoundException {
		scanner = new Scanner(file);
		LinearNode<ArrayList<String>> previous = null;
		while (scanner.hasNextLine()) {
			String line = scanner.nextLine();
			String[] temp = line.split("\t");
			String[] dancerName = temp[1].split(", ");
			ArrayList<String> names = new ArrayList<>();
			for (int i = 0; i < dancerName.length; i++) {
				names.add(dancerName[i]);
			}
			LinearNode<ArrayList<String>> current = new LinearNode<>(names);
			if (linkedList.isEmpty()) {
				linkedList.addFirst(current);
			} else if (previous != null) {
				linkedList.add(current);
				previous.setNext(current);
			}
			previous = current;
		}
	}

	public LinkedList<LinearNode<ArrayList<String>>> getLinkedList(){
		return linkedList;
	}
	
	public HashMap<String, ArrayList<String>> getData() {
		return dances;
	}

	public ArrayList<String> getKeyList() {
		return keyList;
	}

	public ArrayList<ArrayList<String>> getValueList() {
		return valueList;
	}

}