import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Random;

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
		// Reader runningOrder = new Reader("files\\" + filename);
		Reader runningOrder = new Reader("files\\danceShowData_runningOrder.csv");

		String clashes = "";
		boolean successful = true;
		try {

			runningOrder.newReadFile();
			HashMap<String, ArrayList<String>> data = danceGroups.getData();
			LinkedList<LinearNode<Performance>> linked = runningOrder.getLinkedList();
			changeGroupsToNames(linked, data);
			LinearNode<Performance> current = linked.get(0);

			clashes = checkFeasibility(linked, current, gaps);
			if (!clashes.equals("")) {
				successful = false;
			}

		} catch (

		FileNotFoundException e) {
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
		LinkedList<LinearNode<Performance>> runningOrder = new LinkedList<>();
		Random rand = new Random();
		boolean completed = false;
		String result = "";

		try {
			dances.newReadFile();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		LinkedList<LinearNode<Performance>> linked = dances.getLinkedList();
		// LinearNode<Performance> current = linked.get(0);

		while (!completed) {
			while (runningOrder.size() != linked.size()) {
				int index = rand.nextInt(linked.size());
				if (!runningOrder.contains(linked.get(index))) {
					runningOrder.add(linked.get(index));
					String check = checkFeasibility(runningOrder, runningOrder.getFirst(), gaps);
					if (!check.equals("")) {
						runningOrder.removeLast();
					}
				}
			}
			// Check if completed .. if so then completed = true
			String clashes = checkFeasibility(runningOrder, runningOrder.getFirst(), gaps);
			System.out.println(clashes);
			if (clashes.equals("")) {
				completed = true;
			}
		}
		if (completed) {
			for (int i = 0; i < runningOrder.size(); i++) {
				result += i + ": " + runningOrder.get(i).getElement().getDanceName().toString() + "\n";
			}
		}

		return result;

	}

	/**
	 * Replaces the names in "dances", if they appear in "data", with the names of
	 * the people in the group.
	 * 
	 * @param dances
	 * @param data
	 */
	private void changeGroupsToNames(LinkedList<LinearNode<Performance>> dances,
			HashMap<String, ArrayList<String>> data) {
		for (LinearNode<Performance> performances : dances) {
			Performance performance = performances.getElement();
			ArrayList<String> names = performance.getDancers();
			for (int i = 0; i < names.size(); i++) {
				if (data.containsKey(names.get(i))) {
					ArrayList<String> newNames = data.get(names.get(i));
					names.remove(i);
					names.addAll(0, newNames);
				}
			}
			performance.replaceDancerNames(names);
		}
	}

	private String checkFeasibility(LinkedList<LinearNode<Performance>> linked, LinearNode<Performance> current,
			int gaps) {
		String clashes = "";
		boolean listSearched = false;
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
		return clashes;
	}
}