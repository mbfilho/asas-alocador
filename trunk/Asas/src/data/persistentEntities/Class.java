package data.persistentEntities;

import java.awt.Color;
import java.io.Serializable;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;

import data.NamedEntity;

import utilities.StringUtil;

public class Class implements NamedEntity, Serializable{
	private static final long serialVersionUID = -4317770498263255354L;
	
	private String name;
	private List<Professor> professors;
	private double ch;
	private int ccSemester, ecSemester;
	private String course;
	private List<SlotRange> slots;
	private int ch2;
	private String code;
	private Color color;
	private int id;
	private Class alias;
	private ExcelMetadata excelMetadata;
	
	public Class(){
		this.professors = new LinkedList<Professor>();
		this.slots = new LinkedList<SlotRange>();
		ccSemester = ecSemester = -1;
	}
		
	public double getCh() {
		return ch;
	}

	public void setCh(double ch) {
		this.ch = ch;
	}

	public int getCcSemester() {
		return ccSemester;
	}

	public void setCcSemester(String ccSemester) {
		if(StringUtil.isNullOrEmpty(ccSemester)) setCcSemester(-1);
		else setCcSemester(Integer.parseInt(ccSemester));
	}
	
	public void setCcSemester(int ccSemester) {
		this.ccSemester = ccSemester;
	}

	public int getEcSemester() {
		return ecSemester;
	}
	
	public void setEcSemester(String ecSemester) {
		if(StringUtil.isNullOrEmpty(ecSemester)) setEcSemester(-1);
		else setEcSemester(Integer.parseInt(ecSemester));
	}
	
	public void setEcSemester(int ecSemester) {
		this.ecSemester = ecSemester;
	}

	public String getCourse() {
		return course;
	}

	public void setCourse(String course) {
		if(StringUtil.isNullOrEmpty(course))
			course = null;
		this.course = course;
	}

	public int getCh2() {
		return ch2;
	}

	public void setCh2(int ch2) {
		this.ch2 = ch2;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		if(StringUtil.isNullOrEmpty(code))
			code = null;
		this.code = code;
	}

	public void setName(String name){
		if(StringUtil.isNullOrEmpty(name)) 
			name = null;
		this.name = name;
	}
	
	public void addSlot(SlotRange slot){
		this.slots.add(slot);
		Collections.sort(slots);
	}
	
	public Collection<SlotRange> getSlots(){
		return Collections.unmodifiableList(slots);
	}
	
	public List<Professor> getProfessors(){
		return Collections.unmodifiableList(professors);
	}
	
	public void addProfessor(Professor prof){
		this.professors.add(prof);
		sortProfessors();
	}
	
	public String getName() {
		return name;
	}

	public void setProfessors(Iterable<Professor> profs) {
		professors.clear();
		for(Professor p : profs) professors.add(p);
		
	}
	
	private void sortProfessors(){
		Collections.sort(professors, new Comparator<Professor>() {
			public int compare(Professor p1, Professor p2) {
				return p1.getName().compareTo(p2.getName());
			}
		});
	}
	
	public void setSlots(Iterable<SlotRange> newSlots) {
		slots.clear();
		for(SlotRange s : newSlots) slots.add(s);
		Collections.sort(slots);
	}

	public void setColor(Color color){
		this.color = color;
	}
	
	public Color getColor(){
		return color;
	}
	
	public String getHtmlColor(){
		return String.format("#%02x%02x%02x", color.getRed(), color.getGreen(), color.getBlue());
	}
	
	public void setId(int i){
		id = i;
	}
	
	public int getId(){
		return id;
	}
	
	public boolean equals(Object ot){
		Class other = (Class) ot;
		return id == other.getId();
	}
	
	public String completeName(){
		String completeName = "";
		if(code != null && !code.equals("")) completeName = code + " - ";
		completeName += name;
		return completeName;
	}
	
	public Object clone() throws CloneNotSupportedException{
		throw new CloneNotSupportedException();
	}
	
	public List<Classroom> getRoomsOrderedBySlot(){
		List<SlotRange> slots = new LinkedList<SlotRange>(this.slots);
		List<Classroom> rooms = new LinkedList<Classroom>();
		Collections.sort(slots);
		
		for(SlotRange slot : slots){
			if(slot.getClassroom() != null)
				rooms.add(slot.getClassroom());
		}
		return rooms;
	}
	
	public List<Classroom> getAllRooms(){
		List<Classroom> rooms = new LinkedList<Classroom>();
		for(SlotRange r : getSlots()) if(r.getClassroom() != null) rooms.add(r.getClassroom());
		return rooms;
	}
	
	public void setExcelMetadata(ExcelMetadata meta){
		this.excelMetadata = meta;
	}
	
	public ExcelMetadata getExcelMetadata(){
		return excelMetadata;
	}
}
