import java.io.File;
import java.io.File.*;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.HashMap;
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
	//private ArrayList<ArrayList<String>> dancers;
	
	private HashMap<String, String> dances;
	
	public Reader(String pathname) {
		file = new File(pathname);
		dances = new HashMap<String, String>();
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
			//System.out.println(line);
			String names = "";
			String[] temp = line.split("\t");
			for(int i = 1; i < temp.length; i++) {
				if(i == temp.length -1) {
					names += temp[i] + ".";
				}
				else {
					names += temp[i] + ", ";
				}
			}
			dances.put(temp[0], names);
		}
	}
	
	public HashMap<String, String> getData(){
		return dances;
	}
}