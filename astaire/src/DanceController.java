import java.io.FileNotFoundException;
import java.util.ArrayList;
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
		ArrayList<ArrayList<String>> values = danceGroups.getValueList();

		for (int i = 1; i < values.size() - 1; i++) {
			boolean sorted = false;
			ArrayList<String> value = values.get(i);

			int j = 0;
			int count = 0;
			// for (int j = 0; j < value.size() - 1; j++) {
			while (sorted == false) {
				int difference = value.get(j).compareTo(value.get(j + 1));
				if (difference > 0) {
					// System.out.println(value.get(j) + " and " + value.get(j + 1));
					String temp = value.get(j + 1);
					value.remove(j + 1);
					value.add(j, temp);
					count++;
				}
				j++;
				if (j >= value.size() - 1) {
					j = 0;
					count = 0;
				}
				if (count == 0 && j == value.size() - 2) {
					sorted = true;
				}
			}
			results += keys.get(i) + ": " + values.get(i) + "\n";
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