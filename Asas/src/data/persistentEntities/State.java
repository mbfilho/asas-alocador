package data.persistentEntities;

import java.io.Serializable;


import data.NamedEntity;
import data.configurations.ExcelPreferences;
import data.configurations.StateDescription;
import data.repository.Repository;
import data.repository.SimpleRepository;


public class State implements NamedEntity, Serializable{
	private static final long serialVersionUID = 4973989156840803066L;
	
	public StateDescription description;
	public Repository<Class> classes;
	public Repository<Classroom> classrooms;
	public Repository<Professor> professors;
	public Repository<Warning> allowedWarnings;
	public ExcelPreferences excelPrefs;
	public FileHash excelFileHash;
	
	private int _currentId;
	
	public State(){
		classes = new SimpleRepository<Class>();
		classrooms = new SimpleRepository<Classroom>();
		professors = new SimpleRepository<Professor>();
		allowedWarnings = new SimpleRepository<Warning>();
		_currentId = 1;
	}
	
	public String getName() {
		return description.getName();
	}

	public void setStateDescription(String name, String desc, boolean isDraft) {
		description = new StateDescription(name, desc, isDraft);
	}
	
	public int getUniqueId(){
		return _currentId++;
	}
	
	public Object clone() throws CloneNotSupportedException{
		throw new CloneNotSupportedException();
	}
	
	public Object getSignature(){
		return description.getFile();
	}
	
	public void setExcelPreferences(ExcelPreferences prefs){
		this.excelPrefs = prefs;
		updateExcelHash();
	}
	
	public void updateExcelHash(){
		if(excelFileHash == null)
			excelFileHash = new FileHash(excelPrefs.getFileLocation());
		else
			excelFileHash.update(excelPrefs.getFileLocation());
	}
}
