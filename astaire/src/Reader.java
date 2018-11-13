import java.io.File;
import java.io.File.*;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Scanner;
/**
 * This class reads the .csv file and stores it into an array.
 * @author Miraj Shah, Jacob Williams, Devin Shingadia
 *
 */
public class Reader{

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
	private ArrayList<ArrayList<String>> dancers;
	
	//private HashMap<String, IndexedList>;
	
	public Reader(String pathname) {
		file = new File(pathname);
		dancers = new ArrayList<ArrayList<String>>();
	}
	
	/**
	 * @throws FileNotFoundException 
	 * 
	 */
	public void readFile() throws FileNotFoundException {
		scanner = new Scanner(file);
		scanner.useDelimiter("\n");
		while(scanner.hasNextLine()) {
			String line = scanner.nextLine();
			System.out.println(line);
			
			String[] temp = line.split("\t");
		}
	}
}