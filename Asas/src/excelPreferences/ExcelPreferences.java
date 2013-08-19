package excelPreferences;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map.Entry;

import utilities.CollectionUtil;

public class ExcelPreferences implements Serializable{
	private static final long serialVersionUID = -3625638456021737156L;
	
	private static final String EXCEL_PREFERENCES_FILENAME = String.format("configs%sexcelPreferences.config", File.separator);
	public static final String EC_MARKER = "EC";
	public static final String CC_MARKER = "CC";
	public static final String SLOT_HOURS_SEPARATOR = "-";
	public static final String SLOT_DAY_SEPARATOR = ",";
	public static final String OK_MARKER = "OK";
	
	private File fileLocation = null;
	private String classesSheet = "Alocacao2013-2";
	private String professorsSheet = "Professores CIn";
	private HashMap<String, List<String>> codeToRoomMapping;
	private int slotCount = 3;
	private String semesterSeparator = ";";
	private String requiredByGraduationMarker = "Obrigatórias da Graduação";
	private String electivesFromGraduationMarker = "Eletivas da Graduação";
	private int professorCount = 3;
	private String requiredByOtherCentersMaker = "Obrigatórias de outros Centros";
	private String endOfFileMarker = "Professores Afastados";
	private String electivesFromPosGraduationMaker = "Eletivas da Pós-graduação";
	private String requiredByPosGraduationMaker = "Obrigatórias da Pós";
	private boolean requiredByOtherCentersOption = true;
	private boolean electivesFromGraduationOption = false;
	private boolean requiredByPosGraduationOption = true;
	private boolean electivesFromPosGraduationOption = true;
	private boolean requiredByGraduationOption = true;
	
	public static ExcelPreferences defaultPreferences(){
		ExcelPreferences prefs = new ExcelPreferences();
		prefs.addMappingCorrespondence("3", CollectionUtil.createList("D-001"));
		prefs.addMappingCorrespondence("4", CollectionUtil.createList("D-002"));
		prefs.addMappingCorrespondence("5", CollectionUtil.createList("D-003"));
		prefs.addMappingCorrespondence("7", CollectionUtil.createList("D-004"));
		prefs.addMappingCorrespondence("8", CollectionUtil.createList("D-005"));
		prefs.addMappingCorrespondence("9", CollectionUtil.createList("D-218"));
		prefs.addMappingCorrespondence("10", CollectionUtil.createList("A-014", "B-020"));
		prefs.addMappingCorrespondence("11", CollectionUtil.createList("B-020"));
		prefs.addMappingCorrespondence("12", CollectionUtil.createList("D-222"));
		prefs.addMappingCorrespondence("13", CollectionUtil.createList("D-224"));
		prefs.addMappingCorrespondence("14", CollectionUtil.createList("A-010"));
		prefs.addMappingCorrespondence("15", CollectionUtil.createList("A-010"));
		prefs.addMappingCorrespondence("16", CollectionUtil.createList("D-226"));
		prefs.addMappingCorrespondence("65", CollectionUtil.createList("Anfiteatro"));
		prefs.addMappingCorrespondence("91", CollectionUtil.createList("D-218"));
		prefs.addMappingCorrespondence("92", CollectionUtil.createList("D-220"));
		prefs.addMappingCorrespondence("100", CollectionUtil.createList("Niate"));
		prefs.addMappingCorrespondence("200", CollectionUtil.createList("D-004", "D-003"));
		prefs.addMappingCorrespondence("202", CollectionUtil.createList("D-005", "D-003"));
		prefs.addMappingCorrespondence("204", CollectionUtil.createList("D-220", "D-218"));
		prefs.addMappingCorrespondence("206", CollectionUtil.createList("D-001", "D-002"));
		prefs.addMappingCorrespondence("208", CollectionUtil.createList("D-218", "D-220"));
		prefs.addMappingCorrespondence("210", CollectionUtil.createList("D-001", "D-220"));
		prefs.addMappingCorrespondence("212", CollectionUtil.createList("D-003", "D-002"));
		prefs.addMappingCorrespondence("214", CollectionUtil.createList("D-003", "D-218"));
		prefs.addMappingCorrespondence("216", CollectionUtil.createList("D-005", "D-220"));
		prefs.addMappingCorrespondence("218", CollectionUtil.createList("D-005", "D-001"));
		prefs.addMappingCorrespondence("300", CollectionUtil.createList("LabG3"));
		prefs.addMappingCorrespondence("400", CollectionUtil.createList("LabG4"));
		prefs.addMappingCorrespondence("Área 2", CollectionUtil.createList("Area II"));
		prefs.addMappingCorrespondence("Área II", CollectionUtil.createList("Area II"));
		prefs.addMappingCorrespondence("CTG", CollectionUtil.createList("CTG"));
		
		return prefs;
	}
	
	public static ExcelPreferences loadFromFileOrDefault(){
		ExcelPreferences prefs = defaultPreferences();
		try {
			ObjectInputStream in = new ObjectInputStream(new FileInputStream(EXCEL_PREFERENCES_FILENAME));
			prefs = (ExcelPreferences) in.readObject();
			in.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		return prefs;
	}
	
	public void saveToFile() throws IOException{
		ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(EXCEL_PREFERENCES_FILENAME));
		out.writeObject(this);
	}
	
	public ExcelPreferences(){
		codeToRoomMapping = new HashMap<String, List<String>>();
	}
	
	public File getFileLocation() {
		return fileLocation;
	}
	
	public void setFileLocation(File fileLocation) {
		this.fileLocation = fileLocation;
	}
	
	public String getClassesSheet() {
		return classesSheet;
	}
	
	public void setClassesSheet(String classesSheet) {
		this.classesSheet = classesSheet;
	}

	public String getRequiredByGraduationMarker() {
		return requiredByGraduationMarker;
	}

	public String getElectivesFromGraduationMaker() {
		return electivesFromGraduationMarker;
	}

	public String getRequiredByOtherCentersMarker() {
		return requiredByOtherCentersMaker;
	}

	public int getProfessorCount() {
		return professorCount;
	}
	
	public List<String> getMappedRooms(String code){
		if(hasMappedRooms(code)) 
			return codeToRoomMapping.get(code);
		return null;
	}
	
	public Iterable<Entry<String, List<String>>> getCodeToRoomMapping(){
		return codeToRoomMapping.entrySet();
	}
	
	public boolean hasMappedRooms(String code){
		return codeToRoomMapping.containsKey(code);
	}

	public String getSemesterSeparator() {
		return semesterSeparator;
	}
	
	public int getSlotCount() {
		return slotCount;
	}

	public String getRequiredByPosGraduationMarker() {
		return requiredByPosGraduationMaker;
	}

	public String getElectivesFromPosGraduationMarker() {
		return electivesFromPosGraduationMaker;
	}

	public String getEndOfFileMarker() {
		return endOfFileMarker;
	}

	public boolean isRequiredByOtherCentersEnabled() {
		return requiredByOtherCentersOption;
	}

	public boolean isElectivesFromGraduationEnabled() {
		return electivesFromGraduationOption;
	}

	public boolean isRequiredByPosGraduationEnabled() {
		return requiredByPosGraduationOption;
	}

	public boolean isElectivesFromPosGraduationEnabled() {
		return electivesFromPosGraduationOption;
	}

	public boolean isRequiredByGraduationEnabled() {
		return requiredByGraduationOption;
	}
	
	public void addMappingCorrespondence(String code, List<String> rooms){
		if(code != null) codeToRoomMapping.put(code, rooms);
	}

	public void setSlotCount(int slotCount) {
		this.slotCount = slotCount;
	}

	public void setSemesterSeparator(String semesterSeparator) {
		this.semesterSeparator = semesterSeparator;
	}

	public void setRequiredByGraduationMarker(String requiredByGraduationMarker) {
		this.requiredByGraduationMarker = requiredByGraduationMarker;
	}

	public void setElectivesFromGraduationMarker(String graduationElectiveMarker) {
		this.electivesFromGraduationMarker = graduationElectiveMarker;
	}

	public void setProfessorCount(int professorCount) {
		this.professorCount = professorCount;
	}

	public void setRequiredByOtherCentersMaker(String requiredByOtherCentersMaker) {
		this.requiredByOtherCentersMaker = requiredByOtherCentersMaker;
	}

	public void setEndOfFileMarker(String endOfFileMarker) {
		this.endOfFileMarker = endOfFileMarker;
	}

	public void setElectivesFromPosGraduationMaker(
			String electivesFromPosGraduationMaker) {
		this.electivesFromPosGraduationMaker = electivesFromPosGraduationMaker;
	}

	public void setRequiredByPosGraduationMaker(String requiredByPosGraduationMaker) {
		this.requiredByPosGraduationMaker = requiredByPosGraduationMaker;
	}

	public void setRequiredByOtherCenters(boolean requiredByOtherCentersOption) {
		this.requiredByOtherCentersOption = requiredByOtherCentersOption;
	}

	public void setElectivesFromGraduation(
			boolean electivesFromGraduationOption) {
		this.electivesFromGraduationOption = electivesFromGraduationOption;
	}

	public void setRequiredByPosGraduation(
			boolean requiredByPosGraduationOption) {
		this.requiredByPosGraduationOption = requiredByPosGraduationOption;
	}

	public void setElectivesFromPosGraduation(boolean electivesFromPosGraduationOption) {
		this.electivesFromPosGraduationOption = electivesFromPosGraduationOption;
	}

	public void setRequiredByGraduation(boolean requiredByGraduationOption) {
		this.requiredByGraduationOption = requiredByGraduationOption;
	}

	public String getGraduationElectiveMarker() {
		return electivesFromGraduationMarker;
	}

	public String getRequiredByOtherCentersMaker() {
		return requiredByOtherCentersMaker;
	}

	public String getElectivesFromPosGraduationMaker() {
		return electivesFromPosGraduationMaker;
	}

	public String getRequiredByPosGraduationMaker() {
		return requiredByPosGraduationMaker;
	}

	public void setProfessorsSheet(String value){
		this.professorsSheet = value;
	}
	
	public String getProfessorsSheet() {
		return professorsSheet;
	}

}
