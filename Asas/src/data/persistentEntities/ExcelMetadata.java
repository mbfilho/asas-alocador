package data.persistentEntities;

import java.io.Serializable;

public class ExcelMetadata implements Serializable{
	private static final long serialVersionUID = -4088803907547729126L;
	
	private int row;
	
	public ExcelMetadata(int row){
		this.row = row;
	}
	
	public int getRow(){
		return row;
	}
}
