import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedList;

public class DanceController implements Controller {

	private Reader danceGroups;
	private Reader dances;

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
		HashMap<String, ArrayList<String>> data = danceGroups.getData();

		ArrayList<String> keys = dances.getKeyList();
		ArrayList<ArrayList<String>> danceNames = dances.getValueList();
		Collections.sort(keys);

		for (int i = 1; i < danceNames.size(); i++) {
			ArrayList<String> performerNames = danceNames.get(i);
			for (int j = 0; j < performerNames.size(); j++) {
				if (data.containsKey(performerNames.get(j))) {
					ArrayList<String> names = data.get(performerNames.get(j));
					performerNames.remove(j);
					performerNames.addAll(0, names);
				}
			}
			Collections.sort(performerNames);
			results += keys.get(i) + ": " + performerNames + "\n";
		}
		return results;
	}

	@Override
	public String checkFeasibilityOfRunningOrder(String filename, int gaps) {
		Reader runningOrder = new Reader("files\\" + filename);
		// Reader runningOrder = new Reader("files\\danceShowData_runningOrder.csv");
		String clashes = "";
		boolean successful = true;

		try {
			boolean listSearched = false;

			runningOrder.newReadFile();
			LinkedList<LinearNode<Performance>> linked = runningOrder.getLinkedList();
			LinearNode<Performance> current = linked.get(0);

			while (!listSearched) {
				ArrayList<String> dancers = current.getElement().getDancers();
				LinearNode<Performance> nextInLine = current.getNext();
				for (int i = 0; i < gaps; i++) {
					if (linked.indexOf(nextInLine) != -1) { // Checks if nextInLine is null
						ArrayList<String> nextDancers = nextInLine.getElement().getDancers();
						for (String dancer : dancers) {
							if (nextDancers.contains(dancer)) {
								String message = "There is a clash with " + dancer + ", in "
										+ nextInLine.getElement().getDanceName() + "\n ";
								if (!clashes.contains(message)) {
									clashes += message;
								}
								successful = false;
							}
						}
						nextInLine = nextInLine.getNext();
					}
				}
				if (current.getNext() != null) {
					current = current.getNext();
				} else {
					listSearched = true;
				}
			}

		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if (successful) {
			return "Running Order is valid.";
		} else {
			return clashes;
		}
	}

	@Override
	public String generateRunningOrder(int gaps) {
		// TODO Auto-generated method stub
		Reader dances = new Reader("files\\danceShowData_dances.csv");//change path name
		LinkedList<LinearNode<Performance>> runningOrder = new LinkedList<>();
		LinkedList<LinearNode<Performance>> currentDanceOrder = new LinkedList<>();
		String runningOrderList = "";
		boolean completed = false;
		
		
		
		return null;
	}
}