import java.util.ArrayList;

public class Performers {
	
	private String danceName;
	private ArrayList<String> dancers;
	
	public Performers(String danceName) {
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
