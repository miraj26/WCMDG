import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
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

		LinkedList<LinearNode<Performance>> runningOrder = new LinkedList<>(); // final
		String runningOrderList = "";
		boolean completed = false;

		try {
			dances.newReadFile();
			ArrayList<Performance> performances = dances.getPerformances();
			ArrayList<Performance> temp = new ArrayList<>();
			while (!completed) {
				for (Performance performance : performances) {
					if (!runningOrder.isEmpty()) {
						boolean success = true;
						Performance lastInList = runningOrder.getLast().getElement();
						// int index = performances.indexOf(lastInList);
						if (!temp.isEmpty()) {
							for (int i = 0; i < temp.size(); i++) {
								for (int j = 0; j < gaps; j++) {
									int index = runningOrder.size() - j - 1;
									if (index >= 0) {
										ArrayList<String> namesInList = runningOrder.get(index).getElement()
												.getDancers();
										ArrayList<String> namesChecking = performance.getDancers();
										for (String name : namesInList) {
											if (namesChecking.contains(name)) {
												success = false;
											}
										}
									}
								}
								if (success) {
									Performance desired = temp.remove(i);
									runningOrder.addLast(new LinearNode<Performance>(desired));
									// completed = true;
								}
							}
						} else if (!temp.isEmpty() && !success || temp.isEmpty()) {
							ArrayList<String> namesChecking = performance.getDancers();
							for (int i = 0; i < gaps; i++) {
								int index = runningOrder.size() - i -1;
								if (index >= 0) {
									ArrayList<String> namesInList = runningOrder.get(index).getElement().getDancers();
									for (String name : namesInList) {
										if (namesChecking.contains(name)) {
											success = false;
											temp.add(performance);
											// performances.remove(performance);
										}
									}
								}
							}
							if(success) {
								runningOrder.addLast(new LinearNode<Performance>(performance));
								// completed = true;
							}
						}
					} else {
						runningOrder.add(new LinearNode<Performance>(performance));
					}
				}
				
				if(checkFeasibilityOfRunningOrder("danceShowData_dances.csv", gaps).equals("Running Order is valid.")) {
					completed = true;
				}
				
				if(!completed) {
					return "Running order cannot be generated.";
				}
			}
						
			Iterator<LinearNode<Performance>> iterator = runningOrder.listIterator();
			int i = 1;
			while(iterator.hasNext()) {
				runningOrderList += i + ": " + iterator.next().getElement().getDancers() + "\n";
				i++;
			}
			
			return runningOrderList;

		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return null;
	}
}