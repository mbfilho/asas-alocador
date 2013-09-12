package data.persistentEntities;

import java.io.Serializable;

public class ExcelMetadata implements Serializable{
	private static final long serialVersionUID = -4088803907547729126L;
	
	private int row;
	private boolean isDirty;
	
	public ExcelMetadata(int row){
		this.row = row;
		isDirty = false;
	}
	
	public int getRow(){
		return row;
	}
	
	public boolean isDirty(){
		return isDirty;
	}
	
	public void setDirty(boolean val){
		isDirty = val;
	}
}
