package basic;

public class ElectiveClass extends Class {
	private static final long serialVersionUID = 5960240483179024494L;
	private String alias;
	
	public String getAlias(){
		return alias;
	}
	
	public void setAlias(String alias){
		this.alias = alias;
	}

	public boolean hasClassroom() {
		for(SlotRange slot : getSlots()){
			if(slot.getClassroom() != null) return true;
		}
		return false;
	}

}
