import java.util.ArrayList;

public class Performance {
	
	private String danceName;
	private ArrayList<String> dancers;
	
	public Performance(String danceName) {
		this.danceName = danceName;
		dancers = new ArrayList<String>();
	}
	
	public String getDanceName() {
		return danceName;
	}
	
	public void addDancer(String dancer) {
		dancers.add(dancer);
	}
	
	public ArrayList<String> getDancers(){
		return dancers;
	}

}
