import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;

public class DanceController implements Controller {

	private Reader danceGroups;
	private Reader dances;
	private Reader runningOrder;

	public DanceController() {
		danceGroups = new Reader("files\\danceShowData_danceGroups.csv");
		try {
			danceGroups.readFile();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		dances = new Reader("files\\danceShowData_dances.csv");
		try {
			dances.readFile();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		runningOrder = new Reader("files\\danceShowData_runningOrder.csv");
		try {
			runningOrder.readFile();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	public String listAllDancersIn(String danceName) {
		HashMap<String, ArrayList<String>> data = danceGroups.getData();
		ArrayList<String> dances = data.get(danceName);
		String results = "";
		for (String dance : dances) {
			results += dance + " ";
		}
		return danceName + ": " + results;
	}

	@Override
	public String listAllDancesAndPerformers() {
		String results = "";

		ArrayList<String> keys = danceGroups.getKeyList();
		ArrayList<ArrayList<String>> danceNames = danceGroups.getValueList();

		for (int i = 1; i < danceNames.size(); i++) { // got rid of -1 after values.size()
			ArrayList<String> performerNames = danceNames.get(i);
			
			Collections.sort(performerNames);
			
			results += keys.get(i) + ": " + performerNames + "\n";
		}
		return results;
	}

	@Override
	public String checkFeasibilityOfRunningOrder(String filename, int gaps) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String generateRunningOrder(int gaps) {
		// TODO Auto-generated method stub
		return null;
	}

}