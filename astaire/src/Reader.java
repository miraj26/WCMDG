import java.io.File.*;
import java.util.Scanner;
/**
 * This class reads the .csv file and stores it into an array.
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
	private ArrayList<ArrayList<String>> dancers;
	
	public Reader(File file) {
		this.file = file;
		scanner = new Scanner(file);
		dancers = new ArrayList<ArrayList<String>>();
	}
	/**
	 * 
	 */
	public void readFile() {
		scanner.useDelimiter("\n");
	}
	
}
