package preferences;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

public class ExcelPreferences {
	private String fileLocation = "C:\\Users\\Marcio Barbosa\\Dropbox\\2013.1\\tg\\implementacao\\dados\\Alocacao2013-2.horario.xlsm";
	private String classesSheet = "Alocacao2013-2";
	private HashMap<String, List<String>> codeToRoomMapping;
	private String slotHoursSeparator = "-";
	private String slotDaySeparator = ",";
	private int slotCount = 3;
	private String ecMarker = "EC";
	private String ccMarker = "CC";
	private String semesterSeparator = ";";
	private String graduationRequiredMarker = "Obrigatórias da Graduação";
	private String okMarker = "OK";
	private String graduationElectiveMarker = "Eletivas da Graduação";
	private int professorCount = 3;
	private String requiredByOtherCentersMaker = "Obrigatórias de outros Centros";
	
	private List<String> createList(String ... args){
		List<String> list = new LinkedList<String>();
		for(String a : args) list.add(a);
		return list;
	}
	
	public ExcelPreferences(){
		codeToRoomMapping = new HashMap<String, List<String>>();
		codeToRoomMapping.put("3", createList("D-001"));
		codeToRoomMapping.put("4", createList("D-002"));
		codeToRoomMapping.put("5", createList("D-003"));
		codeToRoomMapping.put("7", createList("D-004"));
		codeToRoomMapping.put("8", createList("D-005"));
		codeToRoomMapping.put("9", createList("D-218"));
		codeToRoomMapping.put("10", createList("A-014", "B-020"));
		codeToRoomMapping.put("11", createList("B-020"));
		codeToRoomMapping.put("12", createList("D-222"));
		codeToRoomMapping.put("13", createList("D-224"));
		codeToRoomMapping.put("14", createList("A-010"));
		codeToRoomMapping.put("15", createList("A-010"));
		codeToRoomMapping.put("16", createList("D-226"));
		codeToRoomMapping.put("65", createList("Anfiteatro"));
		codeToRoomMapping.put("91", createList("D-218"));
		codeToRoomMapping.put("92", createList("D-220"));
		codeToRoomMapping.put("100", createList("Niate"));
		codeToRoomMapping.put("200", createList("D-004", "D-003"));
		codeToRoomMapping.put("202", createList("D-005", "D-003"));
		codeToRoomMapping.put("204", createList("D-220", "D-218"));
		codeToRoomMapping.put("206", createList("D-001", "D-002"));
		codeToRoomMapping.put("208", createList("D-218", "D-220"));
		codeToRoomMapping.put("210", createList("D-001", "D-220"));
		codeToRoomMapping.put("212", createList("D-003", "D-002"));
		codeToRoomMapping.put("214", createList("D-003", "D-218"));
		codeToRoomMapping.put("216", createList("D-005", "D-220"));
		codeToRoomMapping.put("218", createList("D-005", "D-001"));
		codeToRoomMapping.put("300", createList("LabG3"));
		codeToRoomMapping.put("400", createList("LabG4"));
		codeToRoomMapping.put("Área 2", createList("Area II"));
		codeToRoomMapping.put("Área II", createList("Area II"));
		codeToRoomMapping.put("CTG", createList("CTG"));
	}
	
	public String getFileLocation() {
		return fileLocation;
	}
	
	public void setFileLocation(String fileLocation) {
		this.fileLocation = fileLocation;
	}
	
	public String getClassesSheet() {
		return classesSheet;
	}
	
	public void setClassesSheet(String classesSheet) {
		this.classesSheet = classesSheet;
	}

	public String getGraduationRequiredMarker() {
		return graduationRequiredMarker;
	}

	public String getOkMarker() {
		return okMarker;
	}

	public String getGraduationElectiveMaker() {
		return graduationElectiveMarker;
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
	
	public boolean hasMappedRooms(String code){
		return codeToRoomMapping.containsKey(code);
	}

	public String getSemesterSeparator() {
		return semesterSeparator;
	}

	public String getCCMarker() {
		return ccMarker;
	}

	public String getECMarker() {
		return ecMarker;
	}
	
	public int getSlotCount() {
		return slotCount;
	}

	public String getSlotDaySeparator() {
		return slotDaySeparator;
	}

	public String getSlotHourSeparator() {
		return slotHoursSeparator;
	}
}
