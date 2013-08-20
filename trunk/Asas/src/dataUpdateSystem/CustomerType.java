package dataUpdateSystem;

public enum CustomerType {
	Service(0),
	Gui(1);
	
	public static final int TYPES_COUNT = 2;
	private int priority;
	
	CustomerType(int priority){
		this.priority = priority;
	}
	
	public int getPriority(){
		return this.priority;
	}
}
