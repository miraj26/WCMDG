import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;
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

	public Reader(String pathname) {
		file = new File(pathname);
		dances = new HashMap<String, ArrayList<String>>();
		keyList = new ArrayList<>();
	}

	/**
	 * @throws FileNotFoundException
	 * 
	 */
	public void readFile() throws FileNotFoundException {
		scanner = new Scanner(file);
		scanner.useDelimiter("\n");
		while (scanner.hasNextLine()) {
			String line = scanner.nextLine();
			// System.out.println(line);
			// String names = "";
			String[] temp = line.split("\t");
			for (int i = 0; i < temp.length; i++) {
				ArrayList<String> names = new ArrayList<>();
				if (i == 0) {
					// names += temp[0] + " members: ";
				} else {
					// names += temp[i];
					String[] name = temp[1].split(",");
					for (int j = 0; j < name.length; j++) {
						names.add(name[j]);
					}
				}
				dances.put(temp[0], names);
				keyList.add(temp[0]);
			}
		}
	}

	public void readFile2() throws FileNotFoundException {
		scanner = new Scanner(file);
		scanner.useDelimiter("\n");
		while (scanner.hasNextLine()) {
			String line = scanner.nextLine();
			String names = "";
			String temp[] = line.split("\t");
		}
	}

	public HashMap<String, ArrayList<String>> getData() {
		return dances;
	}
	
	public ArrayList<String> getKeyList(){
		return keyList;
	}
	
}