import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;

public class DanceController implements Controller{
	
	public DanceController() {
	}
	
	@Override
	public String listAllDancersIn(String danceName) {
		Reader reader = new Reader("files\\danceShowData_danceGroups.csv");
		try {
			reader.readFile();
			HashMap<String, String> data = reader.getData();
			String dance = data.get(danceName);
			return dance;
			
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public String listAllDancesAndPerformers() {
		// TODO Auto-generated method stub
		return null;
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
