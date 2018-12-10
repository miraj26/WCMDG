import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Random;
import java.util.Scanner;

/**
 * Represents the functionality for the TUI
 * 
 * @author Miraj Shah
 * @author Devin Shingadia
 * @author Jacob Williams
 */
public class DanceController implements Controller {

	/**
	 * Holds data referring to a file showing dance group members.
	 * 
	 * @see #DanceController()
	 * @see #listAllDancersIn(String)
	 * @see #listAllDancesAndPerformers()
	 * @see #checkFeasibilityOfRunningOrder(String, int)
	 */
	private Reader danceGroups;

	/**
	 * Holds data referring to a file showing dances.
	 * 
	 * @see #DanceController()
	 * @see #listAllDancesAndPerformers()
	 * @see #generateRunningOrder(int)
	 */
	private Reader dances;

	/**
	 * Create a <code>DanceController</code> and automatically reads the data from
	 * the required files.
	 */
	public DanceController() {
		danceGroups = new Reader("files\\danceShowData_danceGroups.csv");
		try {
			danceGroups.readFileIntoHashMap();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		dances = new Reader("files\\danceShowData_dances.csv");
		try {
			dances.readFileIntoHashMap();
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

			runningOrder.readFileIntoLinkedList();
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
			dances.readFileIntoLinkedList();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		LinkedList<LinearNode<Performance>> linked = dances.getLinkedList();
		// LinearNode<Performance> current = linked.get(0);

		ArrayList<Integer> addedToList = new ArrayList<>();
		while (runningOrder.size() != linked.size()) {
			int index = rand.nextInt(linked.size());
			if (!addedToList.contains(index)) {
				runningOrder.add(linked.get(index));
				addedToList.add(index);
				if (runningOrder.size() > 1) { // Checks if added element is not first in the list
					runningOrder.get(runningOrder.size() - 2).setNext(linked.get(index));
				}
			}
			runningOrder.getLast().setNext(null);
		}

		linked.clear();

		while (!completed) {
			String feasibility = checkFeasibility(runningOrder, runningOrder.getFirst(), gaps);
			if (feasibility.equals("")) { // and linked.isEmpty()
				completed = true;
				for (LinearNode<Performance> performance : runningOrder) {
					result += performance.getElement().getDanceName() + "\n";
				}
			} else {
				ArrayList<String> clashes = new ArrayList<String>();
				Scanner scanner = new Scanner(feasibility);
				while (scanner.hasNextLine()) {
					String line = scanner.nextLine();
					String[] newLine = line.replaceAll("There is a clash with ", ",").replaceAll(" in", "").split(",");
					for (int i = 0; i < newLine.length; i += 2) {
						clashes.add(newLine[i]);
					}
				}
				scanner.close();
				for (String clash : clashes) {
					for (int i = 0; i < runningOrder.size(); i++) {
						if (runningOrder.get(i).getElement().getDanceName().contains(clash)) {
							linked.add(runningOrder.get(i));
							runningOrder.remove(i);
							if (i > 0 && i < runningOrder.size()) {
								runningOrder.get(i - 1).setNext(runningOrder.get(i));
							}
						}
					}
				}
				ArrayList<LinearNode<Performance>> addedPerformances = new ArrayList<>();
				for (LinearNode<Performance> performance : linked) {
					boolean added = false;
					int listSize = runningOrder.size();
					for (int i = 0; i < listSize; i++) {
						if (!added) {
							runningOrder.add(i, performance);

							if (i == 0) {
								runningOrder.getFirst().setNext(runningOrder.get(1));

							} else {
								runningOrder.get(i - 1).setNext(runningOrder.get(i)); // Sets the "next" element of the
																						// previous node
								runningOrder.get(i).setNext(runningOrder.get(i + 1));
								runningOrder.getLast().setNext(null);
							}
							// System.out.println("List element: " + i);
							if (!checkFeasibility(runningOrder, runningOrder.getFirst(), gaps).equals("")) {
								runningOrder.remove(i);
								// runningOrder.getLast().setNext(null);
								if (i > 0) {
									runningOrder.get(i - 1).setNext(runningOrder.get(i));
								}
							} else {
								addedPerformances.add(performance);
								added = true;
							}
						}
					}
				}
				for (LinearNode<Performance> performance : addedPerformances) {
					linked.remove(performance);
				}
			}
		}
		return result;
	}

	/**
	 * Replaces the names in "dances", if they appear in "data", with the names of
	 * the people in the group.
	 * 
	 * @param dances a <code>LinkedList</code> of LinearNodes of type Performance, containing data about each dance.
	 * @param group a <code>HashMap</code> with a String key and ArrayList value, containing data about who is in each group.
	 */
	private void changeGroupsToNames(LinkedList<LinearNode<Performance>> dances,
			HashMap<String, ArrayList<String>> group) {
		for (LinearNode<Performance> performances : dances) {
			Performance performance = performances.getElement();
			ArrayList<String> names = performance.getDancers();
			for (int i = 0; i < names.size(); i++) {
				if (group.containsKey(names.get(i))) {
					ArrayList<String> newNames = group.get(names.get(i));
					names.remove(i);
					names.addAll(0, newNames);
				}
			}
			performance.replaceDancerNames(names);
		}
	}

	/**
	 * Checks whether the given LinkedList is in a suitable order, and returns which dances have clashed otherwise.
	 * @param linked a <code>LinkedList</code> of LinearNodes of type Performance, containing the proposed running order.
	 * @param first a <code>LinearNode</code> of type Performance containing the first element in the LinkedList.
	 * @param gaps an <code>int</code> showing the number of gaps there must be between a recurrence of a dancer.
	 * @return <code>String</code> containing which dances have clashed, or an empty String depending on if a clash/es occur.
	 */
	private String checkFeasibility(LinkedList<LinearNode<Performance>> linked, LinearNode<Performance> first,
			int gaps) {
		LinearNode<Performance> current = first;
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